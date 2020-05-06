package com.bjfu.news.service.impl;

import com.bjfu.news.dao.NewsEditContributionMapper;
import com.bjfu.news.entity.NewsEditContribution;
import com.bjfu.news.req.IdsParam;
import com.bjfu.news.service.NewsEditContributionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class NewsEditContributionServiceImpl implements NewsEditContributionService {

    @Autowired
    private NewsEditContributionMapper newsEditContributionMapper;

    @Override
    public int delete(IdsParam idsParam) {
        try {
            return newsEditContributionMapper.delete(idsParam);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return 0;
    }

    @Override
    public int updateOperation(NewsEditContribution newsEditContribution) {
        try {
            return newsEditContributionMapper.updateOpetation(newsEditContribution);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return 0;
    }
}
