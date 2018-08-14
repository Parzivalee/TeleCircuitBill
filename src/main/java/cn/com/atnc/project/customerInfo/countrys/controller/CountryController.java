package cn.com.atnc.project.customerInfo.countrys.controller;

import cn.com.atnc.framework.aspectj.lang.annotation.Log;
import cn.com.atnc.framework.aspectj.lang.constant.BusinessType;
import cn.com.atnc.framework.web.controller.BaseController;
import cn.com.atnc.framework.web.domain.AjaxResult;
import cn.com.atnc.framework.web.page.TableDataInfo;
import cn.com.atnc.project.customerInfo.countrys.domain.Country;
import cn.com.atnc.project.customerInfo.countrys.service.CountryService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 国家信息 Controller层
 * @author lwj
 * 
 */
@Controller
@RequestMapping("/customerInfo/countrys")
public class CountryController extends BaseController {
    private String prefix = "customerInfo/countrys/";

    @Autowired
    private CountryService countryService;

    @RequiresPermissions("customerInfo:countrys:view")
    @GetMapping()
    public String user()
    {
        return prefix + "/country";
    }

    @RequiresPermissions("customerInfo:countrys:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(Country country)
    {
        startPage();
        List<Country> list = countryService.selectCountryList(country);
        return getDataTable(list);
    }


    /**
     * 国家新增-get
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 国家新增-post
     */
    @RequiresPermissions("customerInfo:countrys:add")
    @Log(title = "国家管理", action = BusinessType.INSERT)
    @PostMapping("/add")
    @Transactional(rollbackFor = Exception.class)
    @ResponseBody
    public AjaxResult addSave(Country country)
    {
        return toAjax(countryService.insertCountry(country));
    }

    /**
     * 修改用户
     *//*
    @GetMapping("/edit/{userId}")
    public String edit(@PathVariable("userId") Long userId, ModelMap mmap)
    {
        mmap.put("user", userService.selectUserById(userId));
        mmap.put("roles", roleService.selectRolesByUserId(userId));
        mmap.put("posts", postService.selectPostsByUserId(userId));
        return prefix + "/edit";
    }

    *//**
     * 修改保存用户
     *//*
    @RequiresPermissions("system:user:edit")
    @Log(title = "用户管理", action = BusinessType.UPDATE)
    @PostMapping("/edit")
    @Transactional(rollbackFor = Exception.class)
    @ResponseBody
    public AjaxResult editSave(User user)
    {
        if (StringUtils.isNotNull(user.getUserId()) && User.isAdmin(user.getUserId()))
        {
            return error("不允许修改超级管理员用户");
        }
        return toAjax(userService.updateUser(user));
    }

    @RequiresPermissions("system:user:resetPwd")
    @Log(title = "重置密码", action = BusinessType.UPDATE)
    @GetMapping("/resetPwd/{userId}")
    public String resetPwd(@PathVariable("userId") Long userId, ModelMap mmap)
    {
        mmap.put("user", userService.selectUserById(userId));
        return prefix + "/resetPwd";
    }

    @RequiresPermissions("system:user:resetPwd")
    @Log(title = "重置密码", action = BusinessType.UPDATE)
    @PostMapping("/resetPwd")
    @ResponseBody
    public AjaxResult resetPwd(User user)
    {
        return toAjax(userService.resetUserPwd(user));
    }

    @RequiresPermissions("system:user:remove")
    @Log(title = "用户管理", action = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        try
        {
            return toAjax(userService.deleteUserByIds(ids));
        }
        catch (Exception e)
        {
            return error(e.getMessage());
        }
    }

    *//**
     * 校验用户名
     *//*
    @PostMapping("/checkLoginNameUnique")
    @ResponseBody
    public String checkLoginNameUnique(User user)
    {
        String uniqueFlag = "0";
        if (user != null)
        {
            uniqueFlag = userService.checkLoginNameUnique(user.getLoginName());
        }
        return uniqueFlag;
    }

    *//**
     * 校验手机号码
     *//*
    @PostMapping("/checkPhoneUnique")
    @ResponseBody
    public String checkPhoneUnique(User user)
    {
        String uniqueFlag = "0";
        if (user != null)
        {
            uniqueFlag = userService.checkPhoneUnique(user);
        }
        return uniqueFlag;
    }

    *//**
     * 校验email邮箱
     *//*
    @PostMapping("/checkEmailUnique")
    @ResponseBody
    public String checkEmailUnique(User user)
    {
        String uniqueFlag = "0";
        if (user != null)
        {
            uniqueFlag = userService.checkEmailUnique(user);
        }
        return uniqueFlag;
    }*/
}