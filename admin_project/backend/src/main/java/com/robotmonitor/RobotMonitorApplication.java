/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.boot.SpringApplication
 *  org.springframework.boot.autoconfigure.ImportAutoConfiguration
 *  org.springframework.boot.autoconfigure.SpringBootApplication
 *  org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
 *  org.springframework.cloud.openfeign.EnableFeignClients
 *  org.springframework.cloud.openfeign.FeignAutoConfiguration
 */
package com.robotmonitor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignAutoConfiguration;

@EnableFeignClients
@ImportAutoConfiguration(value={FeignAutoConfiguration.class})
@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
public class RobotMonitorApplication {
    public static void main(String[] args) {
        SpringApplication.run(RobotMonitorApplication.class, (String[])args);
    }
}
