package com.lzl.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

/**
 * 
 * @TableName dept
 */
@TableName(value ="dept")
@Data
public class Dept implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 
     */
    private String deptName;

    /**
     * 
     */
    @TableField(value ="dept_code" )
    private String deptNo;

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