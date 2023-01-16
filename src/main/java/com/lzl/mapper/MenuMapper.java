package com.lzl.mapper;

import com.lzl.pojo.Menu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
* @author 26605
* @description 针对表【menu】的数据库操作Mapper
* @createDate 2022-11-03 11:30:53
* @Entity com.lzl.pojo.Menu
*/
public interface MenuMapper extends BaseMapper<Menu> {

    List<Menu> selectByRoleId(Long roleId);
}




