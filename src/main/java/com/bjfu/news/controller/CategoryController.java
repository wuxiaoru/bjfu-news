package com.bjfu.news.controller;

import com.bjfu.news.service.NewsCategroyLoader;
import com.bjfu.news.service.NewsCategroyService;
import com.bjfu.news.untils.MapMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CategoryController {

    @Autowired
    private NewsCategroyService newsCategroyService;

    @Autowired
    private NewsCategroyLoader newsCategroyLoader;

    //新建
    @RequestMapping(value = "v1/category/insert", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public MapMessage inser(
            @RequestParam(required = false, defaultValue = "20") String name) {
        return MapMessage.successMessage();
    }

    //编辑
    @RequestMapping(value = "v1/category/edit", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public MapMessage edit(
            @RequestParam(required = false, defaultValue = "20") String name) {
        return MapMessage.successMessage();
    }

    //删除
    @RequestMapping(value = "v1/category/delete", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public MapMessage delete(
            @RequestParam(required = false, defaultValue = "20") String name) {
        return MapMessage.successMessage();
    }

    //所有
    @RequestMapping(value = "v1/category/list", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public MapMessage list(
            @RequestParam(required = false, defaultValue = "20") String name) {
        return MapMessage.successMessage();
    }
}
