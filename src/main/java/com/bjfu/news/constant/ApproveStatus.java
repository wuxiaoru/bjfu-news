package com.bjfu.news.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

@RequiredArgsConstructor
public enum ApproveStatus {
    NONE("无操作"),
    AGREE("审批通过"),
    REJECTION("审批不通过");

    @Getter
    private final String desc;

    public static final Map<String, String> NAME_MAPPING;

    static {
        Map<String, String> codeMap = new LinkedHashMap<>();
        for (ApproveStatus approveStatus : ApproveStatus.values()) {
            if (!approveStatus.equals(ApproveStatus.NONE)) {
                codeMap.put(approveStatus.name(), approveStatus.desc);
            }
        }
        NAME_MAPPING = Collections.unmodifiableMap(codeMap);
    }
}
