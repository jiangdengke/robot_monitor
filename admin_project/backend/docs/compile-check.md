# Compile Check

已完成的验证：

```bash
cd admin_project/backend
./gradlew compileJava
```

结果：
- `BUILD SUCCESSFUL`
- jOOQ 生成代码可正常生成并参与编译。
- 业务 Mapper 已使用显式 jOOQ 实现，不再依赖 MyBatis XML。

说明：
- `src/main/java` 仍作为反编译参考保留。
- 当前实际编译入口是 `src/overlay/java` 和 `build/generated-src/jooq/main`。
