package com.bjfu.news.service;

import com.bjfu.news.entity.NewsWriterContribution;
import com.bjfu.news.req.IdsParam;

public interface NewsWriterContributionService {

    int delete(IdsParam idsParam);

    int updateStatus(NewsWriterContribution newsWriterContribution);
}
