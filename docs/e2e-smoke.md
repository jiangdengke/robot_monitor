# 浏览器逐页端到端烟测

本文档说明如何跑管理后台浏览器逐页验收。脚本不依赖 Playwright/Cypress，直接使用本机 `google-chrome-stable` 的 headless 模式和 Chrome DevTools Protocol。

## 前置条件

- `moban_java` 后端已启动。
- 前端已启动。
- 本地可用 `google-chrome-stable`。

## 启动顺序

```bash
cd moban_java
./gradlew bootRun
```

```bash
cd frontend
npm run dev -- --host 127.0.0.1 --port 4174
```

## 执行烟测

```bash
cd frontend
npm run e2e:smoke
```

默认账号：

- 用户名：`admin`
- 密码：`admin123`

可选环境变量：

- `E2E_FRONTEND_URL`：默认 `http://127.0.0.1:4174`
- `E2E_BACKEND_URL`：默认 `http://127.0.0.1:8080`
- `E2E_USERNAME`：默认 `admin`
- `E2E_PASSWORD`：默认 `admin123`
- `CHROME_BIN`：默认 `google-chrome-stable`
- `E2E_OUTPUT_DIR`：默认 `../docs`
- `E2E_PAGE_TIMEOUT_MS`：默认 `15000`
- `E2E_CLICK_SAFE_ACTIONS`：默认开启；设为 `0` 可只打开页面不点击安全按钮

## 输出报告

脚本会生成：

- `docs/e2e-smoke-report.json`
- `docs/e2e-smoke-report.md`

当前最近一次新单体联调结果：29/29 页面通过。

验收范围：

- 登录接口真实登录并写入前端 token。
- 逐个访问当前保留范围内的页面路径。
- 额外访问个人中心、头像上传等非侧栏入口。
- 默认点击每页的安全操作按钮：查询、搜索、重置、刷新；不会点击新增、删除、提交等会改数据的动作。
- 捕获页面异常、Console error、失败请求和本地前后端 4xx/5xx 响应。
- 如果前后端没有使用默认端口，执行时请显式传入 `E2E_FRONTEND_URL` 和 `E2E_BACKEND_URL`。

## 当前注意事项

- 脚本需要后端和前端同时可访问。
- `moban_java` 当前默认使用 H2 内存库初始化，不依赖旧 `backend` 目录。
- 真实硬件、视觉、语音和机器人外部链路不在本脚本中强制验证。
