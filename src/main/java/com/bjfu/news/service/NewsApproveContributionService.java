package com.bjfu.news.service;

import com.bjfu.news.entity.NewsApproveContribution;
import com.bjfu.news.req.IdsParam;

public interface NewsApproveContributionService {

    int delete(IdsParam idsParam);

    int update(NewsApproveContribution approveContribution);

    NewsApproveContribution create(NewsApproveContribution contribution);
}
