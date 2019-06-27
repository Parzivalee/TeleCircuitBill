package cn.com.atnc.teleCircuitBill.project.costmng.circuitfeemng.domestic.controller;

import cn.com.atnc.teleCircuitBill.framework.aspectj.lang.annotation.Log;
import cn.com.atnc.teleCircuitBill.framework.aspectj.lang.constant.BusinessType;
import cn.com.atnc.teleCircuitBill.framework.web.controller.BaseController;
import cn.com.atnc.teleCircuitBill.framework.web.domain.AjaxResult;
import cn.com.atnc.teleCircuitBill.framework.web.page.TableDataInfo;
import cn.com.atnc.teleCircuitBill.project.circuitmng.circuitInfo.domain.Circuit;
import cn.com.atnc.teleCircuitBill.project.costmng.circuitfeemng.domestic.domain.DomesticFee;
import cn.com.atnc.teleCircuitBill.project.costmng.circuitfeemng.domestic.service.DomesticFeeService;
import cn.com.atnc.teleCircuitBill.project.system.notice.domain.Notice;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 境内电路月租费用Controller层
 * @author liwenjie
 * @date 2018-09-03
 */
@Controller
@RequestMapping("costmng/circuitcost/domestic")
public class DomesticFeeController extends BaseController {
    private String prefix = "/costmng/circuitfeemng/domestic";

    @Autowired
    private DomesticFeeService domesticFeeService;

    /**
     * 获取境内电路月租费用信息-GET
     * @return
     */
    @RequiresPermissions("circuitcost:domestic:view")
    @GetMapping()
    public String circuit() {
        return prefix + "/domesticfee";
    }

    /**
     * 获取电路信息-POST
     * @param domesticFee
     * @return
     */
    @RequiresPermissions("circuitcost:domestic:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(DomesticFee domesticFee)
    {
        startPage();
        List<DomesticFee> list = domesticFeeService.selectDomesticFeeList(domesticFee);
        return getDataTable(list);
    }

    /**
     * 境内电路月租资费新增-get
     * @return
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 境内电路月租资费新增-post
     */
    @RequiresPermissions("circuitcost:domestic:add")
    @Log(title = "境内电路月租资费管理", action = BusinessType.INSERT)
    @PostMapping("/add")
    @Transactional(rollbackFor = Exception.class)
    @ResponseBody
    public AjaxResult addSave(DomesticFee domesticFee) {
        return toAjax(domesticFeeService.insertDomesticFee(domesticFee));
    }

    /**
     * 修改境内电路月租资费-GET
     */
    @GetMapping("/edit/{domesticFeeId}")
    public String edit(@PathVariable("domesticFeeId") String domesticFeeId, ModelMap mmap)
    {
        mmap.put("domesticFee", domesticFeeService.selectDomesticFeeById(domesticFeeId));
        return prefix + "/edit";
    }

    /**
     * 修改境内电路月租资费
     */
    @RequiresPermissions("circuitcost:domestic:edit")
    @Log(title = "境内电路月租资费管理", action = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(DomesticFee domesticFee)
    {
        return toAjax(domesticFeeService.updateDomesticFee(domesticFee));
    }

    /**
     * 删除记录
     * @param ids
     * @return
     */
    @RequiresPermissions("circuitcost:domestic:remove")
    @Log(title = "境内电路月租资费管理", action = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
            return toAjax(domesticFeeService.deleteDomesticFeeByIds(ids));
    }
}
