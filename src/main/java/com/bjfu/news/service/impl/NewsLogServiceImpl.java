package com.bjfu.news.service.impl;

import com.bjfu.news.dao.NewsOperateLogMapper;
import com.bjfu.news.entity.NewsOperateLog;
import com.bjfu.news.service.NewsLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class NewsLogServiceImpl implements NewsLogService {

    @Autowired
    private NewsOperateLogMapper newsOperateLogMapper;

    @Override
    public NewsOperateLog insertLog(NewsOperateLog log) {
        newsOperateLogMapper.insertLog(log);
        return log;
    }
}
