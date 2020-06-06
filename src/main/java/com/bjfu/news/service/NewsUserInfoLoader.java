package com.bjfu.news.service;

import com.bjfu.news.entity.NewsUserInfo;
import com.bjfu.news.req.UserReq;

import java.util.List;

public interface NewsUserInfoLoader {

    List<NewsUserInfo> list(UserReq req);
}
