package cn.com.atnc.teleCircuitBill.project.costmng.circuitfeemng.domestic.domain;

import cn.com.atnc.teleCircuitBill.framework.web.domain.BaseEntity;
import lombok.Getter;
import lombok.Setter;

/**
 * 境内电路月租费用实体类
 * @author liwenjie
 * @date 2018-09-03
 */
@Getter
@Setter
public class DomesticFee extends BaseEntity {

    private static final long serialVersionUID = -7670578442680417298L;

    //id
    private String domesticFeeId;
    //电路类型
    private String circuitType;
    //速率
    private String rate;
    //端口费
    private Double portFee;
    //虚电路费
    private Double vcMonthFee;
    //电路月租费
    private transient Double totalMonthFee;

}
