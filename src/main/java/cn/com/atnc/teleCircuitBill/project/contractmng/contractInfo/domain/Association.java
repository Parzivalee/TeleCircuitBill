package cn.com.atnc.teleCircuitBill.project.contractmng.contractInfo.domain;

import cn.com.atnc.teleCircuitBill.framework.web.domain.BaseEntity;
import cn.com.atnc.teleCircuitBill.project.circuitmng.circuitInfo.domain.Circuit;
import cn.com.atnc.teleCircuitBill.project.customerInfo.customers.domain.Customer;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * 合同-电路关联表实体类
 */
@Getter
@Setter
public class Association extends BaseEntity {

    private static final long serialVersionUID = -5926827710135314555L;

    //id
    private String associationId;
    //合同
    private ContractInfo contract;
    //电路
    private Circuit circuit;
    //客户
    private Customer customer;
    //开始时间
    private Date startTime;
    //结束时间
    private Date endTime;
    //是否终止
    private Integer isEnd;
    //费用占比
    private String feePercent;
    //总费用
    private Double feeSum;
    //通信费用
    private Double feeCir;
    //端口费用
    private Double feePort;
}
