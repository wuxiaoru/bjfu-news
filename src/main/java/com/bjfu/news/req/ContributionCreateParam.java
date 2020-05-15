package com.bjfu.news.req;


import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class ContributionCreateParam implements Serializable {
    @NotBlank(message = "稿件题目不能为空")
    private String title;
    @NotNull
    @Min(value = 1, message = "id必须大于0")
    private Long category;
    @NotBlank(message = "文件地址不能为空")
    private String fileUrl;
    private Long approveId;
    @NotBlank(message = "状态不能为空")
    private String status;
}
