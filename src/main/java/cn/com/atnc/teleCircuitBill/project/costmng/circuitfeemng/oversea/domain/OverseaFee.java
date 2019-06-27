package cn.com.atnc.teleCircuitBill.project.costmng.circuitfeemng.oversea.domain;

import cn.com.atnc.teleCircuitBill.framework.web.domain.BaseEntity;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * 境外电路月租资费实体类
 * @author liwenjie
 * @date 2018-09-07
 */
@Getter
@Setter
public class OverseaFee extends BaseEntity {


    private static final long serialVersionUID = 7529292421357728293L;

    //id
    private String overseaFeeId;
    //电路类型
    private String circuitType;
    //速率
    private String rate;
    //单价（美元/月/条）
    private Double price;

}
