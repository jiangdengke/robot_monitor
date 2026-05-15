# moban_java 后端 API 清单

当前单体后端已落地的主要接口如下。

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

## 基础配置

- `GET /config/lounges`
- `POST /config/lounges`
- `PUT /config/lounges/{id}`
- `DELETE /config/lounges/{id}`
- `GET /config/regions`
- `POST /config/regions`
- `PUT /config/regions/{id}`
- `DELETE /config/regions/{id}`
- `GET /config/areas`
- `POST /config/areas`
- `PUT /config/areas/{id}`
- `DELETE /config/areas/{id}`
- `GET /config/images`
- `POST /config/images`
- `PUT /config/images/{id}`
- `DELETE /config/images/{id}`
- `GET /config/audios`
- `POST /config/audios`
- `PUT /config/audios/{id}`
- `DELETE /config/audios/{id}`
- `GET /config/robot-audios`
- `GET /config/devices`
- `POST /config/devices`
- `PUT /config/devices/{id}`
- `DELETE /config/devices/{id}`
- `POST /config/device-region-bindings`
- `DELETE /config/device-region-bindings/{deviceId}/{regionId}`
- `GET /config/tables`
- `POST /config/tables`
- `PUT /config/tables/{id}`
- `DELETE /config/tables/{id}`
- `GET /config/robots`
- `POST /config/robots`
- `PUT /config/robots/{id}`
- `DELETE /config/robots/{id}`
- `GET /config/tasks`
- `POST /config/tasks`
- `PUT /config/tasks/{id}`
- `DELETE /config/tasks/{id}`
- `POST /config/tasks/{id}/run`
- `GET /config/complaints`
- `POST /config/complaints`
- `PUT /config/complaints/{id}`
- `DELETE /config/complaints/{id}`

## 统计分析

- `GET /statistics/in-lounge`
- `GET /statistics/outgoing`
- `GET /statistics/access-temp`
- `GET /statistics/inquiry`
- `GET /statistics/guide`

## 餐食管理

- `GET /foods/items`
- `POST /foods/items`
- `PUT /foods/items/{id}`
- `DELETE /foods/items/{id}`
- `GET /foods/daily-menus`
- `POST /foods/daily-menus`
- `PUT /foods/daily-menus/{id}`
- `DELETE /foods/daily-menus/{id}`
- `GET /foods/plans`
- `POST /foods/plans`
- `PUT /foods/plans/{id}`
- `DELETE /foods/plans/{id}`
- `GET /foods/orders`
- `POST /foods/orders`
- `PUT /foods/orders/{id}`
- `DELETE /foods/orders/{id}`
- `POST /foods/orders/{id}/receive`
- `POST /foods/orders/{id}/finish`
- `POST /foods/orders/{id}/cancel`

## 日志管理

- `GET /monitor/login-logs`
- `DELETE /monitor/login-logs`
- `GET /monitor/operation-logs`
- `DELETE /monitor/operation-logs`

## 知识库

- `GET /knowledge`
- `GET /knowledge/{id}`
- `POST /knowledge`
- `PUT /knowledge/{id}`
- `DELETE /knowledge/{id}`

## 文件

- `POST /files`

## 当前验证

- `moban_java` 已通过 `compileJava`
- 数据模型在 `src/main/resources/db/schema.sql`
- 当前默认使用 H2 内存库初始化
- 联调时如启用相关缓存能力，建议本地准备 Redis
