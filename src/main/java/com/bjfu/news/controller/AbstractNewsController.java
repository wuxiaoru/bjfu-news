package com.bjfu.news.controller;

import com.bjfu.news.req.ContributionReq;
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
    protected NewsCategoryLoader newsCategoryLoader;

    @Autowired
    protected NewsCategoryService newsCategoryService;

}
