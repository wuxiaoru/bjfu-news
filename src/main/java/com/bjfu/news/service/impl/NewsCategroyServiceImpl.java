package com.bjfu.news.service.impl;

import com.bjfu.news.dao.NewsCategoryMapper;
import com.bjfu.news.service.NewsCategroyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NewsCategroyServiceImpl implements NewsCategroyService {

    @Autowired
    private NewsCategoryMapper newsCategoryMapper;

}
