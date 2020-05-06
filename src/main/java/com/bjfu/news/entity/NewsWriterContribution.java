package com.bjfu.news.entity;

import lombok.Data;

import java.util.Date;

@Data
public class NewsWriterContribution {
    private Long id;

    private String title;

    private Long categoryId;

    private String fileName;

    private String fileUrl;

    private Integer status;

    private Long createUserId;

    private Boolean disabled;

    private Date createTime;

    private Date updateTime;
}