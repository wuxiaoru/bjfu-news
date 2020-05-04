package com.bjfu.news.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

@RequiredArgsConstructor
public enum ApproveStatus {

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

    public static final Map<Integer, ApproveStatus> CODE_MAPPING;

    static {
        Map<Integer, ApproveStatus> codeMap = new LinkedHashMap<>();
        for (ApproveStatus approveStatus : ApproveStatus.values()) {
            codeMap.put(approveStatus.code, approveStatus);
        }
        CODE_MAPPING = Collections.unmodifiableMap(codeMap);
    }
}
