package cn.com.atnc.teleCircuitBill.project.costmng.netaccessmng.atmaccess.domain;

import cn.com.atnc.teleCircuitBill.framework.web.domain.BaseEntity;
import lombok.Getter;
import lombok.Setter;

/**
 * ATM数据网节点入网资费标准
 * @author liwenjie
 * @date 2018-11-01
 */
@Getter
@Setter
public class AtmAccessFee extends BaseEntity {

    private static final long serialVersionUID = -2757947815603576428L;

    //id
    private String atmAccessFeeId;
    //是否购买设备
    private String siteBuyEquipment;
    //币种
    private String currencyType;
    //入网调试费
    private Double siteAccessAmount;
}