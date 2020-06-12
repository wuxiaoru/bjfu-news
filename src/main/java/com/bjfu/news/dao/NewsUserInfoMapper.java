package com.bjfu.news.dao;

import com.bjfu.news.entity.NewsUserInfo;
import com.bjfu.news.req.UserReq;
import org.apache.ibatis.annotations.Mapper;

import java.util.Collection;
import java.util.List;

@Mapper
public interface NewsUserInfoMapper {

    Long insertUserInfo(NewsUserInfo record);

    //    int update(NewsUserInfo newsUserInfo);
//
    NewsUserInfo selectById(Long id);

    List<NewsUserInfo> selectByIds(Collection<Long> ids);
//
//    NewsUserInfo selectByEno(String eno);

    List<NewsUserInfo> list(UserReq req);

    List<NewsUserInfo> page(UserReq req);

    int getCount(UserReq req);
}
