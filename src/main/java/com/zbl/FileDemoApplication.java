package com.zbl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * @Author: zbl
 * @Date: Created in 16:40 2020/8/25
 * @Description:
 * @Version: $
 */

@SpringBootApplication
public class FileDemoApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(FileDemoApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(FileDemoApplication.class, args);
    }

}
