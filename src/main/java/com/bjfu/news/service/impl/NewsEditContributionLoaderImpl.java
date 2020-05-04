package com.bjfu.news.service.impl;

import com.bjfu.news.dao.NewsEditContributionMapper;
import com.bjfu.news.service.NewsEditContributionLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NewsEditContributionLoaderImpl implements NewsEditContributionLoader {

    @Autowired
    private NewsEditContributionMapper newsEditContributionMapper;

}
