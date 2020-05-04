package com.bjfu.news.controller;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1/")
public class HelloController {

    @RequestMapping(value = "hello",
            method = RequestMethod.GET, produces = "application/json;charset=utf-8")
    public String checkHealth() {
        return "hello world";
    }
}
