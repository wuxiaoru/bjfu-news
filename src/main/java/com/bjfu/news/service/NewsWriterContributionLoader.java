package com.bjfu.news.service;

import com.bjfu.news.entity.NewsWriterContribution;
import com.bjfu.news.req.ContributionReq;

import java.util.List;

public interface NewsWriterContributionLoader {

    List<NewsWriterContribution> listByName(ContributionReq req);

    int getCount(ContributionReq req);

    NewsWriterContribution selectById(Long id);
}
