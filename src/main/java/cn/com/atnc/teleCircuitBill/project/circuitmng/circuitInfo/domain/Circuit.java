package cn.com.atnc.teleCircuitBill.project.circuitmng.circuitInfo.domain;

import cn.com.atnc.teleCircuitBill.framework.aspectj.lang.annotation.Excel;
import cn.com.atnc.teleCircuitBill.framework.web.domain.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * 电路详情实体类
 * @author liwenjie
 */
@Getter
@Setter
@ToString
public class Circuit extends BaseEntity {

    private static final long serialVersionUID = 7682565647911268530L;

    //电路id
    private String circuitId;

    //电路业务类型
    private String circuitServiceType;

    //区域
    private String circuitArea;

    //导出Excel时的序号
    @Excel(name="序号")
    private Integer serialNumber;

    //电路名称
    @Excel(name="电路名称")
    private String circuitName;

    //电路编号
    @Excel(name="电路编号")
    private String circuitCode;

    //电路类型
    @Excel(name="电路类型")
    private String circuitType;

    //电路名称（电路编号）
    private String circuitNameAndCode;

    //电路速率
    @Excel(name="速率")
    private String circuitSpeed;

    //通信技术服务费=电路费用 + 端口费用
    private Double circuitFee;

    //电路费用
    private Double circuitFeeCir;

    //端口费用
    private Double circuitFeePort;

    //电路配置费
    private Double configFee;

    //是否收取配置技术服务费
    private Boolean isFirstConfig;

    //是否开出配置技术服务费账单
    private Boolean isGenerateConfigBill;

    //本端节点
    @Excel(name="本端节点名称/槽位号/端口号")
    private String homeEnd;

    //对端节点
    @Excel(name="对端节点名称/槽位号/端口号")
    private String oppEnd;

    //启付时间
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @Excel(name="启付时间")
    private Date openTime;

    //电路用途
    private String useInfo;

    //配置时间
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @Excel(name="配置时间")
    private Date configTime;

    //电路取消时间
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @Excel(name="取消时间")
    private Date cancelTime;

    //是否过期标识位
    private Integer isExpired;

    //分成比例
    private String divideRatio;

    //运维平台申请编号
    @Excel(name="运维平台申请编号")
    private String iomsApplyNumber;

    //依据文件
    @Excel(name="依据文件")
    private String basisFile;

    //说明
    private String description;

    //租用合同账单编号
    private transient List<String> hireContractNumber;

    //维护合同账单编号
    private transient List<String> maintainContractNumber;

    //冗余字段（用于显示合同挂接电路的费用字段）
    private transient String feePercent;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Circuit circuit = (Circuit) o;
        return Objects.equals(circuitId, circuit.circuitId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(circuitId);
    }
}
