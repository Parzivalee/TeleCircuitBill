package cn.com.atnc.teleCircuitBill.project.customerInfo.customers.domain;

import cn.com.atnc.teleCircuitBill.common.enums.customerInfo.CustomerCountryArea;
import cn.com.atnc.teleCircuitBill.common.enums.customerInfo.CustomerLevel;
import cn.com.atnc.teleCircuitBill.framework.web.domain.BaseEntity;
import lombok.*;

/**
 * 客户实体类
 * @author lwj
 */
@Getter
@Setter
public class Customer extends BaseEntity {

    private static final long serialVersionUID = 8821864472027087563L;

    //客户ID
    private String customerId;
    //区域管局
    private String area;
    //客户名称
    private String customerName;
    //译名
    private String translateName;
    //简称
    private String shortName;
    //代码
    private String code;
    //法人
    private String legalPerson;
    //法人电话
    private String legalPersonPhone;
    //委托人
    private String delegation;
    //委托人电话
    private String delegationPhone;
    //开户银行
    private String bank;
    //银行账户
    private String bankAccount;
    //地址
    private String address;
    //邮编
    private String email;
    //电话
    private String phoneNumber;
    //传真
    private String fax;
    //网址
    private String url;
    //客户区域（境内、境外、港澳台）
    private String region;
    //类别
    //private String type;

    //是否入网客户
    private Boolean access;

    //是否为配置客户
    private Boolean config;

    //是否为租用客户
    private Boolean rent;

    //是否为维护客户
    private Boolean maintain;

    //客户等级
    private String level;

    //客户所关联的入网合同数量，当数量为0时“access”为false
    private Integer accessSum;

    //客户所关联的配置电路数量，当数量为0时“config”为false
    private Integer configSum;

     //客户所关联的租用电路数量，当数量为0时“rent”为false
    private Integer rentSum;

    //客户所关联的维护电路数量，当数量为0时“maintain”为false
    private Integer maintainSum;

}
