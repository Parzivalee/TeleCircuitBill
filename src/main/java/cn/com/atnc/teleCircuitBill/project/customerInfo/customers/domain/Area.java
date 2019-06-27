package cn.com.atnc.teleCircuitBill.project.customerInfo.customers.domain;

import cn.com.atnc.teleCircuitBill.framework.web.domain.BaseEntity;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * 区域管局列表
 */
@Getter
@Setter
public class Area extends BaseEntity {

    private static final long serialVersionUID = -2064789124227403248L;

    private String areaId;
    private String areaName;
}
