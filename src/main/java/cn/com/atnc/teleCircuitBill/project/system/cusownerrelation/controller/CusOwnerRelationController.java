package cn.com.atnc.teleCircuitBill.project.system.cusownerrelation.controller;

import java.util.List;

import cn.com.atnc.teleCircuitBill.project.customerInfo.customers.domain.Customer;
import cn.com.atnc.teleCircuitBill.project.customerInfo.customers.service.CustomerService;
import cn.com.atnc.teleCircuitBill.project.system.billowner.domain.Billowner;
import cn.com.atnc.teleCircuitBill.project.system.billowner.service.BillownerServiceImpl;
import cn.com.atnc.teleCircuitBill.project.system.billowner.service.IBillownerService;
import cn.com.atnc.teleCircuitBill.project.system.cusownerrelation.domain.CusOwnerRelation;
import cn.com.atnc.teleCircuitBill.project.system.cusownerrelation.service.ICusOwnerRelationService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import cn.com.atnc.teleCircuitBill.framework.aspectj.lang.annotation.Log;
import cn.com.atnc.teleCircuitBill.framework.aspectj.lang.constant.BusinessType;
import cn.com.atnc.teleCircuitBill.framework.web.controller.BaseController;
import cn.com.atnc.teleCircuitBill.framework.web.page.TableDataInfo;
import cn.com.atnc.teleCircuitBill.framework.web.domain.AjaxResult;

import javax.servlet.http.HttpServletRequest;

/**
 * 客户-开帐单位关系 信息操作处理
 * 
 * @author liwenjie
 * @date 2018-12-07
 */
@Controller
@RequestMapping("/system/cusbillownermng")
public class CusOwnerRelationController extends BaseController
{
    private String prefix = "system/cusownermng";
	
	@Autowired
	private ICusOwnerRelationService cusOwnerRelationService;
	@Autowired
	private CustomerService customerService;
	@Autowired
	private IBillownerService billownerService;
	
	@RequiresPermissions("system:cusbillownermng:view")
	@GetMapping()
	public String ownerRelation(ModelMap modelMap)
	{
        modelMap.put("customers",customerService.selectCustomerList(new Customer()));
	    return prefix + "/cusownerrelation";
	}
	
	/**
	 * 查询客户-开帐单位关系列表
	 */
	@RequiresPermissions("system:cusbillownermng:list")
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(CusOwnerRelation cusownerRelation)
	{
		startPage();
        List<CusOwnerRelation> list = cusOwnerRelationService.selectCusOwnerRelationList(cusownerRelation);
		return getDataTable(list);
	}
	
	/**
	 * 新增客户-开帐单位关系
	 */
	@GetMapping("/add")
	public String add(ModelMap map)
	{
		map.put("customers",customerService.selectCustomerList(new Customer()));
		map.put("billowners",billownerService.selectBillownerList(new Billowner()));
	    return prefix + "/add";
	}
	
	/**
	 * 新增保存客户-开帐单位关系
	 */
	@RequiresPermissions("system:cusbillownermng:add")
	@Log(title = "客户-开帐单位关系", action = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(@RequestParam("customerId") String customerId, @RequestParam("monthBillOwnerId") String monthBillOwnerId,
                              @RequestParam("configBillOwnerId") String configBillOwnerId, @RequestParam("accessBillOwnerId") String accessBillOwnerId)
	{
		CusOwnerRelation cusOwnerRelation = new CusOwnerRelation();
		Customer customer = customerService.findCustomerByCustomerId(customerId);
		if (customer != null) {
			cusOwnerRelation.setCustomer(customer);
		}

		Billowner monthBillOwner = billownerService.selectBillownerById(monthBillOwnerId);
		if (monthBillOwner != null) {
			cusOwnerRelation.setMonthBillOwner(monthBillOwner);
		}
		Billowner configBillOwner = billownerService.selectBillownerById(configBillOwnerId);
		if (configBillOwner != null) {
			cusOwnerRelation.setConfigBillOwner(configBillOwner);
		}
		Billowner accessBillOwner = billownerService.selectBillownerById(accessBillOwnerId);
		if (accessBillOwner != null) {
			cusOwnerRelation.setAccessBillOwner(accessBillOwner);
		}
		return toAjax(cusOwnerRelationService.insertCusOwnerRelation(cusOwnerRelation));
	}

	/**
	 * 修改客户-开帐单位关系关系
	 */
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") String id, ModelMap mmap)
	{
		CusOwnerRelation cusownerRelation = cusOwnerRelationService.selectCusOwnerRelationById(id);
        mmap.put("customers",customerService.selectCustomerList(new Customer()));
        mmap.put("billowners",billownerService.selectBillownerList(new Billowner()));
		mmap.put("cusownerRelation", cusownerRelation);
		if (cusownerRelation.getCustomer().getCustomerId() != null ) {
            mmap.put("customerIdSelected",customerService.findCustomerByCustomerId(cusownerRelation.getCustomer().getCustomerId()).getCustomerId());
        }
		if (cusownerRelation.getMonthBillOwner().getBillOwnerId() != null) {
            mmap.put("monthBillOwnerSelected", billownerService.selectBillownerById(cusownerRelation.getMonthBillOwner().getBillOwnerId()).getBillOwnerId());
        }
        if (cusownerRelation.getAccessBillOwner().getBillOwnerId() != null) {
            mmap.put("accessBillOwnerSelected", billownerService.selectBillownerById(cusownerRelation.getAccessBillOwner().getBillOwnerId()).getBillOwnerId());
        }
        if (cusownerRelation.getConfigBillOwner().getBillOwnerId() != null) {
            mmap.put("configBillOwnerSelected", billownerService.selectBillownerById(cusownerRelation.getConfigBillOwner().getBillOwnerId()).getBillOwnerId());
        }


	    return prefix + "/edit";
	}
	
	/**
	 * 修改保存客户-开帐单位关系关系
	 */
	@RequiresPermissions("system:cusbillownermng:edit")
	@Log(title = "客户-开帐单位关系", action = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(CusOwnerRelation cusOwnerRelation,@RequestParam("customerId") String customerId, @RequestParam("monthBillOwnerId") String monthBillOwnerId,
                               @RequestParam("configBillOwnerId") String configBillOwnerId, @RequestParam("accessBillOwnerId") String accessBillOwnerId)
	{
        Customer customer = customerService.findCustomerByCustomerId(customerId);
        if (customer != null) {
            cusOwnerRelation.setCustomer(customer);
        }

        Billowner monthBillOwner = billownerService.selectBillownerById(monthBillOwnerId);
        if (monthBillOwner != null) {
            cusOwnerRelation.setMonthBillOwner(monthBillOwner);
        }
        Billowner configBillOwner = billownerService.selectBillownerById(configBillOwnerId);
        if (configBillOwner != null) {
            cusOwnerRelation.setConfigBillOwner(configBillOwner);
        }
        Billowner accessBillOwner = billownerService.selectBillownerById(accessBillOwnerId);
        if (accessBillOwner != null) {
            cusOwnerRelation.setAccessBillOwner(accessBillOwner);
        }
		return toAjax(cusOwnerRelationService.updateCusOwnerRelation(cusOwnerRelation));
	}
	
	/**
	 * 删除客户-开帐单位关系关系
	 */
	@RequiresPermissions("system:cusbillownermng:remove")
	@Log(title = "客户-开帐单位关系", action = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(String ids)
	{		
		return toAjax(cusOwnerRelationService.deleteCusOwnerRelationByIds(ids));
	}
	
}
