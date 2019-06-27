package cn.com.atnc.teleCircuitBill.project.customerInfo.customerLinkman.controller;

import java.util.List;

import cn.com.atnc.teleCircuitBill.project.customerInfo.customerLinkman.domain.CustomerLinkman;
import cn.com.atnc.teleCircuitBill.project.customerInfo.customerLinkman.service.ICustomerLinkmanService;
import cn.com.atnc.teleCircuitBill.project.customerInfo.customers.domain.Customer;
import cn.com.atnc.teleCircuitBill.project.customerInfo.customers.service.CustomerService;
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

/**
 * 联系人 信息操作处理
 * 
 * @author 
 * @date 2018-12-05
 */
@Controller
@RequestMapping("customerInfo/linkman")
public class CustomerLinkmanController extends BaseController
{
    private String prefix = "customerInfo/customerLinkman";
	
	@Autowired
	private ICustomerLinkmanService customerLinkmanService;
	@Autowired
	private CustomerService customerService;
	
	@RequiresPermissions("customerInfo:linkman:view")
	@GetMapping()
	public String customerLinkman(ModelMap modelMap)
	{
		modelMap.put("customers",customerService.selectCustomerList(new Customer()));
	    return prefix + "/linkman";
	}
	
	/**
	 * 查询联系人列表
	 */
	@RequiresPermissions("customerInfo:linkman:list")
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(CustomerLinkman customerLinkman)
	{
		startPage();
        List<CustomerLinkman> list = customerLinkmanService.selectCustomerLinkmanList(customerLinkman);
		return getDataTable(list);
	}
	
	/**
	 * 新增联系人
	 */
	@GetMapping("/add")
	public String add(ModelMap map)
	{
		map.put("customers",customerService.selectCustomerList(new Customer()));
	    return prefix + "/add";
	}
	
	/**
	 * 新增保存联系人
	 */
	@RequiresPermissions("customerInfo:linkman:add")
	@Log(title = "联系人", action = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(CustomerLinkman customerLinkman, @RequestParam("customerId") String customerId)
	{
		Customer customer = customerService.findCustomerByCustomerId(customerId);
		customerLinkman.setCustomer(customer);
		return toAjax(customerLinkmanService.insertCustomerLinkman(customerLinkman));
	}

	/**
	 * 修改联系人
	 */
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") String id, ModelMap mmap)
	{
		CustomerLinkman customerLinkman = customerLinkmanService.selectCustomerLinkmanById(id);
		mmap.put("customerLinkman", customerLinkman);
		mmap.put("customers",customerService.selectCustomerList(new Customer()));
		mmap.put("customerIdSelected",customerLinkman.getCustomer().getCustomerId());

		return prefix + "/edit";
	}
	
	/**
	 * 修改保存联系人
	 */
	@RequiresPermissions("customerInfo:linkman:edit")
	@Log(title = "联系人管理", action = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(CustomerLinkman customerLinkman,@RequestParam("customerId") String customerId)
	{
		Customer customer = customerService.findCustomerByCustomerId(customerId);
		customerLinkman.setCustomer(customer);
		System.out.println(customerLinkman.getLinkmanId());
		return toAjax(customerLinkmanService.updateCustomerLinkman(customerLinkman));
	}
	
	/**
	 * 删除联系人
	 */
	@RequiresPermissions("customerInfo:linkman:remove")
	@Log(title = "联系人管理", action = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(String ids)
	{		
		return toAjax(customerLinkmanService.deleteCustomerLinkmanByIds(ids));
	}
	
}
