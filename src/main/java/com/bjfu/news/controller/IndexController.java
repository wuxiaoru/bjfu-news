package com.bjfu.news.controller;

import com.bjfu.news.constant.ContributionStatus;
import com.bjfu.news.constant.UserRoleType;
import com.bjfu.news.untils.MapMessage;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class IndexController {

    @RequestMapping(value = "/")
    public String index() {
        return "index/index"; //当浏览器输入/index时，会返回 /templates/index.html页面
    }

    @RequestMapping(value = "enum", method = RequestMethod.GET)
    @ResponseBody
    public MapMessage enumList() {
        return MapMessage.successMessage().add("status", ContributionStatus.CODE_MAPPING).add("role", UserRoleType.NAME_MAPPING);
    }
}
