package cn.com.atnc.teleCircuitBill.project.contractmng.contractInfo.domain;

import cn.com.atnc.teleCircuitBill.framework.aspectj.lang.annotation.Excel;
import cn.com.atnc.teleCircuitBill.framework.web.domain.BaseEntity;
import cn.com.atnc.teleCircuitBill.project.customerInfo.customers.domain.Customer;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * 合同信息实体类
 * @author liwenjie
 */
@Getter
@Setter
public class ContractInfo extends BaseEntity {

    private static final long serialVersionUID = -2105658687801175755L;

    //id
    private String contractId;
    //合同类型
    private String contractType;
    //合同签订类型(新增/解除)
    private String contractSignType;
    //显示合同类型（合同类型+合同操作类型）
    private transient String TypeALL;
    //区域
    private String area;
    //合同的签订客户
    private Customer customer;
    //合同编号
    private String contractNumber;
    //空管局合同编号
    private String airTrafficContractNumber;
    //合同名称
    private String contractName;
    //甲方
    private Customer partA;
    //乙方
    private Customer partB;
    //合同签订日期
    private Date contractConfigDate;
    //合同生效日期
    private Date contractStartDate;
    //合同结束日期
    private Date contractEndDate;
    //合同终止日期
    private Date contractStopDate;
    //是否过期标识位
    private Integer isExpired;
    //合同是否自动顺延
    private String isContractAutoPostpone;
    //状态
    private transient String status;
    //是否生成账单
    private Integer generateBill;
    //是否变更
    private Integer changeStatus;
    //依据文件
    private String basisFile;


}
