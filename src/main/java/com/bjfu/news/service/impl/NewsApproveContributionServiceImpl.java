package com.bjfu.news.service.impl;

import com.bjfu.news.dao.NewsApproveContributionMapper;
import com.bjfu.news.entity.NewsApproveContribution;
import com.bjfu.news.req.IdsParam;
import com.bjfu.news.service.NewsApproveContributionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class NewsApproveContributionServiceImpl implements NewsApproveContributionService {

    @Autowired
    private NewsApproveContributionMapper newsApproveContributionMapper;

    @Override
    public int delete(IdsParam idsParam) {
        try {
            return newsApproveContributionMapper.delete(idsParam);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return 0;
    }

    @Override
    public int updateOperation(NewsApproveContribution approveContribution) {
        try {
            return newsApproveContributionMapper.updateOpetation(approveContribution);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return 0;
    }
}
