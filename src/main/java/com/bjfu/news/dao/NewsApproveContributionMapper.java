package com.bjfu.news.dao;

import com.bjfu.news.entity.NewsApproveContribution;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface NewsApproveContributionMapper {

    int insert(NewsApproveContribution record);

    List<NewsApproveContribution> selectAll();

    NewsApproveContribution selectById(Long id);
}