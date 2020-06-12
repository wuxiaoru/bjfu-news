package com.bjfu.news.dao;

import com.bjfu.news.entity.NewsApproveContribution;
import org.apache.ibatis.annotations.Mapper;

import java.util.Collection;
import java.util.List;

@Mapper
public interface NewsApproveContributionMapper {

    int insert(NewsApproveContribution record);

    int update(NewsApproveContribution record);

    NewsApproveContribution selectById(Long id);

    NewsApproveContribution selectByCId(Long contributionId);

    List<NewsApproveContribution> selectByCIds(Collection<Long> cIds);

    List<NewsApproveContribution> selectByApproveId(Long approveId);

    List<NewsApproveContribution> selectByDate(String startTime, String endTime);
}