package com.bjfu.news.service;

import com.bjfu.news.entity.NewsApproveContribution;
import com.bjfu.news.req.ContributionReq;

import java.util.List;

public interface NewsApproveContributionLoader {

    int getCount(ContributionReq req);

    NewsApproveContribution selectByCId(Long contributionId);

    List<NewsApproveContribution> list(ContributionReq req);

    NewsApproveContribution selectById(Long id);
}
