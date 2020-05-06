package com.bjfu.news.req;


import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ContributionReq implements Serializable {

    private String name;
    Long userId;
    private Integer start;
    private Integer size;
    private List<Long> contributionIds;
}
