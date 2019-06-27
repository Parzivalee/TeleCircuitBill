package cn.com.atnc.teleCircuitBill.project.costmng.netaccessmng.caccess.controller;

import cn.com.atnc.teleCircuitBill.framework.aspectj.lang.annotation.Log;
import cn.com.atnc.teleCircuitBill.framework.aspectj.lang.constant.BusinessType;
import cn.com.atnc.teleCircuitBill.framework.web.controller.BaseController;
import cn.com.atnc.teleCircuitBill.framework.web.domain.AjaxResult;
import cn.com.atnc.teleCircuitBill.framework.web.page.TableDataInfo;
import cn.com.atnc.teleCircuitBill.project.costmng.netaccessmng.caccess.domain.CAccessFee;
import cn.com.atnc.teleCircuitBill.project.costmng.netaccessmng.caccess.service.CAccessService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * C波段入网资费管理Controller层
 * @author liwenjie
 * @date 2018-10-19
 */
@Controller
@RequestMapping("costmng/netaccessmng/caccess")
public class CAccessController extends BaseController {

    private String prefix = "/costmng/netaccessmng/caccess";

    @Autowired
    private CAccessService cAccessService;

    /**
     * 获取C波段入网资费信息-GET
     * @return
     */
    @RequiresPermissions("netaccessmng:caccess:view")
    @GetMapping()
    public String fee() {
        return prefix + "/caccessfee";
    }

    /**
     * 获取C波段资费信息-POST
     * @param cAccessFee
     * @return
     */
    @RequiresPermissions("netaccessmng:caccess:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(CAccessFee cAccessFee)
    {
        startPage();
        List<CAccessFee> list = cAccessService.selectCAccessFeeList(cAccessFee);
        return getDataTable(list);
    }

    /**
     * C波段入网资费新增-get
     * @return
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * C波段入网资费新增-post
     */
    @RequiresPermissions("netaccessmng:caccess:add")
    @Log(title = "C波段入网资费管理", action = BusinessType.INSERT)
    @PostMapping("/add")
    @Transactional(rollbackFor = Exception.class)
    @ResponseBody
    public AjaxResult addSave(CAccessFee cAccessFee) {
        return toAjax(cAccessService.insertCAccessFee(cAccessFee));
    }

    /**
     * 修改C波段入网资费-GET
     */
    @GetMapping("/edit/{cAccessFeeId}")
    public String edit(@PathVariable("cAccessFeeId") String cAccessFeeId, ModelMap mmap)
    {
        CAccessFee cAccessFee = cAccessService.selectCAccessFeeById(cAccessFeeId);
        System.out.println(cAccessFee);
        mmap.put("cAccessFee", cAccessFee);
        return prefix + "/edit";
    }

    /**
     * 修改C波段入网资费-POST
     */
    @RequiresPermissions("netaccessmng:caccess:edit")
    @Log(title = "C波段入网资费管理", action = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(CAccessFee cAccessFee)
    {
        return toAjax(cAccessService.updateCAccessFee(cAccessFee));
    }
}
