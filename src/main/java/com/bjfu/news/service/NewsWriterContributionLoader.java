package com.bjfu.news.service;

import com.bjfu.news.entity.NewsWriterContribution;
import com.bjfu.news.req.ContributionReq;

import java.util.Collection;
import java.util.List;

public interface NewsWriterContributionLoader {

    List<NewsWriterContribution> pageByName(ContributionReq req);

    List<NewsWriterContribution> listByName(String name);

    int getCount(ContributionReq req);

    NewsWriterContribution selectById(Long id);

    List<NewsWriterContribution> selectByIds(Collection<Long> ids);
}
