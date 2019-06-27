package cn.com.atnc.teleCircuitBill.project.monitor.job.task;

import cn.com.atnc.teleCircuitBill.project.circuitmng.circuitInfo.mapper.CircuitMapper;
import cn.com.atnc.teleCircuitBill.project.circuitmng.circuitInfo.service.CircuitService;
import cn.com.atnc.teleCircuitBill.project.contractmng.contractInfo.service.ContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 定时任务调度测试
 * 
 * @author lwj
 */
@Component("ryTask")
public class RyTask
{
    @Autowired
    private CircuitService circuitService;
    @Autowired
    private ContractService contractService;

    public void ryParams(String params)
    {
        System.out.println("执行有参方法：" + params);
    }

    public void ryNoParams()
    {
        System.out.println("执行无参方法");
    }

    public void checkCircuitIsExpired() {
        System.out.println("------------检查电路是否过期--------------");
        circuitService.checkCircuitIsExpired();
        System.out.println("-----------定时器执行结束--------------------");
    }

    public void checkContractIsExpired() {
        System.out.println("------------检查合同是否过期--------------");
        contractService.checkContractIsExpired();
        System.out.println("-----------定时器执行结束--------------------");
    }

}
