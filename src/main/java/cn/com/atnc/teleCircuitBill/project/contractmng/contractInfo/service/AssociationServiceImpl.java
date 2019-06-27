package cn.com.atnc.teleCircuitBill.project.contractmng.contractInfo.service;


import cn.com.atnc.teleCircuitBill.common.utils.security.ShiroUtils;
import cn.com.atnc.teleCircuitBill.project.changemng.service.IHistoryDataService;
import cn.com.atnc.teleCircuitBill.project.circuitmng.circuitInfo.domain.Circuit;
import cn.com.atnc.teleCircuitBill.project.circuitmng.circuitInfo.service.CircuitService;
import cn.com.atnc.teleCircuitBill.project.contractmng.contractInfo.domain.Association;
import cn.com.atnc.teleCircuitBill.project.contractmng.contractInfo.domain.ContractInfo;
import cn.com.atnc.teleCircuitBill.project.contractmng.contractInfo.mapper.AssociationMapper;
import cn.com.atnc.teleCircuitBill.project.customerInfo.customers.domain.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 合同电路关联Service接口实现类
 * @author liwenjie
 * @date 2018-11-12
 */
@Service
public class AssociationServiceImpl implements AssociationService {
    private static final String CIRCUITCHANGE = "电路变更";

    private static final String CONTRACT = "contract";


    @Autowired
    private AssociationMapper associationMapper;
    @Autowired
    private CircuitService circuitService;
    @Autowired
    private ContractService contractService;
    @Autowired
    private IHistoryDataService historyDataService;


    @Override
    public List<Association> selectAssociationByContractId(String contractId) {
        return associationMapper.selectAssociationByContractId(contractId);
    }

    @Override
    public int insertAssociation(Association association) {
        return associationMapper.insertAssociation(association);
    }

    @Override
    public List<Association> findAssociationsByCircuitId(String circuitId) {
        return associationMapper.findAssociationsByCircuitId(circuitId);
    }

    @Override
    public List<Association> selectAssociationByCustomerId(String customerId) {
        return associationMapper.selectAssociationByCustomerId(customerId);
    }

    @Override
    public List<Association> selectAssociationByConditions(String billType, String customerId) {
        String contractType = "";
        if (billType.equals("HireBill")) {
            contractType = "Hire";
        }else if (billType.equals("DevideBill")) {
            contractType = "Maintain";
        }
        return associationMapper.selectAssociationByConditions(contractType,customerId);
    }

    @Override
    public void cancelAssociation(Association association) {
        association.setIsEnd(1);
        association.setEndTime(new Date());
        association.setUpdateBy(ShiroUtils.getLoginName());

        //新增历史数据信息
        historyDataService.insertCircuitHistoryData(CONTRACT,CIRCUITCHANGE,"",association.getContract().getContractNumber());

        associationMapper.updateAssociation(association);
    }

    @Override
    public int createAssociation(String circuitId, String contractId, String feePercent) {
        //新建association
        Association association = new Association();
        ContractInfo contract = contractService.selectContractByContractId(contractId);
        Customer customer = contract.getCustomer();
        if (contract!= null) {
            association.setContract(contract);
        }

        if (customer!=null) {
            association.setCustomer(customer);
        }
        Circuit circuit = circuitService.selectCircuitById(circuitId);
        if (circuit!=null) {
            association.setCircuit(circuit);
        }
        //开始日期
        association.setStartTime(new Date());
        //设置是否结束
        association.setIsEnd(0);
        //设置费用占比
        association.setFeePercent(feePercent);
        if (feePercent.equals("100%")) {
            //电路费用
            association.setFeeCir(circuit.getCircuitFeeCir());
            //端口费用
            association.setFeePort(circuit.getCircuitFeePort());
            //总费用
            association.setFeeSum(circuit.getCircuitFeeCir()+circuit.getCircuitFeePort());
        }else if (feePercent.equals("70%")) {
            //电路费用
            association.setFeeCir(circuit.getCircuitFeeCir()*0.7);
            //端口费用
            association.setFeePort(circuit.getCircuitFeePort());
            //总费用
            association.setFeeSum(circuit.getCircuitFeeCir()*0.7+circuit.getCircuitFeePort());
        }else if (feePercent.equals("40%")) {
            //电路费用
            association.setFeeCir(circuit.getCircuitFeeCir()*0.4);
            //端口费用
            association.setFeePort(circuit.getCircuitFeePort()*0.5);
            //总费用
            association.setFeeSum(circuit.getCircuitFeeCir()*0.4+circuit.getCircuitFeePort()*0.5);
        }else if (feePercent.equals("30%")) {
            //电路费用
            association.setFeeCir(circuit.getCircuitFeeCir()*0.3);
            //端口费用
            association.setFeePort(circuit.getCircuitFeePort()*0.5);
            //总费用
            association.setFeeSum(circuit.getCircuitFeeCir()*0.3+circuit.getCircuitFeePort()*0.5);
        }
        association.setCreateBy(ShiroUtils.getLoginName());

        //新建历史数据信息
        historyDataService.insertCircuitHistoryData(CONTRACT,CIRCUITCHANGE,"",association.getContract().getContractNumber());


        //保存电路-合同关联关系
        return associationMapper.insertAssociation(association);

    }

    @Override
    public int updateAssociation(Association association) {
        association.setUpdateBy(ShiroUtils.getLoginName());
        return associationMapper.updateAssociation(association);
    }
}
