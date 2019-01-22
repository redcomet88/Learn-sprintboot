package edu.jssvc.demo.controller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Description: 官方示例工程中的测试代码
 * @Author: redcomet
 * @Date: 2019-01-22-9:40
 */
@Controller
@EnableAutoConfiguration
public class SampleController {
    @RequestMapping("/")
    @ResponseBody
    String home(){
        return "Hello World!";
    }

    public static void main(String[] args)throws Exception{
        SpringApplication.run(SampleController.class, args);
    }
}
