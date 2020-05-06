package com.bjfu.news.dao;

import com.bjfu.news.entity.NewsApproveContribution;
import com.bjfu.news.req.ContributionReq;
import com.bjfu.news.req.IdsParam;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface NewsApproveContributionMapper {

    int insert(NewsApproveContribution record);

    int updateOpetation(NewsApproveContribution record);

    List<NewsApproveContribution> selectAll();

    NewsApproveContribution selectById(Long id);

    List<NewsApproveContribution> selectByCId(Long contributionId);

    List<NewsApproveContribution> selectByApproveId(Long approveId);

    List<NewsApproveContribution> list(ContributionReq req);

    int getCount(ContributionReq req);

    int delete(IdsParam idsParam);
}