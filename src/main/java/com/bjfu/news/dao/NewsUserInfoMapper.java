package com.bjfu.news.dao;

import com.bjfu.news.entity.NewsUserInfo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface NewsUserInfoMapper {

     Long insertUserInfo(NewsUserInfo record);

//    int update(NewsUserInfo newsUserInfo);
//
//    NewsUserInfo selectById(Long id);
//
//    NewsUserInfo selectByEno(String eno);
}
