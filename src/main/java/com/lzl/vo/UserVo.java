package com.lzl.vo;

import lombok.Data;

import java.util.Date;

@Data
public class UserVo {

    private Long id;

    /**
     *
     */
    private String userName;

    /**
     *
     */
    private String account;

    /**
     *
     */
    private Long deptId;

    /**
     *
     */
    private String phone;

    /**
     *
     */
    private Long createUser;

    /**
     *
     */
    private Date createTime;

    /**
     *
     */
    private Long updateUser;

    /**
     *
     */
    private Date updateTime;
}
