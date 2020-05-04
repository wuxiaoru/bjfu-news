package com.bjfu.news.service.impl;

import com.bjfu.news.dao.NewsCategoryMapper;
import com.bjfu.news.service.NewsCategroyLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NewsCategroyLoaderImpl implements NewsCategroyLoader {

    @Autowired
    private NewsCategoryMapper newsCategoryMapper;
}
