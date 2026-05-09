# Local Start

当前目录已经具备 Gradle 单体工程形态：

- 反编译参考源码：`src/main/java`
- 实际覆盖源码：`src/overlay/java`
- 资源：`src/main/resources`
- 运行依赖：`lib`
- jOOQ 生成源码：`build/generated-src/jooq/main`

## 编译验证

推荐先验证编译：

```bash
cd backend
./gradlew compileJava
```

当前已验证 `./gradlew compileJava` 可以通过，jOOQ 生成表对象和 overlay 源码均能正常编译。

## 本地依赖

默认 `local` profile 使用这些本地配置：

- MySQL：`127.0.0.1:3306`
- 数据库：`robot_monitor`
- MySQL 用户名：`root`
- MySQL 密码：`123456`
- Redis：`127.0.0.1:6380`
- Redis 密码：`1234%%6`
- 后端端口：`7075`
- 后端 context-path：`/api`

数据库初始化脚本：

```bash
mysql -uroot -p123456 < backend/sql/init.sql
```

默认后台账号：

- 用户名：`admin`
- 密码：`admin123`

## 启动配置

本地启动优先使用：

- `backend/src/main/resources/application-local.yml`
- `backend/sql/init.sql`

如需接入真实业务链路，再替换这些项目：

- OpenAI/模型服务地址和密钥
- Kafka 地址与账号
- DeepGlint 地址与密钥
- `robot.url`
- `robotmonitor.profile`

启动命令：

```bash
cd backend
./gradlew run
```

`run` 任务默认会注入 `spring.profiles.active=local`。如果要指定其他 profile：

```bash
./gradlew run -Dspring.profiles.active=dev
```

注意：

- 当前 `application-dev.yml` 是从部署包直接复制出来的，包含真实环境信息，默认不要直接用。
- `local` profile 已补充本地可注入的旅客服务 Bean，避免默认启动时因 `IPassengerService` 只存在 `dev/prod` 实现而缺 Bean。
- 最近一次端到端启动验证卡在本机 Docker daemon 未启动，且当前会话没有 sudo 密码，无法启动 MySQL/Redis 容器。
