package com.bjfu.news.service;

import com.bjfu.news.entity.NewsCategory;

import java.util.List;

public interface NewsCategoryLoader {

    List<NewsCategory> selectAll();

    NewsCategory selectById(Long id);
}
