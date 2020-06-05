package com.bjfu.news.dao;

import com.bjfu.news.entity.NewsUserRole;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface NewUserRoleMapper {

    Long insertUserRole(NewsUserRole record);

}
