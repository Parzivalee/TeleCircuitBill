package cn.com.atnc.teleCircuitBill.project.circuitmng.circuitInfo.service;

import cn.com.atnc.teleCircuitBill.project.circuitmng.circuitInfo.domain.Circuit;

import java.util.List;
import java.util.Set;

/**
 * 电路详情业务逻辑接口层
 * @author liwenjie
 */
public interface CircuitService {

    List<Circuit> selectCircuitList(Circuit circuit);

    int insertCircuit(Circuit circuit);

    /**
     * 根据合同id找到合同对应的电路信息
     * @param contractId
     * @return List<Circuit>
     */
    List<Circuit> selectCircuitByContractId(String contractId);

    /**
     * 根据电路id查找电路
     * @param circuitId
     * @return
     */
    Circuit selectCircuitById(String circuitId);

    /**
     * 更新电路
     * @param circuit
     * @return
     */
    int updateCircuit(Circuit circuit);

    /**
     * 移除电路
     * @param ids
     * @return
     */
    int removeCircuitByIds(String ids);

    /**
     * 根据电路编号查找是否有该电路信息
     * @param circuitCode
     */
    Circuit selectCircuitByCircuitCode(String circuitCode);

    /**
     * 比较修改前后的电路信息之间的不同
     * @param circuit 电路
     * @param oldCircuit
     * @return
     */
    String compareCircuit(Circuit circuit, Circuit oldCircuit);

    /**
     * 根据客户，合同，电路编号查找电路
     * @param customerId 客户Id
     * @param contractId 合同Id
     * @return List<Circuit>
     */
    List<Circuit> selectCircuitByCustomerAndContract(String customerId, String contractId);

    /**
     * 获取最新（一周内）的即将到期的电路信息
     * @return
     */
    String getLatestOverCircuitInfos();

    /**
     * 校验电路编号是否唯一
     * @param circuitCode 电路编号
     * @param circuitId 电路Id
     * @return String
     */
    String checkCircuitCodeNameUnique(String circuitCode,String circuitId);

    /**
     * 判断电路是否过期
     */
    void checkCircuitIsExpired();
}
