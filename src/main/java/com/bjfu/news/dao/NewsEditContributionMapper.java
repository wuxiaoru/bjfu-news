package com.bjfu.news.dao;

import com.bjfu.news.entity.NewsEditContribution;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface NewsEditContributionMapper {
    int insert(NewsEditContribution record);

    List<NewsEditContribution> selectAll();
}