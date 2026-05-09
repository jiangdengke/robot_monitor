# admin_backend

这是从 `robot-monitor-admin.jar` 反编译并整理出来的管理后台后端目录，现在按单体 Spring Boot Gradle 工程维护。

当前状态：
- `src/main/java` 是反编译源码参考。
- `src/overlay/java` 是当前实际参与编译和运行的覆盖源码。
- `src/main/resources` 是从 JAR 解压出来的配置和资源。
- `lib` 保留原始运行依赖 JAR。
- `sql/init.sql` 提供本地表结构和示例数据。
- `original-poms` 和 `internal-modules` 保留原始 Maven 元数据和内部模块反编译参考。

已验证：
- `./gradlew compileJava` 可以通过。
- 业务 Mapper 已从 MyBatis XML 切到显式 jOOQ 实现。
- 旧 MyBatis XML 仍保留在 `src/main/resources/mapper` 作为反编译参考，但不再进入运行配置同步。

常用命令：

```bash
cd admin_project/backend
./gradlew compileJava
```

本地启动仍需先准备 MySQL、Redis 和配置。按用户当前指示，后端本地完整启动和前后端端到端验证暂未执行。
