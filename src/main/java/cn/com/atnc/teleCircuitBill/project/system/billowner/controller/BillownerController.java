package cn.com.atnc.teleCircuitBill.project.system.billowner.controller;

import java.util.List;

import cn.com.atnc.teleCircuitBill.project.system.billowner.domain.Billowner;
import cn.com.atnc.teleCircuitBill.project.system.billowner.service.IBillownerService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import cn.com.atnc.teleCircuitBill.framework.aspectj.lang.annotation.Log;
import cn.com.atnc.teleCircuitBill.framework.aspectj.lang.constant.BusinessType;
import cn.com.atnc.teleCircuitBill.framework.web.controller.BaseController;
import cn.com.atnc.teleCircuitBill.framework.web.page.TableDataInfo;
import cn.com.atnc.teleCircuitBill.framework.web.domain.AjaxResult;

/**
 * 开帐单位 信息操作处理
 * 
 * @author liwenjie
 * @date 2018-12-03
 */
@Controller
@RequestMapping("/system/billowner")
public class BillownerController extends BaseController
{
    private String prefix = "system/billowner";
	
	@Autowired
	private IBillownerService billownerService;
	
	@RequiresPermissions("system:billowner:view")
	@GetMapping()
	public String billowner()
	{
	    return prefix + "/billowner";
	}
	
	/**
	 * 查询开帐单位列表
	 */
	@RequiresPermissions("system:billowner:list")
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(Billowner billowner)
	{
		startPage();
        List<Billowner> list = billownerService.selectBillownerList(billowner);
		return getDataTable(list);
	}
	
	/**
	 * 新增定义系统账单中的开账单位
	 */
	@GetMapping("/add")
	public String add()
	{
	    return prefix + "/add";
	}
	
	/**
	 * 新增保存开帐单位
	 */
	@RequiresPermissions("system:billowner:add")
	@Log(title = "开帐单位管理", action = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(Billowner billowner)
	{		
		return toAjax(billownerService.insertBillowner(billowner));
	}

	/**
	 * 修改开帐单位
	 */
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") String id, ModelMap mmap)
	{
		Billowner billowner = billownerService.selectBillownerById(id);
		mmap.put("billowner", billowner);
	    return prefix + "/edit";
	}
	
	/**
	 * 修改保存开帐单位
	 */
	@RequiresPermissions("system:billowner:edit")
	@Log(title = "开帐单位管理", action = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(Billowner billowner)
	{		
		return toAjax(billownerService.updateBillowner(billowner));
	}
	
	/**
	 * 删除开帐单位
	 */
	@RequiresPermissions("system:billowner:remove")
	@Log(title = "开帐单位管理", action = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(String ids)
	{		
		return toAjax(billownerService.deleteBillownerByIds(ids));
	}
	
}
