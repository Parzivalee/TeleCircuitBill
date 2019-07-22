package cn.com.atnc.teleCircuitBill.project.statisticsmng.controller;

import cn.com.atnc.teleCircuitBill.framework.web.controller.BaseController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 统计报表控制器
 * @author liwenjie
 */
@Controller
@RequestMapping("/statistics")
public class StatisticsController extends BaseController {

    String prefix = "/statisticsmng";
    @Value("${fineReport.url}")
    private String reportUrl;

    /**
     * 电路信息总表
     * @return
     */
    @RequiresPermissions("statistics:circuitlist:view")
    @GetMapping("/circuitlist")
    public String circuitlist()
    {
        return redirect(reportUrl+"Circuit.cpt&op=write");
    }

    /**
     * 合同汇总表
     * @return
     */
    @RequiresPermissions("statistics:contractlist:view")
    @GetMapping("/contractlist")
    public String contractlist()
    {
        return  redirect(reportUrl+"Contract.cpt&op=write");
    }

    /**
     * 技术服务账单汇总表
     * @return
     */
    @RequiresPermissions("statistics:hirebilllist:view")
    @GetMapping("/hirebilllist")
    public String hirebilllist()
    {
        return redirect(reportUrl+"HireBillInfo.cpt&op=write");
    }

    /**
     * 维护服务账单汇总表
     * @return
     */
    @RequiresPermissions("statistics:maintainbilllist:view")
    @GetMapping("/maintainbilllist")
    public String maintainbilllist()
    {
        return redirect(reportUrl+"MaintainBillInfo.cpt&op=write");
    }

    /**
     * 年度费用汇总表
     * @return
     */
    @RequiresPermissions("statistics:annualfeelist:view")
    @GetMapping("/annualfeelist")
    public String annualfeelist()
    {
        return redirect(reportUrl+"BillYearStat.cpt&op=write");
    }

    /**
     * 合同信息表
     * @return
     */
    @RequiresPermissions("statistics:contractinfolist:view")
    @GetMapping("/contractinfolist")
    public String contractinfolist()
    {
        return redirect(reportUrl+"git.cpt&op=write");
    }
}
