package com.bjfu.news.dao;

import com.bjfu.news.entity.NewsWriterContribution;
import com.bjfu.news.req.ContributionReq;
import com.bjfu.news.req.IdsParam;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface NewsWriterContributionMapper {
    int insert(NewsWriterContribution record);

    NewsWriterContribution selectById(Long id);

    List<NewsWriterContribution> listByName(ContributionReq req);

    int getCount(ContributionReq req);

    int delete(IdsParam idsParam);
}