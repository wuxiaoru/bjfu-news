package com.bjfu.news.controller;

import com.bjfu.news.constant.UserRoleType;
import com.bjfu.news.entity.NewsUserInfo;
import com.bjfu.news.entity.NewsUserRole;
import com.bjfu.news.model.UserList;
import com.bjfu.news.req.UserInfoCreateParam;
import com.bjfu.news.req.UserReq;
import com.bjfu.news.untils.MapMessage;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.*;

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
        MapMessage check = check(param);
        if (!check.isSuccess()) {
            return check;
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
        List<UserList> list = new ArrayList<>();
        for (NewsUserInfo userInfo : userInfos) {
            UserList userList = new UserList();
            BeanUtils.copyProperties(userInfo, userList);
            list.add(userList);
        }
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> finalMap = getFinalMap(count, size, page, list, map);
        return MapMessage.successMessage().add("data", finalMap);
    }

    @RequestMapping(value = "delete.vpage", method = RequestMethod.POST)
    @ResponseBody
    public MapMessage insert(@Validated @NotNull @Min(value = 1, message = "id必须大于0") Long id) {
        NewsUserInfo newsUserInfo = newsUserInfoLoader.loadById(id);
        if (Objects.isNull(newsUserInfo)) {
            return MapMessage.errorMessage().add("info", "id有误");
        }
        int delete = newsUserInfoService.delete(id);
        if (delete <= 0) {
            return MapMessage.errorMessage().add("info", "删除失败");
        }
        return MapMessage.successMessage();
    }

    @RequestMapping(value = "edit.vpage", method = RequestMethod.POST)
    @ResponseBody
    public MapMessage edit(@Validated @RequestBody UserInfoCreateParam param) {
        if (param.getId() == null || param.getId() <= 0) {
            return MapMessage.errorMessage().add("info", "id有误");
        }
        NewsUserInfo newsUserInfo = newsUserInfoLoader.loadById(param.getId());
        if (Objects.isNull(newsUserInfo)) {
            return MapMessage.errorMessage().add("info", "id有误");
        }
        MapMessage check = check(param);
        if (!check.isSuccess()) {
            return check;
        }
        BeanUtils.copyProperties(param, newsUserInfo);
        int result = newsUserInfoService.update(newsUserInfo);
        if (result <= 0) {
            return MapMessage.errorMessage().add("info", "编辑失败");
        }
        return MapMessage.successMessage();
    }

    private MapMessage check(UserInfoCreateParam param) {
        if (param.getEno() == null || param.getEno().isEmpty()) {
            return MapMessage.errorMessage().add("info", "职工号有误");
        }

        if (param.getUserName() == null || param.getUserName().isEmpty()) {
            return MapMessage.errorMessage().add("info", "姓名有误");
        }

        if (param.getUnit() == null || param.getUnit().isEmpty()) {
            return MapMessage.errorMessage().add("info", "单位有误");
        }
        return MapMessage.successMessage();
    }

    @RequestMapping(value = "role.vpage", method = RequestMethod.GET)
    @ResponseBody
    public MapMessage roleList(@Validated @NotNull @Min(value = 1, message = "id必须大于0") Long id) {
        NewsUserInfo newsUserInfo = newsUserInfoLoader.loadById(id);
        if (Objects.isNull(newsUserInfo)) {
            return MapMessage.errorMessage().add("info", "id有误");
        }
        List<NewsUserRole> newsUserRoles = newsUserInfoLoader.loadByUserId(id);
        return MapMessage.successMessage().add("role", newsUserRoles);
    }
}
