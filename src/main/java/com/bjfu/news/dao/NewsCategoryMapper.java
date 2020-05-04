package com.bjfu.news.dao;

import com.bjfu.news.entity.NewsCategory;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface NewsCategoryMapper {
    int insert(NewsCategory record);

    List<NewsCategory> selectAll();

    NewsCategory selectById(Long id);
}