package com.bjfu.news.service.impl;

import com.bjfu.news.dao.NewsWriterContributionMapper;
import com.bjfu.news.service.NewsWriterContributionLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NewsWriterContributionLoaderImpl implements NewsWriterContributionLoader {

    @Autowired
    NewsWriterContributionMapper newsWriterContributionMapper;

}
