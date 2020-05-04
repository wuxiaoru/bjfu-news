package com.bjfu.news.service.impl;

import com.bjfu.news.dao.NewsApproveContributionMapper;
import com.bjfu.news.service.NewsApproveContributionLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NewsApproveContributionLoaderImpl implements NewsApproveContributionLoader {

    @Autowired
    private NewsApproveContributionMapper newsApproveContributionMapper;

}
