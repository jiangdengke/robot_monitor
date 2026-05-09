# 管理后台功能对比

本文档对比原部署包中的管理后台功能，以及当前 `admin_project` 重建后的实现状态。

## 状态定义

- `[x]` 已完成
- `[-]` 已有代码，但仍需联调、运行验证或外部服务接入
- `[ ]` 未完成

## 总体状态

| 项目 | 状态 | 当前说明 |
| --- | --- | --- |
| 后端源码合并 | `[x]` | 已合并到 `backend/src/main/java`，共 771 个 Java 文件 |
| jOOQ Mapper 恢复 | `[x]` | 业务 Mapper 已切到显式 jOOQ 实现，使用 `com.robotmonitor.jooq.generated.Tables.*` 生成表对象 |
| 后端全量编译 | `[x]` | `cd admin_project/backend && ./gradlew compileJava` 已通过 |
| 初始化 SQL | `[x]` | 已提供 `backend/sql/init.sql` |
| 前端旧静态包保留 | `[x]` | 原构建产物已归档到 `frontend/legacy-dist/` |
| 前端 `src` 工程源码 | `[x]` | 已建立 `Vite + Vue 3 + Element Plus` 源码工程，普通后台页统一接 CRUD，复杂页单独实现交互 |
| 原始 Vue 页面源码 1:1 恢复 | `[ ]` | 当前没有原始仓库，不能诚实描述为原始仓库 1:1 恢复；现在是基于部署产物和接口重建的可维护 `.vue` 源码 |
| 后端本地完整启动 | `[-]` | 代码和 local profile 已补齐到可启动形态；当前机器 Docker daemon 未启动且需要 sudo 密码，暂不能拉起 MySQL/Redis 继续验证 |
| 前后端端到端功能验证 | `[ ]` | 前端构建和后端编译均通过；真实接口链路仍需在 MySQL/Redis 可用后继续逐页验证 |

## 模块对比

| 模块 | 状态 | 当前说明 |
| --- | --- | --- |
| 登录认证 | `[-]` | 后端登录、验证码、注册代码已合并可编译；前端登录/注册页已有源码；仍需 Redis/数据库联调 |
| 系统管理 | `[x]` | 用户、角色、菜单、部门、岗位、字典、参数、通知、授权和个人中心均已接 Element Plus CRUD/表单页面 |
| 系统监控 | `[x]` | 在线用户、登录日志、操作日志、缓存、服务信息、任务和调度日志页面已接入；Redis/运行环境未做本地启动验证 |
| 机器人配置 | `[x]` | 机器人、图片、语音、区域、功能区、设备、餐桌、任务等配置页已接 CRUD、上传和表单弹窗 |
| 数字孪生 | `[x]` | 前端已实现地图、区域、机器人、旅客、预警联动；实时真机链路未做端到端验证 |
| 航班管理 | `[-]` | 航班、登机口、预警代码和 SQL 已有；Kafka/实时更新未验证 |
| 旅客管理 | `[-]` | 旅客、位置、预警日志代码和 SQL 已有；视觉和设备链路未验证 |
| 点餐管理 | `[x]` | 菜品、订单、餐桌、菜单计划页面已接列表和 CRUD 操作 |
| 巡检任务 | `[x]` | 任务配置页已接 CRUD；机器人服务和 Redis 队列未做端到端验证 |
| 机器人引导/控制 | `[-]` | 日志、回家、状态更新、任务控制代码已在；真机接口未联调 |
| AI 知识库/对话 | `[x]` | 知识库页、问题统计页已接真实接口；模型服务和语音服务未做端到端验证 |
| DeepGlint/视觉 | `[-]` | 管理后台相关接口和消费代码已在；原视觉后端不在当前单体内，链路未本地恢复 |
| 公共文件/资源 | `[x]` | 上传弹窗和头像上传已接接口；文件目录和静态映射未做本地启动验证 |
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
| 外部服务 | `[ ]` | Kafka、DeepGlint、AI、机器人、语音服务仍需本地 mock 或真实接入 |
| 全功能保证 | `[ ]` | 还没有完成真实登录、菜单、页面、业务接口的端到端验证 |

## 编译验证

当前后端全量编译已通过：

```bash
cd admin_project/backend
./gradlew compileJava
```

结果：

```text
BUILD SUCCESSFUL
```

仅剩少量 unchecked/unsafe 提示，不影响编译。

当前前端构建已通过：

```bash
cd admin_project/frontend
npm run build
```

结果：

```text
✓ built
```

前端已配置 Vue 与 Element Plus vendor 分包，构建不再出现默认大 chunk 警告。

## 结论

当前 `admin_project` 已经完成管理后台主体源码恢复：

- 后端源码、jOOQ Mapper、SQL 已落地，并且后端可全量编译。
- 前端不再只有静态包，已经补出可维护的 `frontend/src` Vue 源码工程。
- 普通后台页已具备查询、分页、新增、编辑、删除、详情、上传等管理后台能力。
- 数字孪生、桌台模型、表单构建器、代码生成、头像上传、授权、AI 统计和视频流页面已做专项交互。
- 原静态构建产物已保留在 `frontend/legacy-dist`，可继续作为行为对照。
- 代码已推送到 `git@github.com:jiangdengke/robot_monitor.git` 的 `main` 分支。

当前不能把项目描述为“原始仓库 1:1 完全恢复”或“已完成端到端生产验证”。更准确的状态是：

- 后端主体代码和 jOOQ 数据访问恢复完成，进入运行联调阶段。
- 前端源码恢复到可维护后台工程状态，并已通过构建。
- 后端本地完整启动和前后端端到端验证当前被本机 Docker daemon/sudo 权限阻塞，待 MySQL/Redis 可用后继续。
