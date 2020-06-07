package com.bjfu.news.service.impl;

import com.bjfu.news.dao.NewUserRoleMapper;
import com.bjfu.news.dao.NewsUserInfoMapper;
import com.bjfu.news.entity.NewsUserInfo;
import com.bjfu.news.entity.NewsUserRole;
import com.bjfu.news.req.UserReq;
import com.bjfu.news.service.NewsUserInfoLoader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class NewsUserInfoLoaderImpl implements NewsUserInfoLoader {

    @Autowired
    private NewUserRoleMapper newUserRoleMapper;

    @Autowired
    private NewsUserInfoMapper newsUserInfoMapper;

    @Override
    public List<NewsUserInfo> list(UserReq req) {
        List<NewsUserRole> userIds = newUserRoleMapper.loadByRole(req.getRoleType());
        if (CollectionUtils.isEmpty(userIds)) {
            return Collections.emptyList();
        }
        req.setUserIds(userIds.stream().map(NewsUserRole::getUserId).collect(Collectors.toList()));
        return newsUserInfoMapper.list(req);
    }

    @Override
    public NewsUserInfo loadById(Long userId) {
        return newsUserInfoMapper.selectById(userId);
    }

    @Override
    public List<NewsUserRole> loadByRole(String roleType) {
        return newUserRoleMapper.loadByRole(roleType);
    }
}
