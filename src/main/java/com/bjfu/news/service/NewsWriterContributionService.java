package com.bjfu.news.service;

import com.bjfu.news.entity.NewsContribution;
import com.bjfu.news.req.IdsParam;

public interface NewsWriterContributionService {

    int delete(IdsParam idsParam);

    int updateStatus(NewsContribution newsContribution);
}
