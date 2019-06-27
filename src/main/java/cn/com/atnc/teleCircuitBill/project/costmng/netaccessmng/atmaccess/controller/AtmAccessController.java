package cn.com.atnc.teleCircuitBill.project.costmng.netaccessmng.atmaccess.controller;


import cn.com.atnc.teleCircuitBill.framework.aspectj.lang.annotation.Log;
import cn.com.atnc.teleCircuitBill.framework.aspectj.lang.constant.BusinessType;
import cn.com.atnc.teleCircuitBill.framework.web.controller.BaseController;
import cn.com.atnc.teleCircuitBill.framework.web.domain.AjaxResult;
import cn.com.atnc.teleCircuitBill.framework.web.page.TableDataInfo;
import cn.com.atnc.teleCircuitBill.project.costmng.netaccessmng.atmaccess.domain.AtmAccessFee;
import cn.com.atnc.teleCircuitBill.project.costmng.netaccessmng.atmaccess.service.AtmAccessService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * ATM数据节点入网资费Controller层
 * @author liwenjie
 * @date 2018-11-01
 */
@RequestMapping("costmng/netaccessmng/atmaccess")
@Controller
public class AtmAccessController extends BaseController {
    private String prefix = "/costmng/netaccessmng/atmaccess";

    @Autowired
    private AtmAccessService atmAccessService;

    /**
     * 获取C波段入网资费信息-GET
     * @return
     */
    @RequiresPermissions("netaccessmng:atmaccess:view")
    @GetMapping()
    public String fee() {
        return prefix + "/atmaccessfee";
    }

    /**
     * 获取ATM数据网节点入网资费信息-POST
     * @param atmAccessFee
     * @return
     */
    @RequiresPermissions("netaccessmng:atmaccess:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(AtmAccessFee atmAccessFee)
    {
        startPage();
        List<AtmAccessFee> list = atmAccessService.selectAtmAccessFeeList(atmAccessFee);
        return getDataTable(list);
    }

    /**
     * ATM数据网节点入网资费新增-get
     * @return
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * ATM数据网节点入网资费新增-post
     */
    @RequiresPermissions("netaccessmng:atmaccess:add")
    @Log(title = "ATM数据网节点入网资费管理", action = BusinessType.INSERT)
    @PostMapping("/add")
    @Transactional(rollbackFor = Exception.class)
    @ResponseBody
    public AjaxResult addSave(AtmAccessFee atmAccessFee) {
        return toAjax(atmAccessService.insertAtmAccessFee(atmAccessFee));
    }

    /**
     * 修改ATM数据网节点入网资费-GET
     */
    @GetMapping("/edit/{atmAccessFeeId}")
    public String edit(@PathVariable("atmAccessFeeId") String atmAccessFeeId, ModelMap mmap)
    {
        mmap.put("atmAccessFee", atmAccessService.selectAtmAccessFeeById(atmAccessFeeId));
        return prefix + "/edit";
    }

    /**
     * 修改ATM数据网节点入网资费-POST
     */
    @RequiresPermissions("netaccessmng:atmaccess:edit")
    @Log(title = "ATM数据网节点入网管理", action = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(AtmAccessFee atmAccessFee)
    {
        return toAjax(atmAccessService.updateAtmAccessFee(atmAccessFee));
    }
}
