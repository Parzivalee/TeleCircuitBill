package cn.com.atnc.teleCircuitBill.project.system.exchangerate.controller;

import java.util.List;

import cn.com.atnc.teleCircuitBill.project.system.exchangerate.domain.Exchangerate;
import cn.com.atnc.teleCircuitBill.project.system.exchangerate.service.IExchangerateService;
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
 * 汇率信息 信息操作处理
 * 
 * @author 
 * @date 2018-12-03
 */
@Controller
@RequestMapping("/system/exchangerate")
public class ExchangerateController extends BaseController
{
    private String prefix = "system/exchangerate";
	
	@Autowired
	private IExchangerateService exchangerateService;
	
	@RequiresPermissions("system:exchangerate:view")
	@GetMapping()
	public String exchangerate()
	{
	    return prefix + "/exchangerate";
	}
	
	/**
	 * 查询汇率信息列表
	 */
	@RequiresPermissions("system:exchangerate:list")
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(Exchangerate exchangerate)
	{
		startPage();
        List<Exchangerate> list = exchangerateService.selectExchangerateList(exchangerate);
		return getDataTable(list);
	}
	
	/**
	 * 新增汇率信息
	 */
	@GetMapping("/add")
	public String add()
	{
	    return prefix + "/add";
	}
	
	/**
	 * 新增保存汇率信息
	 */
	@RequiresPermissions("system:exchangerate:add")
	@Log(title = "汇率信息", action = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(Exchangerate exchangerate)
	{		
		return toAjax(exchangerateService.insertExchangerate(exchangerate));
	}

	/**
	 * 修改汇率信息
	 */
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") String id, ModelMap mmap)
	{
		Exchangerate exchangerate = exchangerateService.selectExchangerateById(id);
		mmap.put("exchangerate", exchangerate);
	    return prefix + "/edit";
	}
	
	/**
	 * 修改保存汇率信息
	 */
	@RequiresPermissions("system:exchangerate:edit")
	@Log(title = "汇率信息", action = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(Exchangerate exchangerate)
	{		
		return toAjax(exchangerateService.updateExchangerate(exchangerate));
	}
	
	/**
	 * 删除汇率信息
	 */
	@RequiresPermissions("system:exchangerate:remove")
	@Log(title = "汇率信息", action = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(String ids)
	{		
		return toAjax(exchangerateService.deleteExchangerateByIds(ids));
	}
	
}
