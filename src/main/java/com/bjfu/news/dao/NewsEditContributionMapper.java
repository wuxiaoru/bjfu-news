package com.bjfu.news.dao;

import com.bjfu.news.entity.NewsEditContribution;
import com.bjfu.news.req.ContributionReq;
import com.bjfu.news.req.IdsParam;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface NewsEditContributionMapper {
    int insert(NewsEditContribution record);

    int updateOpetation(NewsEditContribution record);

    List<NewsEditContribution> selectAll();

    NewsEditContribution selectById(Long id);

    NewsEditContribution selectByCId(Long contributionId);

    List<NewsEditContribution> selectByEditorId(Long editor);

    List<NewsEditContribution> list(ContributionReq req);

    int getCount(ContributionReq req);

    int delete(IdsParam idsParam);
}