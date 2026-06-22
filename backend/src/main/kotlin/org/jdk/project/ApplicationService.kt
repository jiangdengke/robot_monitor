package org.jdk.project

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication(scanBasePackages = ["org.jdk.project", "org.jooq.generated"])
class ApplicationService {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            SpringApplication.run(ApplicationService::class.java, *args)
        }
    }
}
