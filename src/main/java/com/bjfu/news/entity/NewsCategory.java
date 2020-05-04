package com.bjfu.news.entity;

import lombok.Data;

import java.util.Date;

@Data
public class NewsCategory {
    private Integer id;

    private String categoryName;

    private Boolean disabled;

    private Date createTime;

    private Date updateTime;
}