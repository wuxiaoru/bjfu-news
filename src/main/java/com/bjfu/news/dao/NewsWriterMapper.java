package com.bjfu.news.dao;

import com.bjfu.news.entity.NewsWriter;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface NewsWriterMapper {
    int insert(NewsWriter record);

    List<NewsWriter> selectAll();
}