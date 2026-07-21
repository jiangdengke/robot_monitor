# 通用机器人管理系统 API 清单

当前单体后端只保留通用机器人管理所需接口。除 Swagger 外，业务接口均按现有认证规则访问。

## 认证

- `POST /auth/login`
- `POST /auth/sign-up`
- `POST /auth/sign-out`
- `GET /auth/me`

## 用户与个人中心

- `GET /users`
- `GET /users/{id}`
- `POST /users`
- `PUT /users/{id}`
- `DELETE /users/{ids}`
- `GET /me`
- `PUT /me`
- `PUT /me/password`
- `PUT /me/avatar`

## 文件上传

- `POST /files`

## 场地、区域与点位

- `GET /config/sites`
- `POST /config/sites`
- `PUT /config/sites/{id}`
- `DELETE /config/sites/{id}`
- `GET /config/areas`
- `POST /config/areas`
- `PUT /config/areas/{id}`
- `DELETE /config/areas/{id}`
- `GET /config/points`
- `POST /config/points`
- `PUT /config/points/{id}`
- `DELETE /config/points/{id}`

空间 JSON 契约固定使用 `siteId`、`siteName`、`siteCode`、`pointId` 和 `pointName`。
区域和点位必须引用存在的场地，点位引用的区域必须属于同一场地。场地、区域或点位仍被下级资源使用时，删除请求会返回业务错误，不执行隐式级联删除。

## 设备与点位绑定

- `GET /config/devices`
- `POST /config/devices`
- `PUT /config/devices/{id}`
- `DELETE /config/devices/{id}`
- `GET /config/device-point-bindings?deviceId={deviceId}&pointId={pointId}`
- `POST /config/device-point-bindings`
- `DELETE /config/device-point-bindings?deviceId={deviceId}&pointId={pointId}`

设备和点位绑定要求两者属于同一场地。

## 机器人

- `GET /config/robots`
- `POST /config/robots`
- `PUT /config/robots/{id}`
- `DELETE /config/robots/{id}`

机器人选择的点位必须属于机器人的场地。

## 任务

- `GET /config/tasks`
- `POST /config/tasks`
- `PUT /config/tasks/{id}`
- `DELETE /config/tasks/{id}`
- `POST /config/tasks/{id}/run`
- `GET /config/task-logs`

任务目标字段固定使用文本字段 `targetPoint`。任务执行与执行日志属于保留范围，不提供旧空间字段别名。
任务选择的机器人必须属于任务场地。

## 日志管理

- `GET /monitor/login-logs`
- `DELETE /monitor/login-logs`
- `GET /monitor/operation-logs`
- `DELETE /monitor/operation-logs`

## Swagger

- `GET /swagger-ui/index.html`
- `GET /v3/api-docs`

## 已删除接口

旧 lounge/region 与 device-region-binding 接口，以及旅客航班、媒体、投诉、知识库、统计和数字孪生等接口均已删除。系统不提供兼容别名或转发；携带有效身份访问这些旧路径时应返回 HTTP 404。

## 当前验证

- 后端质量门禁使用 `./gradlew test spotlessCheck`，完整构建使用 `./gradlew clean jooqCodegen compileKotlin compileJava bootJar`。
- 数据模型在 `src/main/resources/db/schema.sql`
- 当前默认使用 MySQL，生产/已有库默认不执行 schema 初始化；仅在 `SPRING_SQL_INIT_MODE=always` 时由 `schema.sql` 重建示例库
- 浏览器与 API 运行时验证方式见 `docs/e2e-smoke.md`；生成报告只应由真实烟测运行更新。
