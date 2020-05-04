package com.bjfu.news.service.impl;

import com.bjfu.news.dao.NewsEditContributionMapper;
import com.bjfu.news.service.NewsEditContributionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NewsEditContributionServiceImpl implements NewsEditContributionService {

    @Autowired
    private NewsEditContributionMapper newsEditContributionMapper;

}
