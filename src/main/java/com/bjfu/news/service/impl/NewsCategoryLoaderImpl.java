package com.bjfu.news.service.impl;

import com.bjfu.news.dao.NewsCategoryMapper;
import com.bjfu.news.entity.NewsCategory;
import com.bjfu.news.service.NewsCategoryLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NewsCategoryLoaderImpl implements NewsCategoryLoader {

    @Autowired
    private NewsCategoryMapper newsCategoryMapper;

    @Override
    public List<NewsCategory> selectAll() {
        return newsCategoryMapper.selectAll();
    }

    @Override
    public NewsCategory selectById(Long id) {
        return newsCategoryMapper.selectById(id);
    }
}
