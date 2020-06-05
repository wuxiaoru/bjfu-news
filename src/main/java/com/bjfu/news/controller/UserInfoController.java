package com.bjfu.news.controller;

import com.bjfu.news.req.UserInfoCreateParam;
import com.bjfu.news.untils.MapMessage;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/v1/info")
public class UserInfoController extends AbstractNewsController {

    //新增
    @RequestMapping(value = "insert", method = RequestMethod.POST)
    @ResponseBody
    public MapMessage insert(@Validated @RequestBody UserInfoCreateParam param) {
        MapMessage result = newsUserInfoService.createUserInfo(param);
        if (!result.isSuccess()) {
            return MapMessage.errorMessage().add("info", "添加失败");
        }
        return MapMessage.successMessage();
    }
}
