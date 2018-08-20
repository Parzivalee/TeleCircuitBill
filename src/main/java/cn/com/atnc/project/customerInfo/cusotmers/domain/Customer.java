package cn.com.atnc.project.customerInfo.cusotmers.domain;

import cn.com.atnc.framework.web.domain.BaseEntity;
import lombok.Getter;
import lombok.Setter;

/**
 * 客户实体类
 * @author lwj
 */
@Getter
@Setter
public class Customer extends BaseEntity {
    //区域管局
    private String areaAuthority;
    //名称
    private String companyName;
    //译名
    private String translateName;
    //简称
    private String shortName;
    //代码
    private String code;
    //法人
    private String legalPerson;
    //法人电话
    private int legalPersonPhone;
    //委托人
    private String consignor;
    //委托人电话
    private String consignorPhone;
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
    //地区
    private String region;
    //等级
    private String level;
    //类别
    private String type;

}
