package com.bjfu.news.req;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
public class UserInfoCreateParam implements Serializable {

    @NotBlank(message = "职工号不能为空")
    private String eno;

    @NotBlank(message = "姓名不能为空")
    private String userName;

    @NotBlank(message = "单位不能为空")
    private String unit;

    private String job;

    private String mail;

    private String officePhone;

    private String mobile;

    private String roleType;
}
