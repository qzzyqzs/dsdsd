package com.lzl.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * 
 * @TableName menu
 */
@TableName(value ="menu")
@Data
public class Menu implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    @JsonProperty("menuid")
    private Long id;

    /**
     * 
     */
    private String icon;

    /**
     * 
     */
    @JsonProperty("menuname")
    private String menuName;

    /**
     * 
     */
    private String hasThird;

    /**
     * 
     */
    private String url;

    /**
     * 
     */
    private Long pid;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    @TableField(exist = false)
    private List<Menu> menus;
}