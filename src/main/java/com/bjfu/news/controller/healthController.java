package com.bjfu.news.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class healthController {
    @RequestMapping(value = "health", method = RequestMethod.GET)
    @ResponseBody
    public String enumList() {
        return "ok";
    }
}
