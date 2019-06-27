package cn.com.atnc.teleCircuitBill.project.system.billowner.domain;


import cn.com.atnc.teleCircuitBill.framework.web.domain.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * 定义系统账单中的开账单位，包括ATNC/ATNE/ATMB等表 sys_billowner
 * 
 * @author 
 * @date 2018-12-03
 */
@Getter
@Setter
public class Billowner extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	/**  */
	private String billOwnerId;
	/**  */
	private String billCompany;
	/**  */
	private String billCompanyEn;
	/**  */
	private String billAccountNumber;
	/**  */
	private String billAccountNumberEn;
	/**  */
	private String billAccountBank;
	/**  */
	private String billAccountBankEn;
	/**  */
	private String billCompanyAddress;
	/**  */
	private String billCompanyAddressEn;
	/**  */
	private String billCompanyPostcode;
	/**  */
	private String billCompanyPostcodeEn;
	/**  */
	private String billCompanyTele;
	/**  */
	private String billCompanyTeleEn;
	/**  */
	private String billCompanyFax;
	/**  */
	private String billCompanyFaxEn;
	/**  */
	private String billCompanyLinkman;
	/**  */
	private String billCompanyLinkmanEn;
	//邮编
	private String billCompanyLinkmanEmail;
	/**  */
	private String billMaker;
	/**  */
	private String billMakerEn;
	//纳税人识别号
	private String taxpayerNumber;
	//空管局信息
	private String airTrafficInfo;



}
