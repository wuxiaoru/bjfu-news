package com.bjfu.news.service;

import com.bjfu.news.entity.NewsCategory;

public interface NewsCategoryService {

    int insert(String name);

    int update(NewsCategory newsCategory);

}
