package com.bjfu.news.controller;

import com.bjfu.news.constant.ContributionStatus;
import com.bjfu.news.constant.UserRoleType;
import com.bjfu.news.untils.MapMessage;
import com.neusoft.education.tp.sso.client.filter.CASFilterRequestWrapper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author anne
 */
@RestController
@RequestMapping("/v1/news")
public class IndexController {

    @RequestMapping(value = "enum.vpage", method = RequestMethod.GET)
    public MapMessage enumList(HttpServletRequest request) {
        CASFilterRequestWrapper reqWrapper = new CASFilterRequestWrapper(request);
        String userID = reqWrapper.getRemoteUser();
        return MapMessage.successMessage().add("status", ContributionStatus.CODE_MAPPING)
                .add("role", UserRoleType.NAME_MAPPING)
                .add("approveStatus", ContributionStatus.APPROVE_MAPPING)
                .add("editStatus", ContributionStatus.EDITOR_MAPPING);
    }
}
