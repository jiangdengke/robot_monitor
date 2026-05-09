package com.robotmonitor;

import com.github.pagehelper.autoconfigure.PageHelperAutoConfiguration;
import org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignAutoConfiguration;

@EnableFeignClients
@ImportAutoConfiguration({FeignAutoConfiguration.class})
@SpringBootApplication(exclude = {
    DataSourceAutoConfiguration.class,
    MybatisAutoConfiguration.class,
    PageHelperAutoConfiguration.class
})
public class RobotMonitorApplication {
    public static void main(String[] args) {
        SpringApplication.run(RobotMonitorApplication.class, args);
    }
}
