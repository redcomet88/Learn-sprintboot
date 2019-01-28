package com.chensi.yghy.configuration;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

/**
 * @Description: 入口
 * @Author: redcomet
 * @Date: 2019-01-22-14:44
 */

@SpringBootApplication
@ComponentScan(basePackages = "com.chensi")
public class Entry extends SpringBootServletInitializer{
 /*
    public static void main(String[] args)throws Exception{
        SpringApplication.run(Entry.class, args);
    }
   */
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder){
        return builder.sources(Entry.class);
    }

}
