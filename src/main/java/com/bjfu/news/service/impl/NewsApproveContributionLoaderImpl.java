package com.bjfu.news.service.impl;

import com.bjfu.news.dao.NewsApproveContributionMapper;
import com.bjfu.news.entity.NewsApproveContribution;
import com.bjfu.news.req.ContributionReq;
import com.bjfu.news.service.NewsApproveContributionLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NewsApproveContributionLoaderImpl implements NewsApproveContributionLoader {

    @Autowired
    private NewsApproveContributionMapper newsApproveContributionMapper;

    @Override
    public int getCount(ContributionReq req) {
        return newsApproveContributionMapper.getCount(req);
    }

    @Override
    public List<NewsApproveContribution> selectByCId(Long contributionId) {
        return newsApproveContributionMapper.selectByCId(contributionId);
    }

    @Override
    public List<NewsApproveContribution> list(ContributionReq req) {
        return newsApproveContributionMapper.list(req);
    }

    @Override
    public NewsApproveContribution selectById(Long id) {
        return newsApproveContributionMapper.selectById(id);
    }
}
