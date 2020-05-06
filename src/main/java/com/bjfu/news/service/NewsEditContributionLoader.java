package com.bjfu.news.service;

import com.bjfu.news.entity.NewsEditContribution;
import com.bjfu.news.req.ContributionReq;

import java.util.List;

public interface NewsEditContributionLoader {

    int getCount(ContributionReq req);

    List<NewsEditContribution> selectByCId(Long contributionId);

    NewsEditContribution selectById(Long id);

    List<NewsEditContribution> list(ContributionReq req);
}
