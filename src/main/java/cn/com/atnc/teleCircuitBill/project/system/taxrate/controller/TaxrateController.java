package cn.com.atnc.teleCircuitBill.project.system.taxrate.controller;

import java.util.List;

import cn.com.atnc.teleCircuitBill.project.system.taxrate.domain.Taxrate;
import cn.com.atnc.teleCircuitBill.project.system.taxrate.service.ITaxrateService;
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
 * 税率信息 信息操作处理
 * 
 * @author liwenjie
 * @date 2018-12-03
 */
@Controller
@RequestMapping("/system/taxrate")
public class TaxrateController extends BaseController
{
    private String prefix = "system/taxrate";
	
	@Autowired
	private ITaxrateService taxrateService;
	
	@RequiresPermissions("system:taxrate:view")
	@GetMapping()
	public String taxrate()
	{
	    return prefix + "/taxrate";
	}
	
	/**
	 * 查询税率信息列表
	 */
	@RequiresPermissions("system:taxrate:list")
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(Taxrate taxrate)
	{
		startPage();
        List<Taxrate> list = taxrateService.selectTaxrateList(taxrate);
		return getDataTable(list);
	}
	
	/**
	 * 新增税率信息
	 */
	@GetMapping("/add")
	public String add()
	{
	    return prefix + "/add";
	}
	
	/**
	 * 新增保存税率信息
	 */
	@RequiresPermissions("system:taxrate:add")
	@Log(title = "税率信息", action = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(Taxrate taxrate)
	{		
		return toAjax(taxrateService.insertTaxrate(taxrate));
	}

	/**
	 * 修改税率信息
	 */
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") String id, ModelMap mmap)
	{
		Taxrate taxrate = taxrateService.selectTaxrateById(id);
		mmap.put("taxrate", taxrate);
	    return prefix + "/edit";
	}
	
	/**
	 * 修改保存税率信息
	 */
	@RequiresPermissions("system:taxrate:edit")
	@Log(title = "税率信息", action = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(Taxrate taxrate)
	{		
		return toAjax(taxrateService.updateTaxrate(taxrate));
	}
	
	/**
	 * 删除税率信息
	 */
	@RequiresPermissions("system:taxrate:remove")
	@Log(title = "税率信息", action = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(String ids)
	{		
		return toAjax(taxrateService.deleteTaxrateByIds(ids));
	}
	
}
