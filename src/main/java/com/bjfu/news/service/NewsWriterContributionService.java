package com.bjfu.news.service;

import com.bjfu.news.entity.NewsWriterContribution;

import java.util.List;

public interface NewsWriterContributionService {
    List<NewsWriterContribution> selectById(NewsWriterContribution record);
}
