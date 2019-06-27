package cn.com.atnc.teleCircuitBill.project.costmng.circuitfeemng.oversea.controller;

import cn.com.atnc.teleCircuitBill.framework.aspectj.lang.annotation.Log;
import cn.com.atnc.teleCircuitBill.framework.aspectj.lang.constant.BusinessType;
import cn.com.atnc.teleCircuitBill.framework.web.controller.BaseController;
import cn.com.atnc.teleCircuitBill.framework.web.domain.AjaxResult;
import cn.com.atnc.teleCircuitBill.framework.web.page.TableDataInfo;
import cn.com.atnc.teleCircuitBill.project.costmng.circuitfeemng.oversea.domain.OverseaFee;
import cn.com.atnc.teleCircuitBill.project.costmng.circuitfeemng.oversea.service.OverseaFeeService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 境外电路月租资费Controller层
 * @author liwenjie
 * @date 2018-09-07
 */
@Controller
@RequestMapping("costmng/circuitcost/oversea")
public class OverseaFeeController extends BaseController {

    private String prefix = "/costmng/circuitfeemng/oversea";

    @Autowired
    private OverseaFeeService overseaFeeService;

    /**
     * 获取境外电路月租费用信息-GET
     * @return
     */
    @RequiresPermissions("circuitcost:oversea:view")
    @GetMapping()
    public String fee() {
        return prefix + "/overseafee";
    }

    /**
     * 获取电路信息-POST
     * @param overseaFee
     * @return
     */
    @RequiresPermissions("circuitcost:oversea:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(OverseaFee overseaFee)
    {
        startPage();
        List<OverseaFee> list = overseaFeeService.selectOverseaFeeList(overseaFee);
        return getDataTable(list);
    }

    /**
     * 境外电路月租资费新增-get
     * @return
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 境外电路月租资费新增-post
     */
    @RequiresPermissions("circuitcost:oversea:add")
    @Log(title = "境外电路月租资费管理", action = BusinessType.INSERT)
    @PostMapping("/add")
    @Transactional(rollbackFor = Exception.class)
    @ResponseBody
    public AjaxResult addSave(OverseaFee overseaFee) {
        return toAjax(overseaFeeService.insertOverseaFee(overseaFee));
    }

    /**
     * 修改境外电路月租资费-GET
     */
    @GetMapping("/edit/{overseaFeeId}")
    public String edit(@PathVariable("overseaFeeId") String overseaFeeId, ModelMap mmap)
    {
        mmap.put("overseaFee", overseaFeeService.selectOverseaFeeById(overseaFeeId));
        return prefix + "/edit";
    }

    /**
     * 修改境外电路月租资费
     */
    @RequiresPermissions("circuitcost:oversea:edit")
    @Log(title = "境外电路月租资费管理", action = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(OverseaFee overseaFee)
    {
        return toAjax(overseaFeeService.updateOverseaFee(overseaFee));
    }

    /**
     * 删除境外资费
     */
    @RequiresPermissions("circuitcost:oversea:remove")
    @Log(title = "境外电路月租资费管理", action = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(overseaFeeService.deleteOverseaFeeByIds(ids));
    }

}
