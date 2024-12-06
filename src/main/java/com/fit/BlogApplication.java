package com.fit;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ConfigurableApplicationContext;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Slf4j
@SpringBootApplication
public class BlogApplication extends SpringBootServletInitializer {

    public static void main(String[] args) throws UnknownHostException {
        ConfigurableApplicationContext run = SpringApplication.run(BlogApplication.class, args);
        String ip = InetAddress.getLocalHost().getHostAddress();
        String port = run.getEnvironment().getProperty("server.port");
        String item = run.getEnvironment().getProperty("server.servlet.context-path");
        log.info("\n---------------------------------------------------------\n" +
                "Application Blog is running! Access Urls:\n" +
                String.format("External:\thttp://%s:%s/%s\n", ip, port, item) +
                String.format("Local: \t\thttp://localhost:%s/%s", port, item) +
                "\n-----------------页面请部署 admin-web----------------------");
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(BlogApplication.class);
    }
}
