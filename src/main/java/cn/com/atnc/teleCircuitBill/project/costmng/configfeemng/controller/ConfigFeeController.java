package cn.com.atnc.teleCircuitBill.project.costmng.configfeemng.controller;

import java.util.List;

import cn.com.atnc.teleCircuitBill.project.costmng.configfeemng.domain.ConfigFee;
import cn.com.atnc.teleCircuitBill.project.costmng.configfeemng.service.IConfigFeeService;
import cn.com.atnc.teleCircuitBill.project.system.config.domain.Config;
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
 * 配置资费管理 信息操作处理
 * 
 * @author liwenjie
 * @date 2018-12-03
 */
@Controller
@RequestMapping("/costmng/configfeemng")
public class ConfigFeeController extends BaseController
{
    private String prefix = "costmng/configfeemng";
	
	@Autowired
	private IConfigFeeService configFeeService;
	
	@RequiresPermissions("costmng:configfee:view")
	@GetMapping()
	public String configFee()
	{
	    return prefix + "/configFee";
	}
	
	/**
	 * 查询配置资费管理列表
	 */
	@RequiresPermissions("costmng:configFee:list")
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(ConfigFee configFee)
	{
		startPage();
        List<ConfigFee> list = configFeeService.selectConfigFeeList(configFee);
        for (ConfigFee configFee1: list) {
        	configFee1.setAmount(configFee1.getCircuitConfigfee()+"("+configFee1.getCircuitConfigfeeUnit()+")");
		}
		return getDataTable(list);
	}
	
	/**
	 * 新增配置资费管理
	 */
	@GetMapping("/add")
	public String add()
	{
	    return prefix + "/add";
	}
	
	/**
	 * 新增保存配置资费管理
	 */
	@RequiresPermissions("costmng:configFee:add")
	@Log(title = "配置资费管理", action = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(ConfigFee configFee)
	{		
		return toAjax(configFeeService.insertConfigFee(configFee));
	}

	/**
	 * 修改配置资费管理
	 */
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") String id, ModelMap mmap)
	{
		ConfigFee configFee = configFeeService.selectConfigFeeById(id);
		mmap.put("configFee", configFee);
	    return prefix + "/edit";
	}
	
	/**
	 * 修改保存配置资费管理
	 */
	@RequiresPermissions("costmng:configFee:edit")
	@Log(title = "配置资费管理", action = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(ConfigFee configFee)
	{		
		return toAjax(configFeeService.updateConfigFee(configFee));
	}
	
	/**
	 * 删除配置资费管理
	 */
	@RequiresPermissions("costmng:configFee:remove")
	@Log(title = "配置资费管理", action = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(String ids)
	{		
		return toAjax(configFeeService.deleteConfigFeeByIds(ids));
	}
	
}
