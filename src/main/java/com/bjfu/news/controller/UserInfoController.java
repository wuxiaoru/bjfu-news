package com.bjfu.news.controller;

import com.bjfu.news.constant.UserRoleType;
import com.bjfu.news.entity.NewsUserInfo;
import com.bjfu.news.req.UserInfoCreateParam;
import com.bjfu.news.req.UserReq;
import com.bjfu.news.untils.MapMessage;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/v1/user/info")
public class UserInfoController extends AbstractNewsController {

    //新增
    @RequestMapping(value = "create.vpage", method = RequestMethod.POST)
    @ResponseBody
    public MapMessage create(@Validated @RequestBody UserInfoCreateParam param) {
        if (param.getRoleType() == null || param.getRoleType().isEmpty() || !UserRoleType.NAME_MAPPING.keySet().contains(param.getRoleType())) {
            return MapMessage.errorMessage().add("info", "角色有误");
        }
        if (param.getEno() == null || param.getEno().isEmpty()) {
            return MapMessage.errorMessage().add("info", "职工号有误");
        }

        if (param.getUserName() == null || param.getUserName().isEmpty()) {
            return MapMessage.errorMessage().add("info", "姓名有误");
        }

        if (param.getUnit() == null || param.getUnit().isEmpty()) {
            return MapMessage.errorMessage().add("info", "单位有误");
        }
        MapMessage result = newsUserInfoService.createUserInfo(param);
        if (!result.isSuccess()) {
            return MapMessage.errorMessage().add("info", "添加失败");
        }
        return MapMessage.successMessage();
    }

    //列表
    @RequestMapping(value = "list.vpage", method = RequestMethod.POST)
    @ResponseBody
    public MapMessage insert(@Validated @RequestBody UserReq req) {
        if (req.getRoleType() == null || req.getRoleType().isEmpty() || !UserRoleType.NAME_MAPPING.keySet().contains(req.getRoleType())) {
            return MapMessage.errorMessage().add("info", "角色有误");
        }
        int size = req.getSize() != null ? req.getSize() : 10;
        int page = req.getPage() != null ? req.getPage() : 0;
        req.setStart((page - 1) * size > 0 ? (page - 1) * size : 0);
        req.setSize(size);
        int count = newsUserInfoLoader.getCount(req);
        List<NewsUserInfo> userInfos = newsUserInfoLoader.list(req);
        int maxPage = count % size == 0 ? count / size : count / size + 1;
        Map<String, Object> map = new HashMap<>();
        map.put("list", userInfos);
        map.put("pageSize", page);
        map.put("totalCount", count);
        map.put("maxPage", maxPage);
        return MapMessage.successMessage().add("data", map);
    }
}
