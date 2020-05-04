package com.bjfu.news.dao;

import com.bjfu.news.entity.NewsApprover;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface NewsApproverMapper {
    int insert(NewsApprover record);

    List<NewsApprover> selectAll();

    NewsApprover selectById(Long id);
}