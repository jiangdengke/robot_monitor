# Local Start

当前目录已经具备 Gradle 单体工程形态：

- 反编译参考源码：`src/main/java`
- 实际覆盖源码：`src/overlay/java`
- 资源：`src/main/resources`
- 运行依赖：`lib`
- jOOQ 生成源码：`build/generated-src/jooq/main`

推荐先验证编译：

```bash
cd admin_project/backend
./gradlew compileJava
```

如果后续要做本地启动，优先使用：

- `admin_project/backend/src/main/resources/application-local.yml`
- `admin_project/backend/sql/init.sql`

至少替换这些项目：
- MySQL 地址、用户名、密码
- Redis 地址、密码
- OpenAI/模型服务地址和密钥
- Kafka 地址与账号
- DeepGlint 地址与密钥
- `robot.url`
- `robotmonitor.profile`

启动命令：

```bash
cd admin_project/backend
./gradlew run -Dspring.profiles.active=local
```

注意：
- 当前 `application-dev.yml` 是从部署包直接复制出来的，包含真实环境信息。
- 启动前应先改成本地可用值，或直接新建你自己的本地 profile 配置。
- 目前按用户指示还没做“后端本地完整启动”和“前后端端到端验证”。
