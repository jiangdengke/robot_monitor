package com.robotmonitor.framework.config;

import java.lang.reflect.Proxy;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyBatisConfig {
    @Bean
    public SqlSessionFactory sqlSessionFactory() {
        org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
        return (SqlSessionFactory) Proxy.newProxyInstance(
            SqlSessionFactory.class.getClassLoader(),
            new Class<?>[] {SqlSessionFactory.class},
            (proxy, method, args) -> switch (method.getName()) {
                case "getConfiguration" -> configuration;
                case "toString" -> "DisabledSqlSessionFactory(jOOQ)";
                case "hashCode" -> System.identityHashCode(proxy);
                case "equals" -> args != null && args.length == 1 && proxy == args[0];
                default -> throw new UnsupportedOperationException("MyBatis has been replaced by jOOQ mappers.");
            }
        );
    }
}
