package com.bjfu.news.req;


import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ContributionReq implements Serializable {

    private String userName;
    private String title;
    private Integer start;
    private Integer page;
    private String endTime;
    private String status;
    private Long userId;
    private Integer size;
    private String startTime;
    private List<Long> contributionIds;
}
