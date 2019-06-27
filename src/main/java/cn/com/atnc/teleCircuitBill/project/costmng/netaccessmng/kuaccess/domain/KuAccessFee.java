package cn.com.atnc.teleCircuitBill.project.costmng.netaccessmng.kuaccess.domain;

import cn.com.atnc.teleCircuitBill.framework.web.domain.BaseEntity;
import lombok.Getter;
import lombok.Setter;

/**
 * Ku波段入网资费实体类
 * @author lwj
 * @date 2018-10-31
 */
@Getter
@Setter
public class KuAccessFee extends BaseEntity {

    private static final long serialVersionUID = -1674606658631551038L;
    //Id
    private String kuAccessFeeId;
    //站点位置
    private String siteArea;
    //币种
    private String currencyType;
    //站点等级
    private String siteLevel;
    //入网调试费
    private Double siteAccessAmount;

}
