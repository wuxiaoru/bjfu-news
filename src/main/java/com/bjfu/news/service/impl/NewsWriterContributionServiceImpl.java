package com.bjfu.news.service.impl;

import com.bjfu.news.dao.NewsWriterContributionMapper;
import com.bjfu.news.req.IdsParam;
import com.bjfu.news.service.NewsWriterContributionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class NewsWriterContributionServiceImpl implements NewsWriterContributionService {

    @Autowired
    NewsWriterContributionMapper newsWriterContributionMapper;

    @Override
    public int delete(IdsParam idsParam) {
        try {
            newsWriterContributionMapper.delete(idsParam);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return 0;
    }
}
