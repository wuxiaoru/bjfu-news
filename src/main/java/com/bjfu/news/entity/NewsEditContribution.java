package com.bjfu.news.entity;

import lombok.Data;

import java.util.Date;

@Data
public class NewsEditContribution {
    private Long id;

    private String editorId;

    private Long contributionId;

    private String operation;

    private Boolean disabled;

    private Date createTime;

    private Date updateTime;

    private String suggestion;

}