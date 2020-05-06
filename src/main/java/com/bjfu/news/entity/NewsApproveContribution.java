package com.bjfu.news.entity;

import lombok.Data;

import java.util.Date;

@Data
public class NewsApproveContribution {
    private Long id;

    private Long contributionId;

    private Long approverId;

    private String operation;

    private Boolean disabled;

    private Date createTime;

    private Date updateTime;

    private String suggestion;
}