package cn.com.atnc.teleCircuitBill.project.costmng.netaccessmng.kuaccess.controller;

import cn.com.atnc.teleCircuitBill.framework.aspectj.lang.annotation.Log;
import cn.com.atnc.teleCircuitBill.framework.aspectj.lang.constant.BusinessType;
import cn.com.atnc.teleCircuitBill.framework.web.controller.BaseController;
import cn.com.atnc.teleCircuitBill.framework.web.domain.AjaxResult;
import cn.com.atnc.teleCircuitBill.framework.web.page.TableDataInfo;
import cn.com.atnc.teleCircuitBill.project.costmng.netaccessmng.kuaccess.domain.KuAccessFee;
import cn.com.atnc.teleCircuitBill.project.costmng.netaccessmng.kuaccess.service.KuAccessService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Ku波段入网资费Controller层
 * @author lwj
 * @date 2018-10-31
 */
@Controller
@RequestMapping("costmng/netaccessmng/kuaccess")
public class KuAccessController extends BaseController {
    private String prefix = "/costmng/netaccessmng/kuaccess";

    @Autowired
    private KuAccessService kuAccessService;

    /**
     * 获取Ku波段入网资费信息-GET
     * @return
     */
    @RequiresPermissions("netaccessmng:kuaccess:view")
    @GetMapping()
    public String fee() {
        return prefix + "/kuaccessfee";
    }

    /**
     * 获取Ku波段资费信息-POST
     * @param kuAccessFee
     * @return
     */
    @RequiresPermissions("netaccessmng:kuaccess:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(KuAccessFee kuAccessFee)
    {
        startPage();
        List<KuAccessFee> list = kuAccessService.selectKuAccessFeeList(kuAccessFee);
        return getDataTable(list);
    }

    /**
     * Ku波段入网资费新增-get
     * @return
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * Ku波段入网资费新增-post
     */
    @RequiresPermissions("netaccessmng:kuaccess:add")
    @Log(title = "Ku波段入网资费管理", action = BusinessType.INSERT)
    @PostMapping("/add")
    @Transactional(rollbackFor = Exception.class)
    @ResponseBody
    public AjaxResult addSave(KuAccessFee kuAccessFee) {
        return toAjax(kuAccessService.insertKuAccessFee(kuAccessFee));
    }

    /**
     * 修改Ku波段入网资费-GET
     */
    @GetMapping("/edit/{kuAccessFeeId}")
    public String edit(@PathVariable("kuAccessFeeId") String kuAccessFeeId, ModelMap mmap)
    {
        mmap.put("kuAccessFee", kuAccessService.selectKuAccessFeeById(kuAccessFeeId));
        return prefix + "/edit";
    }

    /**
     * 修改Ku波段入网资费-POST
     */
    @RequiresPermissions("netaccessmng:kuaccess:edit")
    @Log(title = "Ku波段入网资费管理", action = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(KuAccessFee kuAccessFee)
    {
        return toAjax(kuAccessService.updateKuAccessFee(kuAccessFee));
    }
}
