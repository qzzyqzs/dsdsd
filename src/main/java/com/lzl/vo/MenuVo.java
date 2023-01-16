package com.lzl.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.lzl.pojo.Menu;
import lombok.Data;

import java.util.List;

@Data
public class MenuVo {

    @TableId(type = IdType.AUTO)
    @JsonProperty("menuid")
    private Long id;

    private String icon;


    @JsonProperty("menuname")
    private String menuName;


    private String hasThird;


    private String url;

    private Long pid;

    @TableField(exist = false)
    private List<MenuVo> menus;
}
