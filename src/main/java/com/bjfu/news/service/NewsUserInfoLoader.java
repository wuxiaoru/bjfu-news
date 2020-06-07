package com.bjfu.news.service;

import com.bjfu.news.entity.NewsUserInfo;
import com.bjfu.news.entity.NewsUserRole;
import com.bjfu.news.req.UserReq;

import java.util.List;

public interface NewsUserInfoLoader {

    List<NewsUserInfo> list(UserReq req);

    NewsUserInfo loadById(Long userId);

    List<NewsUserRole> loadByRole(String roleType);
}