import { spawn } from 'node:child_process'
import { mkdir, mkdtemp, rm, writeFile } from 'node:fs/promises'
import { tmpdir } from 'node:os'
import path from 'node:path'

const FRONTEND_BASE_URL = process.env.E2E_FRONTEND_URL || 'http://127.0.0.1:4174'
const BACKEND_BASE_URL = process.env.E2E_BACKEND_URL || 'http://127.0.0.1:8080'
const USERNAME = process.env.E2E_USERNAME || 'admin'
const PASSWORD = process.env.E2E_PASSWORD || 'admin123'
const CHROME_BIN = process.env.CHROME_BIN || 'google-chrome-stable'
const OUTPUT_DIR = process.env.E2E_OUTPUT_DIR || path.resolve('..', 'docs')
const JSON_REPORT = path.join(OUTPUT_DIR, 'e2e-smoke-report.json')
const MD_REPORT = path.join(OUTPUT_DIR, 'e2e-smoke-report.md')
const PAGE_TIMEOUT_MS = Number(process.env.E2E_PAGE_TIMEOUT_MS || 15000)
const MAX_ROUTE_FAILURES = Number(process.env.E2E_MAX_ROUTE_FAILURES || 0)
const CLICK_SAFE_ACTIONS = process.env.E2E_CLICK_SAFE_ACTIONS !== '0'

const EXTRA_ROUTES = [
  { title: '首页', path: '/' },
  { title: '个人中心', path: '/profile' },
  { title: '个人资料', path: '/profile/userInfo' },
  { title: '修改密码', path: '/profile/resetPwd' },
  { title: '头像上传', path: '/profile/userAvatar' }
]

const PAGE_ERROR_TEXTS = [
  '系统繁忙',
  '认证失败',
  '无法访问系统资源',
  '加载失败',
  '请求失败',
  '接口不可访问',
  'Download failed',
  'Request failed'
]

const SAFE_ACTION_TEXTS = ['查询', '搜索', '重置', '刷新']

const cdpCallbacks = new Map()
let cdpId = 0
let chrome
let ws
let tmpProfile
let sessionId
let requestUrls = new Map()

async function main() {
  await ensureService(`${BACKEND_BASE_URL}/auth/login`, 'backend')
  await ensureService(FRONTEND_BASE_URL, 'frontend')

  const token = await login()
  const routes = await collectRoutes(token)
  tmpProfile = await mkdtemp(path.join(tmpdir(), 'robot-monitor-e2e-'))

  try {
    const browserWsUrl = await launchChrome()
    ws = new WebSocket(browserWsUrl)
    await onceOpen(ws)
    const targetId = await createTarget()
    sessionId = await attachToTarget(targetId)
    await enablePage()
    await seedToken(token)

    const results = []
    for (const route of routes) {
      results.push(await checkRoute(route))
    }

    await saveReport(results)
    const failed = results.filter((item) => !item.ok)
    printSummary(results, failed)
    if (failed.length > MAX_ROUTE_FAILURES) {
      process.exitCode = 1
    }
  } finally {
    await cleanup()
  }
}

async function ensureService(url, name) {
  try {
    const response = await fetch(url, { method: 'GET', signal: AbortSignal.timeout(3000) })
    if (response.status >= 200 && response.status < 500) {
      return
    }
    throw new Error(`${name} responded with HTTP ${response.status}`)
  } catch (error) {
    throw new Error(`${name} service is not reachable at ${url}: ${error.message}`)
  }
}

async function login() {
  const response = await fetch(`${BACKEND_BASE_URL}/auth/login`, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({ username: USERNAME, password: PASSWORD })
  })
  const payload = await response.json()
  if (!payload.token) {
    throw new Error(`login failed: ${response.status}`)
  }
  return payload.token
}

async function collectRoutes(token) {
  const menuRoutes = [
    { title: '用户管理', path: '/system/user' },
    { title: '在舱记录', path: '/statAnalysis/inLoungeList' },
    { title: '准出记录', path: '/viewManagment/outGoing' },
    { title: '准入记录', path: '/statAnalysis/goingStat' },
    { title: '问询统计', path: '/statAnalysis/inquiry' },
    { title: '引导统计', path: '/statAnalysis/guide' },
    { title: '贵宾室', path: '/configManagment/vipRoom' },
    { title: '功能区', path: '/configManagment/areaManagment' },
    { title: '区域', path: '/configManagment/vipRoomRegion' },
    { title: '摄像头', path: '/configManagment/monitorDevice' },
    { title: '机器人', path: '/configManagment/robot' },
    { title: '机器人音频', path: '/configManagment/robotAudio' },
    { title: '音频', path: '/configManagment/audio' },
    { title: '图片', path: '/configManagment/photo' },
    { title: '任务列表', path: '/taskManagment/taskList' },
    { title: '投诉记录', path: '/configManagment/complaintRecord' },
    { title: '登录日志', path: '/monitor/logininfor' },
    { title: '操作日志', path: '/monitor/operlog' }
  ]
  return uniqueRoutes([...menuRoutes, ...EXTRA_ROUTES])
}

function uniqueRoutes(routes) {
  const seen = new Set()
  return routes.filter((route) => {
    if (!route.path || seen.has(route.path)) {
      return false
    }
    seen.add(route.path)
    return true
  })
}

async function launchChrome() {
  const port = 9222 + Math.floor(Math.random() * 1000)
  chrome = spawn(CHROME_BIN, [
    '--headless=new',
    '--disable-gpu',
    '--no-first-run',
    '--no-default-browser-check',
    '--disable-dev-shm-usage',
    '--window-size=1440,1000',
    `--user-data-dir=${tmpProfile}`,
    `--remote-debugging-port=${port}`,
    'about:blank'
  ], { stdio: ['ignore', 'pipe', 'pipe'] })

  chrome.stderr.on('data', (chunk) => {
    const text = String(chunk)
    if (/ERROR|FATAL/i.test(text)) {
      process.stderr.write(text)
    }
  })

  const versionUrl = `http://127.0.0.1:${port}/json/version`
  for (let i = 0; i < 80; i++) {
    try {
      const response = await fetch(versionUrl)
      const payload = await response.json()
      if (payload.webSocketDebuggerUrl) {
        return payload.webSocketDebuggerUrl
      }
    } catch {
      await sleep(150)
    }
  }
  throw new Error(`Chrome DevTools endpoint did not start: ${versionUrl}`)
}

function send(method, params = {}, targetSessionId = sessionId) {
  const id = ++cdpId
  ws.send(JSON.stringify(targetSessionId ? { id, method, params, sessionId: targetSessionId } : { id, method, params }))
  return new Promise((resolve, reject) => {
    cdpCallbacks.set(id, { resolve, reject })
  })
}

function onceOpen(socket) {
  return new Promise((resolve, reject) => {
    socket.addEventListener('open', resolve, { once: true })
    socket.addEventListener('error', reject, { once: true })
    socket.addEventListener('message', handleCdpMessage)
  })
}

function handleCdpMessage(event) {
  const message = JSON.parse(event.data)
  if (!message.id) {
    return
  }
  const callback = cdpCallbacks.get(message.id)
  if (!callback) {
    return
  }
  cdpCallbacks.delete(message.id)
  if (message.error) {
    callback.reject(new Error(message.error.message || JSON.stringify(message.error)))
  } else {
    callback.resolve(message.result)
  }
}

async function createTarget() {
  const result = await send('Target.createTarget', { url: 'about:blank' }, null)
  return result.targetId
}

async function attachToTarget(targetId) {
  const result = await send('Target.attachToTarget', { targetId, flatten: true }, null)
  return result.sessionId
}

async function enablePage() {
  await Promise.all([
    send('Page.enable'),
    send('Runtime.enable'),
    send('Network.enable'),
    send('Log.enable')
  ])
}

async function seedToken(token) {
  await navigate(`${FRONTEND_BASE_URL}/login`)
  await evaluate(`localStorage.setItem('robotmonitor_admin_token', ${JSON.stringify(token)})`)
}

async function checkRoute(route) {
  const events = {
    consoleErrors: [],
    pageErrors: [],
    failedRequests: [],
    httpErrors: []
  }
  const listener = (event) => captureRouteEvent(event, events)
  ws.addEventListener('message', listener)

  const startedAt = Date.now()
  try {
    await navigate(`${FRONTEND_BASE_URL}${route.path}`)
    await waitForRouteReady(route.path)
    await sleep(650)
    const safeActions = CLICK_SAFE_ACTIONS ? await clickSafeActions() : []
    if (safeActions.length > 0) {
      await sleep(650)
    }
    const snapshot = await evaluate(`(() => ({
      title: document.title,
      path: location.pathname,
      bodyText: document.body.innerText.slice(0, 5000),
      hasContent: !!document.querySelector('.content-shell, .page-card, .ant-card, .el-card, table, .el-table, .ant-table'),
      hasLogin: location.pathname === '/login' || document.body.innerText.includes('登录系统'),
      errorHeading: document.querySelector('.error-shell .error-card h1')?.textContent?.trim() || '',
      has404: document.querySelector('.error-shell .error-card h1')?.textContent?.trim() === '404'
        || document.body.innerText.includes('页面不存在或路由尚未映射'),
      has401: document.querySelector('.error-shell .error-card h1')?.textContent?.trim() === '401'
        || document.body.innerText.includes('没有访问该资源的权限'),
      errorTexts: ${JSON.stringify(PAGE_ERROR_TEXTS)}.filter((text) => document.body.innerText.includes(text))
    }))()`)
    const ok = snapshot.path !== '/login'
      && !snapshot.hasLogin
      && !snapshot.has404
      && !snapshot.has401
      && snapshot.errorTexts.length === 0
      && snapshot.hasContent
      && events.pageErrors.length === 0
      && events.failedRequests.length === 0
      && events.httpErrors.length === 0

    return {
      ...route,
      ok,
      durationMs: Date.now() - startedAt,
      snapshot,
      safeActions,
      ...events
    }
  } catch (error) {
    return {
      ...route,
      ok: false,
      durationMs: Date.now() - startedAt,
      error: error.message,
      ...events
    }
  } finally {
    ws.removeEventListener('message', listener)
  }
}

function captureRouteEvent(event, events) {
  const message = JSON.parse(event.data)
  if (message.sessionId !== sessionId) {
    return
  }
  if (message.method === 'Runtime.exceptionThrown') {
    events.pageErrors.push(message.params.exceptionDetails?.text || message.params.exceptionDetails?.exception?.description || 'page exception')
  }
  if (message.method === 'Runtime.consoleAPICalled' && ['error', 'assert'].includes(message.params.type)) {
    events.consoleErrors.push((message.params.args || []).map((arg) => arg.value || arg.description || '').join(' '))
  }
  if (message.method === 'Network.requestWillBeSent') {
    requestUrls.set(message.params.requestId, message.params.request?.url || '')
  }
  if (message.method === 'Network.loadingFailed') {
    const url = requestUrls.get(message.params.requestId) || message.params.requestId || ''
    events.failedRequests.push(`${url} ${message.params.errorText}`)
  }
  if (message.method === 'Network.responseReceived') {
    const response = message.params.response
    if (response.status >= 400 && shouldTrackResponse(response.url)) {
      events.httpErrors.push(`${response.status} ${response.url}`)
    }
  }
  if (message.method === 'Log.entryAdded' && message.params.entry?.level === 'error') {
    events.consoleErrors.push(message.params.entry.text)
  }
}

function shouldTrackResponse(url) {
  return url.startsWith(FRONTEND_BASE_URL) || url.startsWith(BACKEND_BASE_URL.replace(/\/api$/, ''))
}

async function navigate(url) {
  requestUrls = new Map()
  await send('Page.navigate', { url })
  await waitFor(() => evaluate('document.readyState').then((state) => state === 'complete'), PAGE_TIMEOUT_MS)
}

async function waitForRouteReady(pathname) {
  await waitFor(async () => {
    const current = await evaluate('location.pathname')
    return current === pathname
  }, PAGE_TIMEOUT_MS)
}

async function evaluate(expression) {
  const result = await send('Runtime.evaluate', {
    expression,
    awaitPromise: true,
    returnByValue: true
  })
  if (result.exceptionDetails) {
    throw new Error(result.exceptionDetails.text || result.exceptionDetails.exception?.description || 'Runtime.evaluate failed')
  }
  return result.result?.value
}

async function waitFor(check, timeoutMs) {
  const deadline = Date.now() + timeoutMs
  while (Date.now() < deadline) {
    if (await check()) {
      return
    }
    await sleep(150)
  }
  throw new Error(`timeout after ${timeoutMs}ms`)
}

async function saveReport(results) {
  await mkdir(OUTPUT_DIR, { recursive: true })
  const summary = {
    generatedAt: new Date().toISOString(),
    frontendBaseUrl: FRONTEND_BASE_URL,
    backendBaseUrl: BACKEND_BASE_URL,
    total: results.length,
    passed: results.filter((item) => item.ok).length,
    failed: results.filter((item) => !item.ok).length
  }
  await writeFile(JSON_REPORT, JSON.stringify({ summary, results }, null, 2))
  await writeFile(MD_REPORT, renderMarkdown(summary, results))
}

function renderMarkdown(summary, results) {
  const lines = [
    '# 浏览器逐页端到端烟测报告',
    '',
    `- 生成时间：${summary.generatedAt}`,
    `- 前端地址：${summary.frontendBaseUrl}`,
    `- 后端地址：${summary.backendBaseUrl}`,
    `- 总页面：${summary.total}`,
    `- 通过：${summary.passed}`,
    `- 失败：${summary.failed}`,
    '',
    '| 状态 | 页面 | 路径 | 耗时 | 说明 |',
    '| --- | --- | --- | ---: | --- |'
  ]
  for (const item of results) {
    const status = item.ok ? '[x]' : '[ ]'
    const reason = item.ok ? '通过' : summarizeFailure(item)
    lines.push(`| ${status} | ${escapeMd(item.title)} | \`${item.path}\` | ${item.durationMs}ms | ${escapeMd(reason)} |`)
  }
  lines.push('')
  lines.push('## 失败明细')
  const failed = results.filter((item) => !item.ok)
  if (failed.length === 0) {
    lines.push('')
    lines.push('全部通过。')
  } else {
    for (const item of failed) {
      lines.push('')
      lines.push(`### ${item.title} \`${item.path}\``)
      if (item.error) lines.push(`- 错误：${item.error}`)
      if (item.httpErrors?.length) lines.push(`- HTTP：${item.httpErrors.join('; ')}`)
      if (item.failedRequests?.length) lines.push(`- 请求失败：${item.failedRequests.join('; ')}`)
      if (item.pageErrors?.length) lines.push(`- 页面异常：${item.pageErrors.join('; ')}`)
      if (item.consoleErrors?.length) lines.push(`- Console：${item.consoleErrors.join('; ')}`)
      if (item.safeActions?.length) lines.push(`- 已点击安全操作：${item.safeActions.join(', ')}`)
      if (item.snapshot?.bodyText) lines.push(`- 页面文本：${item.snapshot.bodyText.slice(0, 300).replace(/\s+/g, ' ')}`)
    }
  }
  return `${lines.join('\n')}\n`
}

function summarizeFailure(item) {
  if (item.error) return item.error
  if (item.httpErrors?.length) return item.httpErrors[0]
  if (item.failedRequests?.length) return item.failedRequests[0]
  if (item.pageErrors?.length) return item.pageErrors[0]
  if (item.consoleErrors?.length) return item.consoleErrors[0]
  if (item.snapshot?.hasLogin) return '跳回登录页'
  if (item.snapshot?.has404) return '页面 404'
  if (item.snapshot?.has401) return '页面 401'
  if (item.snapshot?.errorTexts?.length) return `页面出现错误提示：${item.snapshot.errorTexts.join(', ')}`
  if (!item.snapshot?.hasContent) return '未识别到页面内容'
  return '未知失败'
}

async function clickSafeActions() {
  return evaluate(`(() => {
    const safeTexts = ${JSON.stringify(SAFE_ACTION_TEXTS)}
    const clicked = []
    const buttons = Array.from(document.querySelectorAll('button'))
    for (const button of buttons) {
      const text = (button.innerText || button.textContent || '').trim().replace(/\\s+/g, '')
      if (!safeTexts.includes(text) || button.disabled || button.offsetParent === null) {
        continue
      }
      button.click()
      clicked.push(text)
      if (clicked.length >= 3) {
        break
      }
    }
    return clicked
  })()`)
}

function escapeMd(text) {
  return String(text || '').replace(/\|/g, '\\|').replace(/\n/g, ' ')
}

function printSummary(results, failed) {
  console.log(`E2E smoke finished: ${results.length - failed.length}/${results.length} passed`)
  console.log(`Report: ${MD_REPORT}`)
  if (failed.length > 0) {
    failed.forEach((item) => console.log(`FAIL ${item.path}: ${summarizeFailure(item)}`))
  }
}

async function cleanup() {
  if (ws) {
    ws.close()
  }
  if (chrome && !chrome.killed) {
    chrome.kill('SIGTERM')
    await waitForProcessExit(chrome, 2500)
  }
  if (tmpProfile) {
    try {
      await rm(tmpProfile, { recursive: true, force: true, maxRetries: 8, retryDelay: 150 })
    } catch (error) {
      console.warn(`Failed to remove temporary Chrome profile ${tmpProfile}: ${error.message}`)
    }
  }
}

function sleep(ms) {
  return new Promise((resolve) => setTimeout(resolve, ms))
}

function waitForProcessExit(child, timeoutMs) {
  if (child.exitCode !== null || child.signalCode) {
    return Promise.resolve()
  }
  return new Promise((resolve) => {
    const timer = setTimeout(resolve, timeoutMs)
    child.once('exit', () => {
      clearTimeout(timer)
      resolve()
    })
  })
}

main().catch(async (error) => {
  console.error(error.message)
  await cleanup()
  process.exit(1)
})
