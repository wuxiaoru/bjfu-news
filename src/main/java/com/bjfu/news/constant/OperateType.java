package com.bjfu.news.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum OperateType {

    CONTRIBUTOR_SUBMIT("投稿人提交"),
    APPROVE_SUBMIT("审稿人审稿");

    @Getter
    private final String desc;
}
