package cn.com.atnc.teleCircuitBill.project.billmng.domain;

import cn.com.atnc.teleCircuitBill.framework.web.domain.BaseEntity;
import cn.com.atnc.teleCircuitBill.project.contractmng.contractInfo.domain.Association;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * 电路租用、分成账单明细实体类
 * @author liwenjie
 * @date 2018-11-14
 */
@Getter
@Setter
public class BillDetail extends BaseEntity {

    private static final long serialVersionUID = -1738536440056765138L;

    private String billDetailId;        //id
    private Bill bill;                 //电路月租账单实体类
    private Association association;    //合同-电路关联实体类
    private String monthSum;
    private Date monthBillStart;
    private Date monthBillEnd;
    private String accountPeriod;       //账期
    private Double monthEveryFee;
    private Double monthTotalFee;
    private String monthDetailCheckCode;

}
