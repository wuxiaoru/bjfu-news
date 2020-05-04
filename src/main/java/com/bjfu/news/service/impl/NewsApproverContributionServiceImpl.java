package com.bjfu.news.service.impl;

import com.bjfu.news.dao.NewsApproveContributionMapper;
import com.bjfu.news.service.NewsApproverContributionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NewsApproverContributionServiceImpl implements NewsApproverContributionService {

    @Autowired
    private NewsApproveContributionMapper newsApproveContributionMapper;

}
