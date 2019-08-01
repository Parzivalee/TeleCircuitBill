package cn.com.atnc.teleCircuitBill.project.costmng.netaccessmng.caccess.domain;

import cn.com.atnc.teleCircuitBill.framework.web.domain.BaseEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * C波段入网资费实体类
 * @author liwenjie
 * @date 2018-10-19
 */
@Getter
@Setter
public class CAccessFee extends BaseEntity {

    private static final long serialVersionUID = -5914509768188026637L;

    //Id
    @JsonProperty("cAccessFeeId")
    private String cAccessFeeId;
    //站点位置
    private String siteArea;
    //币种
    private String currencyType;
    //站点类型
    private String siteType;
    //入网调试费
    private Double siteAccessAmount;

}
