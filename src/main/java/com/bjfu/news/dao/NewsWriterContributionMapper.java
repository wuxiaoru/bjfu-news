package com.bjfu.news.dao;

import com.bjfu.news.entity.NewsWriterContribution;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface NewsWriterContributionMapper {
    int insert(NewsWriterContribution record);

    List<NewsWriterContribution> selectById(NewsWriterContribution record);
}