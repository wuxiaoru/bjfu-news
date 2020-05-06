package com.bjfu.news.service;

import com.bjfu.news.entity.NewsEditContribution;
import com.bjfu.news.req.IdsParam;

public interface NewsEditContributionService {

    int delete(IdsParam idsParam);

    int updateOperation(NewsEditContribution newsEditContribution);

}
