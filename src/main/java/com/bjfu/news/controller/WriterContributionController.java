package com.bjfu.news.controller;


import com.bjfu.news.entity.NewsWriterContribution;
import com.bjfu.news.service.NewsWriterContributionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class WriterContributionController {

    @Autowired
   private   NewsWriterContributionService newsWriterContributionService;

    @RequestMapping(value = "v1/news_writer_contribution/select", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
    public NewsWriterContribution select(HttpServletRequest request,
                                           @RequestParam(required = false, defaultValue = "20") Long id) {
        NewsWriterContribution newsWriterContribution = new NewsWriterContribution();
        newsWriterContribution.setId(id);
        List<NewsWriterContribution> newsWriterContributions = newsWriterContributionService.selectById(newsWriterContribution);
        return  newsWriterContributions.get(0);
    }

    //新建

    //根据名字分页按时间倒叙 1页10条

    //查看详情

    //编辑

    //批量删除
}
