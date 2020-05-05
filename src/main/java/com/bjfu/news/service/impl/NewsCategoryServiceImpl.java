package com.bjfu.news.service.impl;

import com.bjfu.news.dao.NewsCategoryMapper;
import com.bjfu.news.entity.NewsCategory;
import com.bjfu.news.service.NewsCategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class NewsCategoryServiceImpl implements NewsCategoryService {

    @Autowired
    private NewsCategoryMapper newsCategoryMapper;

    @Override
    public int insert(String name) {
        try {
            NewsCategory newsCategory = new NewsCategory();
            newsCategory.setCategoryName(name);
            return newsCategoryMapper.insert(newsCategory);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return 0;
    }

    @Override
    public int update(NewsCategory newsCategory) {
        try {
            return newsCategoryMapper.update(newsCategory);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return 0;
    }
}
