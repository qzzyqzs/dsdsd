package com.lzl.pojo;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

/**
 * 
 * @TableName user
 */
@Data
@TableName("user")
public class User implements Serializable {
    /**
     * 
     */
    @JsonSerialize(using= ToStringSerializer.class)
    @TableId(value = "id",type = IdType.AUTO)
    private Long id;

    private Long roleId;

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
    private String password;

    /**
     * 
     */
    private Long deptId;

    /**
     * 
     */
    @JsonProperty("userMobile")
    private String phone;

    /**
     * 
     */
    private Long createUser;

    /**
     * 
     */
    @JsonFormat(pattern ="yyyy-MM-dd HH:mm:ss",timezone ="GMT+8")
    private Date createTime;

    /**
     * 
     */
    private Long updateUser;

    /**
     * 
     */
    @JsonFormat(pattern ="yyyy-MM-dd HH:mm:ss",timezone ="GMT+8")
    @TableField(value ="update_time" )
    private Date editTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

}