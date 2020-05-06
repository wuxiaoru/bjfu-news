package com.bjfu.news.service.impl;

import com.bjfu.news.dao.NewsWriterContributionMapper;
import com.bjfu.news.entity.NewsWriterContribution;
import com.bjfu.news.req.ContributionReq;
import com.bjfu.news.service.NewsWriterContributionLoader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
@Slf4j
public class NewsWriterContributionLoaderImpl implements NewsWriterContributionLoader {

    @Autowired
    NewsWriterContributionMapper newsWriterContributionMapper;

    @Override
    public List<NewsWriterContribution> pageByName(ContributionReq req) {
        try {
            return newsWriterContributionMapper.pageByName(req);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    @Override
    public List<NewsWriterContribution> listByName(String name) {
        return newsWriterContributionMapper.listByName(name);
    }

    @Override
    public int getCount(ContributionReq req) {
        try {
            return newsWriterContributionMapper.getCount(req);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return 0;
    }

    @Override
    public NewsWriterContribution selectById(Long id) {
        try {
            return newsWriterContributionMapper.selectById(id);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    @Override
    public List<NewsWriterContribution> selectByIds(Collection<Long> ids) {
        return newsWriterContributionMapper.selectByIds(ids);
    }
}
