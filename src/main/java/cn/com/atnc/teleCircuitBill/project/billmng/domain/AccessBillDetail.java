package cn.com.atnc.teleCircuitBill.project.billmng.domain;

import cn.com.atnc.teleCircuitBill.framework.web.domain.BaseEntity;
import cn.com.atnc.teleCircuitBill.project.contractmng.contractInfo.domain.ContractAccessInfo;
import lombok.Getter;
import lombok.Setter;

/**
 * 入网账单明细实体类
 * @author lwj
 */
@Getter
@Setter
public class AccessBillDetail extends BaseEntity {

    private static final long serialVersionUID = -6603310388174756152L;

    private String accessBillDetailId;
    private Bill bill;
    private ContractAccessInfo contractAccessInfo;
    private String accessType;
    private Integer billAccessSum;
    private Double billAccessFee;
}
