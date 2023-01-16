package com.lzl.service;

import com.lzl.pojo.Menu;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lzl.resp.ListResp;
import com.lzl.resp.Resp;

import java.util.List;

/**
* @author 26605
* @description 针对表【menu】的数据库操作Service
* @createDate 2022-11-03 11:30:53
*/
public interface MenuService extends IService<Menu> {

    ListResp menus();

    ListResp nodes();

    Resp saveOrUpdate(Long menuId, Long parentId, String menuIcon, String menuUrl, String menuName, Integer menuOrder);

    ListResp getMenus(Long menuId);

    Resp delete(List<Long> menuId);
}
