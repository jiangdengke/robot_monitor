# 浏览器逐页端到端烟测

本文档说明如何跑管理后台浏览器逐页验收。脚本不依赖 Playwright/Cypress，直接使用本机 `google-chrome-stable` 的 headless 模式和 Chrome DevTools Protocol。

## 前置条件

- MySQL 已启动：`127.0.0.1:3306`，数据库 `robot_monitor`。
- Redis 已启动：`127.0.0.1:6380`，密码 `1234%%6`。
- 数据库已初始化：`backend/sql/init.sql`。
- 后端已启动：`http://127.0.0.1:7075/api`。
- 前端已启动：`http://127.0.0.1:4174`。

## 启动顺序

```bash
cd backend
./gradlew run
```

```bash
cd frontend
npm run dev
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
- `E2E_BACKEND_URL`：默认 `http://127.0.0.1:7075/api`
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

当前最近一次本地验收结果：57/57 页面通过。

验收范围：

- 登录接口真实登录并写入前端 token。
- 从后端 `/getRouters` 拉取真实菜单。
- 逐个访问菜单路径。
- 额外访问个人中心、头像上传、授权、字典数据、调度详情、桌台模型等非侧栏入口。
- 默认点击每页的安全操作按钮：查询、搜索、重置、刷新、刷新桌台；不会点击新增、删除、提交等会改数据的动作。
- 捕获页面异常、Console error、失败请求和本地前后端 4xx/5xx 响应。

## 当前注意事项

- 脚本需要后端和前端同时可访问；如果 Docker 未启动导致 MySQL/Redis 不可用，脚本会在后端健康检查阶段失败。
- 真实硬件、视觉、语音、AI/Kafka 外部链路仍按 mock 边界验收，不在本脚本中强制验证真机行为。
