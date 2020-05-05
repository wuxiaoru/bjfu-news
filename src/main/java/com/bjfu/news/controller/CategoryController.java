package com.bjfu.news.controller;

import com.bjfu.news.service.NewsCategroyLoader;
import com.bjfu.news.service.NewsCategroyService;
import com.bjfu.news.untils.MapMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/v1/category")
public class CategoryController {

    @Autowired
    private NewsCategroyService newsCategroyService;

    @Autowired
    private NewsCategroyLoader newsCategroyLoader;

    //新建
    @RequestMapping(value = "insert", method = RequestMethod.POST)
    @ResponseBody
    public MapMessage insert(String name) {
        return MapMessage.successMessage();
    }

    //编辑
    @RequestMapping(value = "edit", method = RequestMethod.POST)
    @ResponseBody
    public MapMessage edit(Long id,String name) {
        return MapMessage.successMessage();
    }

    //删除
    @RequestMapping(value = "delete", method = RequestMethod.POST)
    @ResponseBody
    public MapMessage delete(Long id) {
        return MapMessage.successMessage();
    }

    //所有
    @RequestMapping(value = "list", method = RequestMethod.POST)
    @ResponseBody
    public MapMessage list(
            @RequestParam(required = false, defaultValue = "20") String name) {
        return MapMessage.successMessage();
    }
}
