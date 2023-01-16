package com.lzl.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lzl.mdc.MDCKey;
import com.lzl.pojo.Dept;
import com.lzl.pojo.Menu;
import com.lzl.resp.ListResp;
import com.lzl.resp.Resp;
import com.lzl.service.MenuService;
import com.lzl.mapper.MenuMapper;
import com.lzl.vo.MenuNodeVo;
import com.lzl.vo.MenuVo;
import io.swagger.annotations.ApiOperation;
import org.slf4j.MDC;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
* @author 26605
* @description 针对表【menu】的数据库操作Service实现
* @createDate 2022-11-03 11:30:53
*/
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu>
    implements MenuService{

    @Resource
    private MenuMapper menuMapper;

    @Override
    public ListResp menus() {

        Long roleId = Long.valueOf(MDC.get(MDCKey.ROLE_ID));

        List<Menu> menus=menuMapper.selectByRoleId(roleId);
        //List<Menu> menus = menuMapper.selectList(null);
        List<MenuVo> parentMenus = new ArrayList<>();
        List<MenuVo> sonMenus = new ArrayList<>();

        for (Menu menu : menus) {
            MenuVo menuVo = new MenuVo();
            BeanUtils.copyProperties(menu,menuVo);
            if (menu.getPid()==null){
                parentMenus.add(menuVo);
            }
            sonMenus.add(menuVo);
        }

        for (MenuVo parentMenu : parentMenus) {
            List<MenuVo> child = getChild(parentMenu.getId(), sonMenus);
            parentMenu.setMenus(child);
        }
        ListResp listResp = new ListResp<>();
        listResp.setData(parentMenus);
        return listResp;
    }



    public List<MenuVo> getChild(Long menuId,List<MenuVo> allMenus){
        List<MenuVo> childMenus = new ArrayList<>();
        for (MenuVo allMenu : allMenus) {
            if (allMenu.getPid()==menuId){
                childMenus.add(allMenu);
            }
        }

        for (MenuVo childMenu : childMenus) {
            if (StringUtils.isEmpty(childMenu.getUrl())){
                //递归
                childMenu.setMenus(getChild(childMenu.getId(),allMenus));
            }
        }

        if (childMenus.size()==0){
            return childMenus;
        }

        return childMenus;

    }


    @Override
    public ListResp nodes() {
        QueryWrapper<Menu> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("url","0");
        List<Menu> menus = menuMapper.selectList(queryWrapper);

        List<MenuNodeVo> menuNodeVos=new ArrayList<>();
        for (Menu menu : menus) {
            MenuNodeVo menuNodeVo = new MenuNodeVo();
            menuNodeVo.setId(menu.getId());
            menuNodeVo.setName(menu.getMenuName());
            menuNodeVos.add(menuNodeVo);
        }

        ListResp listResp = new ListResp<>();
        listResp.setData(menuNodeVos);
        return listResp;
    }

    @Override
    public Resp saveOrUpdate(Long menuId, Long parentId, String menuIcon, String menuUrl, String menuName, Integer menuOrder) {
        Menu menu = new Menu();
        menu.setPid(parentId);
        menu.setIcon(menuIcon);
        menu.setUrl(menuUrl);
        menu.setMenuName(menuName);
        if (menuId == null) {
            int insert = menuMapper.insert(menu);
            return Resp.toReturn(insert > 0 ? "成功" : "失败", insert > 0);
        }

        int update = menuMapper.updateById(menu);
        return Resp.toReturn(update > 0 ? "成功" : "失败", update > 0);
    }

    @Override
    public ListResp getMenus(Long menuId) {
        Menu menu = menuMapper.selectById(menuId);

        ListResp listResp = new ListResp<>();
        listResp.setData(menu);
        return listResp;
    }

    @Override
    public Resp delete(List<Long> menuId) {
        int delete=0;
        for (Long aLong : menuId) {
            delete += menuMapper.deleteById(aLong);
        }
        return Resp.toReturn(delete > 0 ? "成功" : "失败", delete > 0);
    }

}




