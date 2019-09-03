package cn.com.atnc.teleCircuitBill.project.customerInfo.customers.domain;

import cn.com.atnc.teleCircuitBill.framework.aspectj.lang.annotation.Excel;
import lombok.Getter;
import lombok.Setter;

/**
 * @Description 客户表导出模版
 * @Author lwj
 * @Date 2019/8/27
 * @Version 1.0
 **/
@Getter
@Setter
public class CustomerExportModel {
    //区域管局
    @Excel(name = "区域管局")
    private String area;
    //客户名称
    @Excel(name = "客户名称")
    private String customerName;
    //译名
    @Excel(name = "译名")
    private String translateName;
    //简称
    @Excel(name = "简称")
    private String shortName;
    //代码
    @Excel(name = "代码")
    private String code;
    //法人
    @Excel(name = "法人")
    private String legalPerson;
    //法人电话
    @Excel(name = "法人电话")
    private String legalPersonPhone;
    //委托人
    @Excel(name = "委托人")
    private String delegation;
    //委托人电话
    @Excel(name = "委托人电话")
    private String delegationPhone;
    //开户银行
    @Excel(name = "开户银行")
    private String bank;
    //银行账户
    @Excel(name = "银行账户")
    private String bankAccount;
    //地址
    @Excel(name = "地址")
    private String address;
    //邮编
    @Excel(name = "邮编")
    private String email;
    //电话
    @Excel(name = "电话")
    private String phoneNumber;
    //传真
    @Excel(name = "传真")
    private String fax;
    //网址
    @Excel(name = "网址")
    private String url;
    //客户区域（境内、境外、港澳台）
    @Excel(name = "客户区域")
    private String region;
    //客户等级
    @Excel(name = "客户等级")
    private String level;
}
