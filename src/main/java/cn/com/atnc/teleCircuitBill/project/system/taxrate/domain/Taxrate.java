package cn.com.atnc.teleCircuitBill.project.system.taxrate.domain;


import cn.com.atnc.teleCircuitBill.framework.web.domain.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * 定义系统中的税率标准表 sys_taxrate
 * 
 * @author liwenjie
 * @date 2018-12-03
 */
@Getter
@Setter
public class Taxrate extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	/**  */
	private String id;
	/**  */
	private Date taxRateTime;
	/**  */
	private Float taxRate;
	/**  */
	private String taxDescription;


}
