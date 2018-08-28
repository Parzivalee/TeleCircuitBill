package cn.com.atnc.teleCircuitBill.project.monitor.job.controller;

import java.util.List;

import cn.com.atnc.teleCircuitBill.project.monitor.job.domain.JobLog;
import cn.com.atnc.teleCircuitBill.project.monitor.job.service.IJobLogService;
import cn.com.atnc.teleCircuitBill.common.utils.poi.ExcelUtil;
import cn.com.atnc.teleCircuitBill.framework.aspectj.lang.annotation.Log;
import cn.com.atnc.teleCircuitBill.framework.aspectj.lang.constant.BusinessType;
import cn.com.atnc.teleCircuitBill.framework.web.controller.BaseController;
import cn.com.atnc.teleCircuitBill.framework.web.domain.AjaxResult;
import cn.com.atnc.teleCircuitBill.framework.web.page.TableDataInfo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 调度日志操作处理
 * 
 * @author
 */
@Controller
@RequestMapping("/monitor/jobLog")
public class JobLogController extends BaseController
{
    private String prefix = "monitor/job";

    @Autowired
    private IJobLogService jobLogService;

    @RequiresPermissions("monitor:job:view")
    @GetMapping()
    public String jobLog()
    {
        return prefix + "/jobLog";
    }

    @RequiresPermissions("monitor:job:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(JobLog jobLog)
    {
        startPage();
        List<JobLog> list = jobLogService.selectJobLogList(jobLog);
        return getDataTable(list);
    }

    @Log(title = "调度日志", action = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(JobLog jobLog) throws Exception
    {
        try
        {
            List<JobLog> list = jobLogService.selectJobLogList(jobLog);
            ExcelUtil<JobLog> util = new ExcelUtil<JobLog>(JobLog.class);
            return util.exportExcel(list, "jobLog");
        }
        catch (Exception e)
        {
            return error("导出Excel失败，请联系网站管理员！");
        }
    }

    @Log(title = "调度日志", action = BusinessType.DELETE)
    @RequiresPermissions("monitor:job:remove")
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(jobLogService.deleteJobLogByIds(ids));
    }
}
