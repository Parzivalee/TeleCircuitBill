package cn.com.atnc.teleCircuitBill.project.changemng.domain;


import cn.com.atnc.teleCircuitBill.framework.web.domain.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * 历史数据管理表 tele_change
 * 
 * @author 
 * @date 2019-01-02
 */

@Getter
@Setter
public class HistoryData extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	/**  */
	private String historyId;
	/** 合同/电路 */
	private String changeType;
	/** 编号 */
	private String changeNumber;
	/** 操作日期 */
	private Date operatorDate;
	/** 操作 */
	private String operating;
	/** 变更内容 */
	private String changeContent;
	/** 依据文件 */
	private String basisFile;
	/** 运维平台申请编号 */
	private String iomsApplyNumber;
	/** 说明 */
	private String remark;

	//创建情况
	private transient String createStatus;
	//修改情况
	private transient String updateStatus;

}
