package cn.com.atnc.teleCircuitBill.project.customerInfo.customerLinkman.domain;


import cn.com.atnc.teleCircuitBill.framework.web.domain.BaseEntity;
import cn.com.atnc.teleCircuitBill.project.customerInfo.customers.domain.Customer;
import lombok.Getter;
import lombok.Setter;

/**
 * 客户单位的联系人表 cus_customer_linkman
 * 
 * @author liwenjie
 * @date 2018-12-05
 */
@Getter
@Setter
public class CustomerLinkman extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	/**  */
	private String linkmanId;
	/**  */
	private Customer customer;
	/**  */
	private String linkmanName;
	/**  */
	private String linkmanDep;
	/**  */
	private String linkmanDuty;
	/**  */
	private String linkmanTel;
	/**  */
	private String linkmanMobile;
	/**  */
	private String linkmanEmail;
	/**  */
	private String linkmanFax;
	/**  */
	private String linkmanPostcode;
	/**  */
	private String linkmanAddress;

}
