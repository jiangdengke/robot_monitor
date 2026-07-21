# 后端分层规范

当前 Kotlin 后端统一采用以下依赖方向：

```text
Controller -> Service -> Repository -> jOOQ / MySQL
```

文件上传是文件系统链路，不创建无意义的 Repository：

```text
FileController -> FileService -> FileSystem
```

## Controller

Controller 只负责 HTTP 适配：

- 声明路由、请求方法和参数来源。
- 提取 `Principal`、路径参数、查询参数和请求体。
- 处理 JWT Cookie 等依赖 Servlet API 的响应适配。
- 调用一个业务 Service 并返回 DTO。

Controller 不得依赖 Repository、`DSLContext`、jOOQ Record、生成 DAO 或生成 POJO。

## Service

Service 负责应用和业务逻辑：

- 校验业务输入并抛出 `BusinessException`。
- 设置业务默认值和转换布尔、时间等 API 字段。
- 将请求 DTO 转为 Repository `WriteData`。
- 将 Repository `Row` 转为响应 DTO。
- 编排一个或多个 Repository 操作。
- 使用 `@Transactional` 定义业务事务边界。

Service 不得直接构造 SQL，也不得引用 `DSLContext`、jOOQ 表、Record、生成 DAO 或生成 POJO。

## Repository

Repository 是唯一允许访问数据库的业务层：

- 注入 `DSLContext`。
- 定义 SELECT、JOIN、筛选、排序、INSERT、UPDATE 和 DELETE。
- 将 jOOQ Record 映射为 Repository 自有的 `Row`。
- 接收 Repository 自有的 `WriteData` 执行写入。
- 更新和删除方法返回受影响行数，由 Service 判断业务结果。

Repository 不得依赖 Controller、Service 或 API DTO，也不得抛业务异常、格式化 API 时间或返回 `ListResponse`。

## 当前业务边界

| Service | Repository | 数据范围 |
| --- | --- | --- |
| `UserService`、`SignService` | `UserRepository` | 用户、认证凭据 |
| `SpaceService` | `SpaceRepository` | 场地、区域、点位 |
| `DeviceService` | `DeviceRepository` | 设备、设备点位绑定 |
| `RobotService` | `RobotRepository` | 机器人 |
| `TaskService` | `TaskRepository` | 任务模板、任务执行日志 |
| `LogService` | `LogRepository` | 登录日志、操作日志 |

表中列出的是主要持久化边界。Service 可以组合多个窄 Repository 完成跨聚合校验，例如空间删除前检查设备、机器人和任务引用，设备绑定、机器人和任务写入前检查关联对象是否属于同一场地；SQL 仍只能存在于 Repository。

## 事务边界

事务放在由 Controller 或框架适配器调用的公开 Service 方法上。以下多语句操作必须保持同一事务：

- 注册用户：检查用户名后插入用户。
- 创建、更新用户：读取当前值、写入并重新查询。
- 删除设备：先删除设备点位绑定，再删除设备。
- 保存设备点位绑定：先删除旧复合键，再插入新绑定。
- 执行任务：读取任务模板，再插入任务执行日志。
- 删除场地、区域或点位：先检查下级引用，存在关联数据时拒绝删除，避免产生孤儿记录。

Repository 不定义业务事务，确保多个 Repository 操作能够加入同一个 Service 事务。

## DTO 与 Row 边界

- HTTP 请求和响应只使用 `dto/**` 中的 DTO。
- Repository 只暴露自身文件内的 `Row` 和 `WriteData`。
- jOOQ 生成类型只能出现在 `repository/**` 和数据库基础设施代码中。
- 时间格式、布尔编码和默认值属于 API/业务语义，必须在 Service 中处理。

## 检查命令

```bash
rg 'DSLContext|org\.jooq\.generated|org\.jooq\.impl' backend/src/main/kotlin/org/jdk/project/service
rg 'repository\.|DSLContext|org\.jooq' backend/src/main/kotlin/org/jdk/project/controller
rg 'org\.jdk\.project\.(dto|controller|service)' backend/src/main/kotlin/org/jdk/project/repository
./backend/gradlew -p backend test spotlessCheck
./backend/gradlew -p backend clean jooqCodegen compileKotlin compileJava bootJar
```

前三条静态分层命令均应无输出，两个 Gradle 命令均应成功退出。jOOQ 生成代码只加入主源码集，不加入测试源码集，也不进入 Spotless 格式检查；当前仓库没有测试源码时，Gradle 显示 `test NO-SOURCE` 属于正常成功结果。
