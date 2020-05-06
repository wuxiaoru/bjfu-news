package com.bjfu.news.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

@RequiredArgsConstructor
public enum ContributionStatus {

    DRAFT(1, "草稿"),
    APPROVAL_PENDING(2, "待审批"),
    APPROVE(3, "审批通过等待编辑部处理"),
    APPROVAL_REJECTION(4, "审批不过待修改"),
    HIRE(5, "编辑部已录用"),
    REJECTION(6, "编辑部已拒稿");

    @Getter
    private final int code;
    @Getter
    private final String desc;

    public static final Map<Integer, String> CODE_MAPPING;

    static {
        Map<Integer, String> codeMap = new LinkedHashMap<>();
        for (ContributionStatus contributionStatus : ContributionStatus.values()) {
            codeMap.put(contributionStatus.code, contributionStatus.desc);
        }
        CODE_MAPPING = Collections.unmodifiableMap(codeMap);
    }
}
