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
import org.springframework.ui.ModelMap;
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
     * 国家修改
     */
    @GetMapping("/edit/{countryId}")
    public String edit(@PathVariable("countryId") Long countryId, ModelMap model)
    {
        model.put("country", countryService.selectCountryById(countryId));
        return prefix + "/edit";
    }

    /**
     * 修改保存用户
     */
    @RequiresPermissions("customerInfo:countrys:edit")
    @Log(title = "国家管理", action = BusinessType.UPDATE)
    @PostMapping("/edit")
    @Transactional(rollbackFor = Exception.class)
    @ResponseBody
    public AjaxResult editSave(Country country) {
        return toAjax(countryService.updateCountry(country));
    }

    /**
     * 国家删除
     */
    @RequiresPermissions("customerInfo:countrys:remove")
    @Log(title = "国家管理", action = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(countryService.deleteCountryByIds(ids));
    }

}