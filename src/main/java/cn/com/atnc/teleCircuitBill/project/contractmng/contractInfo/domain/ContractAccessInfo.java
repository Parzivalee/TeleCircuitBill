package cn.com.atnc.teleCircuitBill.project.contractmng.contractInfo.domain;

import cn.com.atnc.teleCircuitBill.framework.web.domain.BaseEntity;
import cn.com.atnc.teleCircuitBill.project.customerInfo.customers.domain.Customer;
import lombok.Getter;
import lombok.Setter;

/**
 * 合同入网信息实体类
 */
@Getter
@Setter
public class ContractAccessInfo extends BaseEntity {

    private static final long serialVersionUID = -6577539020458033828L;

    private String contractAccessId;       //合同入网信息id
    private ContractInfo contractInfo;      //合同
    private String contractAccessType;      //入网合同类型
    private Customer customer;
    private Integer contractAccessSum;          //入网数量
    private Double contractAccessFee;       //入网费用
    private Boolean generateAccessBill;       //是否生成入网账单

    //冗余字段
    private transient String contractAccessTypeName;  //入网合同类型名称
}
