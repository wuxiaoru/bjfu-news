package com.bjfu.news.req;


import lombok.Data;

import java.io.Serializable;

@Data
public class ContributionReq implements Serializable {

     private String name;
     private int start;
     private int size;
}
