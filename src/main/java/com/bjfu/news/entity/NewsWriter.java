package com.bjfu.news.entity;

import lombok.Data;

import java.util.Date;

@Data
public class NewsWriter {
    private Long id;

    private String name;

    private Boolean disabled;

    private Date createTime;

    private Date updateTime;

}