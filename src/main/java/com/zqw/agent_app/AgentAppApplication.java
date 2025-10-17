package com.zqw.agent_app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
@MapperScan(basePackages = "com.zqw.agent_app.mapper")
public class AgentAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(AgentAppApplication.class, args);
    }

}
