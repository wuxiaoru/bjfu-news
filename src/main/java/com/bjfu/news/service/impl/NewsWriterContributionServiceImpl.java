package com.bjfu.news.service.impl;

import com.bjfu.news.dao.NewsWriterContributionMapper;
import com.bjfu.news.entity.NewsWriterContribution;
import com.bjfu.news.service.NewsWriterContributionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NewsWriterContributionServiceImpl implements NewsWriterContributionService {

    @Autowired
    NewsWriterContributionMapper newsWriterContributionMapper;

    @Override
    public List<NewsWriterContribution> selectById(NewsWriterContribution record) {
        return newsWriterContributionMapper.selectById(record);
    }
}
