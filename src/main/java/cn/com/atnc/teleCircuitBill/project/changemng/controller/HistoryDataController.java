package cn.com.atnc.teleCircuitBill.project.changemng.controller;

import java.text.SimpleDateFormat;
import java.util.List;

import cn.com.atnc.teleCircuitBill.project.changemng.domain.HistoryData;
import cn.com.atnc.teleCircuitBill.project.changemng.service.IHistoryDataService;
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
 * 历史数据管理 信息操作处理
 * 
 * @author 
 * @date 2019-01-02
 */
@Controller
@RequestMapping("/changemng")
public class HistoryDataController extends BaseController {
	private static final String CIRCUIT = "circuit";
	private static final String CONTRACT = "contract";
    private String prefix = "changemng";
	
	@Autowired
	private IHistoryDataService historyDataService;

	/**
	 * 查询电路变更表-GET
	 * @return
	 */
	@RequiresPermissions("changemng:circuit:view")
	@GetMapping("/circuit")
	public String changeCircuit()
	{
	    return prefix + "/circuitchange/change";
	}


	/**
	 * 查询电路变更列表
	 */
	@RequiresPermissions("changemng:circuit:list")
	@PostMapping("/circuit/list")
	@ResponseBody
	public TableDataInfo circuitList(HistoryData historyData)
	{
		historyData.setChangeType(CIRCUIT);
		startPage();
        List<HistoryData> list = historyDataService.selectHistoryDataList(historyData);

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        for (HistoryData historyData1: list) {
			String createTime = "";
			String updateTime = "";
			if (historyData1.getCreateTime()!=null) {
				createTime = dateFormat.format(historyData1.getCreateTime());
			}
        	historyData1.setCreateStatus(historyData1.getCreateBy()+"/"+createTime);
        	if (historyData1.getUpdateTime()!=null) {
        		updateTime = dateFormat.format(historyData1.getUpdateTime());
			}
			historyData1.setUpdateStatus(historyData1.getUpdateBy()+"/"+updateTime);

		}
		return getDataTable(list);
	}


	/**
	 * 查询合同变更表-GET
	 * @return
	 */
	@RequiresPermissions("changemng:contract:view")
	@GetMapping("/contract")
	public String changeContract()
	{
		return prefix + "/contractchange/change";
	}


	/**
	 * 查询合同变更列表
	 */
	@RequiresPermissions("changemng:contract:list")
	@PostMapping("/contract/list")
	@ResponseBody
	public TableDataInfo contractList(HistoryData historyData)
	{
		historyData.setChangeType(CONTRACT);
		startPage();
		List<HistoryData> list = historyDataService.selectHistoryDataList(historyData);
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		for (HistoryData historyData1: list) {
			String createTime = "";
			String updateTime = "";
			if (historyData1.getCreateTime()!=null) {
				createTime = dateFormat.format(historyData1.getCreateTime());
			}
			historyData1.setCreateStatus(historyData1.getCreateBy()+"/"+createTime);
			if (historyData1.getUpdateTime()!=null) {
				updateTime = dateFormat.format(historyData1.getUpdateTime());
			}
			historyData1.setUpdateStatus(historyData1.getUpdateBy()+"/"+updateTime);

		}
		return getDataTable(list);
	}

	/**
	 * 新增历史数据管理
	 *//*
	@GetMapping("/add")
	public String add()
	{
	    return prefix + "/add";
	}
	
	*//**
	 * 新增保存历史数据管理
	 *//*
	@RequiresPermissions("module:change:add")
	@Log(title = "历史数据管理", action = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(HistoryData historyData)
	{		
		return toAjax(historyDataService.insertHistoryData(historyData));
	}*/

	/**
	 * 修改电路变更信息-get
	 */
	@GetMapping("/circuit/edit/{id}")
	public String editCircuitChange(@PathVariable("id") String id, ModelMap mmap)
	{
		HistoryData historyData = historyDataService.selectCircuitChangeById(id);
		mmap.put("historyData", historyData);
	    return prefix + "/circuitchange/edit";
	}

	/**
	 * 修改合同变更信息-get
	 */
	@GetMapping("/contract/edit/{id}")
	public String editContractChange(@PathVariable("id") String id, ModelMap mmap)
	{
		HistoryData historyData = historyDataService.selectContractChangeById(id);
		mmap.put("historyData", historyData);
		return prefix + "/contractchange/edit";
	}
	
	/**
	 * 修改电路变更信息
	 */
	@RequiresPermissions("changemng:historydata:edit")
	@Log(title = "历史数据管理", action = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(HistoryData historyData)
	{		
		return toAjax(historyDataService.updateHistoryData(historyData));
	}
}
