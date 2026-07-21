// Spring Boot 打包任务类型，用于设置最终可执行 jar 名称
import org.springframework.boot.gradle.tasks.bundling.BootJar

// 统一管理 jOOQ 版本
val jooqVersion by extra("3.19.24")

plugins {
    // Java 支持
    java
    `java-library`
    application
    kotlin("jvm") version "1.9.25"
    kotlin("plugin.spring") version "1.9.25"
    // 覆盖率
    jacoco
    // Spring Boot 插件与依赖管理
    id("org.springframework.boot") version "3.3.9"
    id("io.spring.dependency-management") version "1.1.7"
    // OpenAPI 运行时仅需依赖，不需要 Gradle 插件
    // OpenAPI Gradle 插件（用于生成离线文档）
    id("org.springdoc.openapi-gradle-plugin") version "1.9.0"
    // jOOQ 代码生成
    id("org.jooq.jooq-codegen-gradle") version "3.19.24"
    // 代码格式化
    id("com.diffplug.spotless") version "7.1.0"
}

// 将 jOOQ 生成代码加入源码目录，便于编译
sourceSets {
    main {
        java {
            srcDir("build/generated-sources/jooq")
        }
    }
}

group = "org.jdk.project"
version = "1.0.0"
description = "脚手架"
java.sourceCompatibility = JavaVersion.VERSION_17

kotlin {
    jvmToolchain(17)
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    maven { url = uri("https://maven.aliyun.com/repository/central") }
    maven { url = uri("https://maven.aliyun.com/repository/public") }
    maven { url = uri("https://maven.aliyun.com/repository/gradle-plugin") }
    maven { url = uri("https://maven.aliyun.com/repository/apache-snapshots") }
    mavenCentral()
}

dependencies {
    // Spring Boot 基础能力
    implementation("org.springframework.boot:spring-boot-starter-data-redis")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-jooq")
    implementation("org.springframework.boot:spring-boot-starter-mail")
    implementation("org.springframework.boot:spring-boot-starter-cache")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-aop")
    // 工具库
    implementation("org.apache.commons:commons-lang3:3.17.0")
    implementation("org.apache.commons:commons-collections4:4.4")
    // OpenAPI UI（与 Spring Boot 3.3 兼容的稳定版本）
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.6.0")
    // jOOQ 元数据支持（解析 DDL）
    implementation("org.jooq:jooq-meta:$jooqVersion")
    // JWT
    implementation("com.auth0:java-jwt:4.4.0")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")

    implementation("com.github.ben-manes.caffeine:caffeine:3.2.1")

    // 运行时依赖
    runtimeOnly("com.mysql:mysql-connector-j:8.2.0")
    // developmentOnly("org.springframework.boot:spring-boot-devtools") // 避免 DevTools 类加载干扰

    // 测试（本项目已移除测试代码，如需测试可按需添加依赖）
    // jOOQ 代码生成依赖
    jooqCodegen("com.mysql:mysql-connector-j:8.2.0")
    jooqCodegen("org.jooq:jooq-codegen:$jooqVersion")
    jooqCodegen("org.jooq:jooq-meta-extensions:$jooqVersion")
    // 配置提示生成
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
    api("org.jspecify:jspecify:1.0.0")
}

// 配置 OpenAPI 生成任务（命中运行中的应用 http://localhost:8080/v3/api-docs）
openApi {
    apiDocsUrl.set("http://localhost:8080/v3/api-docs")
    outputDir.set(
        layout.buildDirectory
            .dir("openapi")
            .get()
            .asFile,
    )
    outputFileName.set("openapi.json")
}

// 兼容命令别名：generateOpenApiDoc -> generateOpenApiDocs
tasks.register("generateOpenApiDoc") {
    dependsOn("generateOpenApiDocs")
}

// 配置 BootJar 名称
tasks.withType<BootJar> {
    archiveFileName.set("jiangdk.jar")
}

application {
    mainClass.set("org.jdk.project.ApplicationService")
}

tasks.named<JavaExec>("run") {
    dependsOn("classes")
    jvmArgs = listOf("-Dfile.encoding=UTF-8")
}

tasks.named("compileJava") {
    dependsOn("jooqCodegen")
}

tasks.named("compileKotlin") {
    dependsOn("jooqCodegen")
}

// 使用 JUnit 5
tasks.withType<Test> {
    useJUnitPlatform()
}

// 生成测试覆盖率报告
tasks.test {
    finalizedBy(tasks.jacocoTestReport) // 测试后生成 Jacoco 报告
}

tasks.jacocoTestReport {
    dependsOn(tasks.test) // 生成报告前需要执行测试
}

jacoco {
    toolVersion = "0.8.13"
    reportsDirectory.set(layout.buildDirectory.dir("reports/jacoco"))
}

// 统一代码风格
spotless {
    // 只约束本次变更，避免格式化无关历史代码。
    ratchetFrom("HEAD")

    format("misc") {
        // define the files to apply `misc` to
        target("*.gradle.kts", "*.md", ".gitignore")
        // define the steps to apply to those files
        trimTrailingWhitespace()
        leadingTabsToSpaces()
        endWithNewline()
    }

    java {
        // jOOQ 生成代码不属于人工维护源码。
        target("src/**/*.java")
        // Google Java 格式
        googleJavaFormat("1.28.0").reflowLongStrings()
        formatAnnotations()
    }

    kotlin {
        target("src/**/*.kt")
        ktlint()
    }

    kotlinGradle {
        target("*.gradle.kts") // 默认作用于根目录下的 .gradle.kts
        ktlint() // 也可选 ktfmt()/prettier()
    }
}

// jOOQ 代码生成（从 DDL 脚本生成 POJO/DAO/Record）
jooq {
    configuration {
        generator {
            database {
                includes = ".*"
                excludes = "qrtz_.*"
                name = "org.jooq.meta.extensions.ddl.DDLDatabase"
                properties {
                    property {
                        key = "scripts" // DDL 脚本位置
                        value = "src/main/resources/db/schema.sql"
                    }
                    property {
                        key = "sort" // 脚本语义排序
                        value = "semantic"
                    }
                    property {
                        key = "unqualifiedSchema" // 未限定 Schema 处理
                        value = "none"
                    }
                    property {
                        key = "defaultNameCase" // 默认命名小写
                        value = "lower"
                    }
                    property {
                        key = "logExecutedQueries" // 打印执行的查询
                        value = "true"
                    }
                    property {
                        key = "logExecutionResults" // 打印执行结果
                        value = "true"
                    }
                }
                forcedTypes {
                    forcedType {
                        name = "OffsetDateTime" // TIMESTAMP -> OffsetDateTime
                        includeExpression = ".*"
                        includeTypes = "TIMESTAMP"
                    }
                }
            }
            generate {
                isDaos = true // 生成 DAO
                isRecords = true // 生成 Record
                isDeprecated = false
                isImmutablePojos = false
                isFluentSetters = true
                isSpringAnnotations = true
                isSpringDao = true
            }
            target {
                packageName = "org.jooq.generated" // 生成代码包名
            }
        }
    }
}
