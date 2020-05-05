package com.bjfu.news.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class ContributionDetail implements Serializable {
    private Long id;
    private String title;
    private String status;
    private String fileName;
    private String category;
    private String approveSuggestion;
    private String editSuggestion;
    private Date createTime;
}
