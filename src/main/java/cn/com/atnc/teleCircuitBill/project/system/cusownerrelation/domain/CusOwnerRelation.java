package cn.com.atnc.teleCircuitBill.project.system.cusownerrelation.domain;


import cn.com.atnc.teleCircuitBill.framework.web.domain.BaseEntity;
import cn.com.atnc.teleCircuitBill.project.customerInfo.customers.domain.Customer;
import cn.com.atnc.teleCircuitBill.project.system.billowner.domain.Billowner;
import lombok.Getter;
import lombok.Setter;

/**
 * 定义客户-开帐单位关系关系表 cus_owner_relation
 * 
 * @author 李文杰
 * @date 2018-12-07
 */
@Getter
@Setter
public class CusOwnerRelation extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	/**  */
	private String id;
	/**  */
	private Customer customer;
	/**  */
	private Billowner monthBillOwner;
	/**  */
	private Billowner configBillOwner;
	/**  */
	private Billowner accessBillOwner;

}
