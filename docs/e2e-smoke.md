# 浏览器逐页端到端烟测

本文档说明如何验收通用机器人管理系统的保留页面、只读 API，以及已删除接口和路由的 HTTP 404 行为。脚本不依赖 Playwright/Cypress，直接使用本机 `google-chrome-stable` 的 headless 模式和 Chrome DevTools Protocol。

## 前置条件

- `backend` 后端已启动，并连接到已完成第二阶段结构重建的本地 MySQL。
- 前端已启动。
- 本地可用 `google-chrome-stable`。

## 启动顺序

```bash
cd backend
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
- `E2E_MAX_ROUTE_FAILURES`：默认 `0`
- `E2E_CLICK_SAFE_ACTIONS`：默认开启；设为 `0` 可只打开页面不点击安全按钮

## 输出报告

脚本会生成：

- `docs/e2e-smoke-report.json`
- `docs/e2e-smoke-report.md`

报告只代表实际运行时的环境和结果，不应手工修改或用静态检查结果替代。修改烟测源脚本后，先执行 `node --check scripts/e2e-smoke.mjs`；待前后端和重建后的数据库均就绪，再运行完整烟测生成新报告。

验收范围：

- 登录接口真实登录并写入前端 token。
- 访问认证、用户、个人资料、sites、areas、points、devices、robots、tasks、登录日志、操作日志和 Swagger 的代表性只读 API。
- 逐个访问 `/config/site`、`/config/area`、`/config/point`、`/config/device`、`/config/robot`、`/config/task` 等当前保留页面路径。
- 额外访问个人中心、头像上传等非侧栏入口。
- 携带有效身份访问代表性的旧 lounge/region、媒体、统计、数字孪生、投诉和知识库接口，并断言 HTTP 404。
- 访问已移除的 `/tool/swagger` 和代表性的旧业务前端路径，并断言页面进入 404；后端 Swagger 接口仍保持可访问。
- 默认点击每页的安全操作按钮：查询、搜索、重置、刷新；不会点击新增、删除、提交等会改数据的动作。
- 捕获页面异常、Console error、失败请求和本地前后端 4xx/5xx 响应。
- 如果前后端没有使用默认端口，执行时请显式传入 `E2E_FRONTEND_URL` 和 `E2E_BACKEND_URL`。

## 当前注意事项

- 脚本需要后端和前端同时可访问。
- 后端默认使用 MySQL；完整烟测前应确认第二阶段 Schema 和初始化数据已经在当前本地演示库生效。
- 脚本仅对保留 API 做只读访问，不执行新增、编辑、删除或任务运行操作。
- 真实硬件、视觉、语音和机器人外部链路不在本脚本中强制验证。
