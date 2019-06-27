package cn.com.atnc.teleCircuitBill.project.costmng.configfeemng.domain;


import cn.com.atnc.teleCircuitBill.framework.web.domain.BaseEntity;
import lombok.Getter;
import lombok.Setter;

/**
 * 定义系统中的入网配置费用表 tele_config_fee
 * 
 * @author liwenjie
 * @date 2018-12-03
 */
@Getter
@Setter
public class ConfigFee extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	/** id */
	private String configFeeId;
	/** 电路类型 */
	private String teleCircuitType;
	/** 配置费数目 */
	private String circuitConfigfee;
	/** 计费单位 */
	private String circuitConfigfeeUnit;
	//金额（配置费数目+计费单位）
	private transient String amount;

}
