package cn.com.atnc.teleCircuitBill.project.system.exchangerate.domain;


import cn.com.atnc.teleCircuitBill.framework.web.domain.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * 定义系统中的汇率标准表 sys_exchangerate
 * 
 * @author liwenjie
 * @date 2018-12-03
 */
@Getter
@Setter
public class Exchangerate extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	/**  */
	private String id;
	/**  */
	private Date rateTime;
	/**  */
	private Float rate;
	/**  */
	private String rateDescription;

}
