package com.bjfu.news.controller;

import com.alibaba.fastjson.JSON;
import com.bjfu.news.constant.OperateType;
import com.bjfu.news.entity.NewsContribution;
import com.bjfu.news.entity.NewsOperateLog;
import com.bjfu.news.model.OperateLogBean;
import com.bjfu.news.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public abstract class AbstractNewsController {

    @Autowired
    protected NewsApproveContributionLoader approveContributionLoader;

    @Autowired
    protected NewsApproveContributionService approveContributionService;

    @Autowired
    protected NewsEditContributionService editContributionService;

    @Autowired
    protected NewsEditContributionLoader newsEditContributionLoader;

    @Autowired
    protected NewsWriterContributionService newsWriterContributionService;

    @Autowired
    protected NewsWriterContributionLoader newsWriterContributionLoader;

    @Autowired
    protected NewsUserInfoService newsUserInfoService;

    @Autowired
    protected NewsUserInfoLoader newsUserInfoLoader;

    @Autowired
    protected NewsLogLoader newsLogLoader;

    @Autowired
    protected NewsLogService newsLogService;

    protected void createLog(NewsContribution newsContribution) {
        NewsOperateLog log = new NewsOperateLog();
        log.setOperateType(OperateType.CONTRIBUTOR_SUBMIT.name());
        log.setOperateId(1L);
        log.setContributionId(newsContribution.getId());
        log.setStatus(newsContribution.getStatus());
        OperateLogBean bean = new OperateLogBean();
        bean.setDocAuthor(newsContribution.getDocAuthor());
        bean.setDocUrl(newsContribution.getDocUrl());
        bean.setPicAuthor(newsContribution.getPicAuthor());
        bean.setPicUrl(newsContribution.getPicUrl());
        log.setOperateDetail(JSON.toJSONString(bean));
        newsLogService.insertLog(log);
    }

}
