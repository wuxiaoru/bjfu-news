package com.bjfu.news.dao;

import com.bjfu.news.entity.NewsEditor;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface NewsEditorMapper {
    int insert(NewsEditor record);

    List<NewsEditor> selectAll();
}