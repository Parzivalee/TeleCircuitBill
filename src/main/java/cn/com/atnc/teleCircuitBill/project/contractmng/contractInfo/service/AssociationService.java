package cn.com.atnc.teleCircuitBill.project.contractmng.contractInfo.service;

import cn.com.atnc.teleCircuitBill.project.contractmng.contractInfo.domain.Association;

import java.util.List;

/**
 * 合同电路关联Service接口
 * @author liwenjie
 * @date 2018-11-12
 */
public interface AssociationService {
    /**
     * 根据合同id查找关联信息
     * @param contractId
     * @return
     */
    List<Association> selectAssociationByContractId(String contractId);

    int insertAssociation(Association association);

    /**
     * 根据电路id查找合同-电路关联信息
     * @param circuitId 电路id
     * @return List<Association>
     */
    List<Association> findAssociationsByCircuitId(String circuitId);

    /**
     * 根据客户id与合同类型查找电路-合同关联信息
     * @param customerId
     * @param contractType
     * @return
     */
    List<Association> selectAssociationByCustomerAndType(String customerId,String contractType);

    /**
     * 根据账单类型和客户id查找电路-合同关联信息
     * @param billType
     * @param customerId
     * @return
     */
    List<Association> selectAssociationByConditions(String billType, String customerId);

    /**
     * 取消此条association
     * @param association
     */
    void cancelAssociation(Association association);

    int createAssociation(String circuitId, String contractId, String feePercent);

    /**
     * 更新association
     * @param association
     * @return
     */
    int updateAssociation(Association association);

}
