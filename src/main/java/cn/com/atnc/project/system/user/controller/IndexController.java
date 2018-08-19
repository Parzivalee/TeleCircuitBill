package cn.com.atnc.project.system.user.controller;

import java.util.List;

import cn.com.atnc.framework.config.ProjectConfig;
import cn.com.atnc.framework.web.controller.BaseController;
import cn.com.atnc.project.system.menu.domain.Menu;
import cn.com.atnc.project.system.menu.service.IMenuService;
import cn.com.atnc.project.system.user.domain.User;
import cn.com.atnc.framework.config.ProjectConfig;
import cn.com.atnc.framework.web.controller.BaseController;
import cn.com.atnc.project.system.menu.domain.Menu;
import cn.com.atnc.project.system.menu.service.IMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 首页 业务处理
 * 
 * @author
 */
@Controller
public class IndexController extends BaseController
{
    @Autowired
    private IMenuService menuService;

    @Autowired
    private ProjectConfig projectConfig;

    // 系统首页
    @GetMapping("/index")
    public String index(ModelMap mmap)
    {
        // 取身份信息
        User user = getUser();
        // 根据用户id取出菜单
        List<Menu> menus = menuService.selectMenusByUserId(user.getUserId());
        mmap.put("menus", menus);
        mmap.put("user", user);
        mmap.put("copyrightYear", projectConfig.getCopyrightYear());
        return "index";
    }

    // 系统介绍
    @GetMapping("/system/main")
    public String main(ModelMap mmap)
    {
        mmap.put("version", projectConfig.getVersion());
        return "main";
    }

}
