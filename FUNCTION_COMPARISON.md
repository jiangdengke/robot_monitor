# 管理后台功能对比

本文档对比原部署包中的管理后台功能，以及当前仓库根目录重建后的实现状态。

## 状态定义

- `[x]` 已完成
- `[-]` 已有代码，但仍需联调、运行验证或外部服务接入
- `[ ]` 未完成

## 总体状态

| 项目 | 状态 | 当前说明 |
| --- | --- | --- |
| 后端源码合并 | `[x]` | 已合并到 `backend/src/main/java`，共 771 个 Java 文件 |
| jOOQ Mapper 恢复 | `[x]` | 业务 Mapper 已切到显式 jOOQ 实现，使用 `com.robotmonitor.jooq.generated.Tables.*` 生成表对象 |
| 后端全量编译 | `[x]` | `cd backend && ./gradlew compileJava` 已通过 |
| 初始化 SQL | `[x]` | 已提供 `backend/sql/init.sql` |
| 前端旧静态包保留 | `[x]` | 原构建产物已归档到 `frontend/legacy-dist/` |
| 前端 `src` 工程源码 | `[x]` | 已建立 `Vite + Vue 3 + Element Plus` 源码工程，普通后台页统一接 CRUD，复杂页单独实现交互 |
| 原始 Vue 页面源码 1:1 恢复 | `[ ]` | 当前没有原始仓库，不能诚实描述为原始仓库 1:1 恢复；现在是基于部署产物和接口重建的可维护 `.vue` 源码 |
| 后端本地完整启动 | `[x]` | 已用本地 MySQL `robot_monitor` 和 Redis 启动通过：`cd backend && ./gradlew run`，服务地址 `http://127.0.0.1:7075/api` |
| 前后端端到端功能验证 | `[x]` | 已完成本地浏览器逐页烟测：`cd frontend && npm run e2e:smoke`，57/57 页面通过，报告见 `docs/e2e-smoke-report.md` |

## 模块对比

| 模块 | 状态 | 当前说明 |
| --- | --- | --- |
| 登录认证 | `[x]` | 后端登录、验证码开关、注册代码已合并；`admin/admin123` 已在本地 MySQL/Redis 环境登录成功并返回 token |
| 系统管理 | `[x]` | 用户、角色、菜单、部门、岗位、字典、参数、通知、授权和个人中心均已接 Element Plus CRUD/表单页面 |
| 系统监控 | `[x]` | 在线用户、登录日志、操作日志、缓存、服务信息、任务和调度日志页面已接入；服务监控已补 CPU/内存/JVM/磁盘完整视图 |
| 机器人配置 | `[x]` | 机器人、图片、语音、区域、功能区、设备、餐桌、任务等配置页已接 CRUD、上传和表单弹窗 |
| 数字孪生 | `[x]` | 前端已实现地图、区域、机器人、旅客、预警、巡检、桌台联动；引导、停止、人工提醒、机器人提醒、巡检处理已通过本地任务 mock API 烟测 |
| 航班管理 | `[x]` | 航班列表、CRUD、导入临时航班、导出和表单字段已接入；Kafka 实时更新按外部链路边界保留 |
| 旅客管理 | `[x]` | 在厅旅客、出厅旅客、旅客 CRUD、预警日志和通行统计已接入；视觉和设备链路按外部链路边界保留 |
| 点餐管理 | `[x]` | 菜品、订单、餐桌、菜单计划页面已接列表和 CRUD 操作 |
| 巡检任务 | `[x]` | 任务配置页已接 CRUD；机器人服务和 Redis 队列未做端到端验证 |
| 机器人引导/控制 | `[-]` | 日志、回家、状态更新、任务控制和数字孪生提醒已接本地任务 mock；真机接口未联调 |
| AI 知识库/对话 | `[x]` | 知识库、引导日志、问题统计、自动分类和聊天接口已接本地 jOOQ/mock；真实模型和语音服务未做端到端验证 |
| DeepGlint/视觉 | `[-]` | 管理后台相关接口和消费代码已在；原视觉后端不在当前单体内，链路未本地恢复 |
| 公共文件/资源 | `[x]` | 上传弹窗、头像上传、用户导入、航班临时文件导入和视频流 mock 控制已接接口；本地后端启动链路已补 RedisTemplate 和 `/profile` 资源兼容 |
| 定时任务 | `[x]` | Quartz 任务和日志 Mapper 已切 jOOQ，前端任务/日志页已接 CRUD；任务实际调度未验证 |
| 代码生成 | `[x]` | 后端生成器 Mapper 已切 jOOQ；前端已接表导入、字段联动、预览、同步和生成操作 |

## 当前已落地内容

| 类型 | 路径 | 说明 |
| --- | --- | --- |
| 后端源码 | `backend/src/main/java` | 管理后台后端和内部模块源码已合并 |
| jOOQ 显式 Mapper | `backend/src/overlay/java/com/robotmonitor/**/mapper/jooq` | 管理后台业务 Mapper 已按模块实现 |
| 后端资源 | `backend/src/main/resources` | `application*.yml`、`logback.xml` 等；旧 MyBatis XML 仅作为反编译参考保留 |
| 初始化 SQL | `backend/sql/init.sql` | 本地 bootstrap 表结构和示例数据 |
| 前端源码工程 | `frontend/src` | Vue 3 + Vite + Element Plus 源码工程，普通后台页统一 CRUD，复杂页独立实现 |
| 前端旧构建产物 | `frontend/legacy-dist` | 原 Vue 管理后台打包产物归档 |
| 后端编译输出 | `backend/build/classes` | 当前全量编译输出 |

## 仍未等同原项目完整恢复的部分

| 项目 | 状态 | 说明 |
| --- | --- | --- |
| 原始 Vue 源码 | `[ ]` | 当前没有原始仓库，不能诚实描述为 1:1 原始源码恢复；当前是可维护重建源码 |
| 原始 Maven 多模块工程 | `[ ]` | 当前是单体 Spring Boot Gradle 工程，不是完整原始 Maven 多模块仓 |
| 生产数据库 | `[ ]` | 目前只有重建的 `init.sql`，没有生产库 dump |
| 外部服务 | `[-]` | 机器人和语音相关后台按钮已接本地 mock；Kafka、DeepGlint、AI 模型和真实硬件仍需继续 mock 或真实接入 |
| 全功能保证 | `[-]` | 管理后台本地登录、菜单、核心业务接口和 57 个页面浏览器逐页烟测已通过；真实硬件/视觉/语音/AI/Kafka 外部链路仍按 mock 边界处理 |

## 编译验证

当前后端全量编译和本地启动已通过：

```bash
cd backend
./gradlew compileJava
```

结果：

```text
BUILD SUCCESSFUL
```

仅剩少量 unchecked/unsafe 提示，不影响编译。

本地启动验证：

```bash
cd backend
./gradlew run
```

结果：`Started RobotMonitorApplication`，端口 `7075`，context-path `/api`。

已通过核心接口烟测：登录、`/getInfo`、`/getRouters`、用户/角色/菜单、机器人配置、餐桌配置、旅客、餐饮、数字孪生、缓存、代码生成、餐桌状态写入和数字孪生动作。

当前前端构建已通过：

```bash
cd frontend
npm run build
```

结果：

```text
✓ built
```

前端已配置 Vue 与 Element Plus vendor 分包，构建不再出现默认大 chunk 警告。

## 结论

当前仓库已经完成管理后台主体源码恢复：

- 后端源码、jOOQ Mapper、SQL 已落地，并且后端可全量编译。
- 前端不再只有静态包，已经补出可维护的 `frontend/src` Vue 源码工程。
- 普通后台页已具备查询、分页、新增、编辑、删除、详情、上传等管理后台能力。
- 数字孪生、桌台模型、AI 知识库/引导日志/问题统计、表单构建器、代码生成、头像上传、授权和视频流页面已做专项交互。
- 原静态构建产物已保留在 `frontend/legacy-dist`，可继续作为行为对照。
- 代码已推送到 `git@github.com:jiangdengke/robot_monitor.git` 的 `main` 分支。

当前不能把项目描述为“原始仓库 1:1 完全恢复”或“已完成端到端生产验证”。更准确的状态是：

- 后端主体代码、jOOQ 数据访问和本地启动链路恢复完成，核心 API 已完成烟测。
- 前端源码恢复到可维护后台工程状态，并已通过构建和 57 个页面浏览器逐页烟测。
- 剩余差距主要是真实硬件/视觉/语音/AI/Kafka 外部链路接入，以及无法从部署产物还原的原始仓库逐行源码。
