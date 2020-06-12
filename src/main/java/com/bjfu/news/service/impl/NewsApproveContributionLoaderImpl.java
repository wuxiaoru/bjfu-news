package com.bjfu.news.service.impl;

import com.bjfu.news.dao.NewsApproveContributionMapper;
import com.bjfu.news.entity.NewsApproveContribution;
import com.bjfu.news.service.NewsApproveContributionLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class NewsApproveContributionLoaderImpl implements NewsApproveContributionLoader {

    @Autowired
    private NewsApproveContributionMapper newsApproveContributionMapper;

    @Override
    public NewsApproveContribution selectByCId(Long contributionId) {
        return newsApproveContributionMapper.selectByCId(contributionId);
    }

    @Override
    public List<NewsApproveContribution> selectByCIds(Collection<Long> contributionIds) {
        return newsApproveContributionMapper.selectByCIds(contributionIds);
    }

    @Override
    public List<NewsApproveContribution> selectByDate(String startTime, String endTime) {
        return newsApproveContributionMapper.selectByDate(startTime, endTime);
    }

    @Override
    public NewsApproveContribution selectById(Long id) {
        return newsApproveContributionMapper.selectById(id);
    }

    @Override
    public List<NewsApproveContribution> selectByApproveId(Long userId) {
        return newsApproveContributionMapper.selectByApproveId(userId);
    }
}
