package com.bjfu.news.service.impl;

import com.bjfu.news.dao.NewsContributionMapper;
import com.bjfu.news.entity.NewsContribution;
import com.bjfu.news.req.IdsParam;
import com.bjfu.news.service.NewsWriterContributionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class NewsWriterContributionServiceImpl implements NewsWriterContributionService {

    @Autowired
    NewsContributionMapper newsContributionMapper;

    @Override
    public int delete(IdsParam idsParam) {
        try {
            return newsContributionMapper.delete(idsParam);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return 0;
    }

    @Override
    public int updateStatus(NewsContribution newsContribution) {
        try {
            return newsContributionMapper.updateStatus(newsContribution);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return 0;
    }
}
