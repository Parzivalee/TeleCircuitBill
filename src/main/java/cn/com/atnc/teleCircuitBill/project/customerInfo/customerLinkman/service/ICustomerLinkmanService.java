package cn.com.atnc.teleCircuitBill.project.customerInfo.customerLinkman.service;

import cn.com.atnc.teleCircuitBill.project.customerInfo.customerLinkman.domain.CustomerLinkman;
import java.util.List;

/**
 * 联系人 服务层
 * 
 * @author liwenjie
 * @date 2018-12-05
 */
public interface ICustomerLinkmanService 
{
	/**
     * 查询联系人信息
     * 
     * @param id 联系人ID
     * @return 联系人信息
     */
	public CustomerLinkman selectCustomerLinkmanById(String id);
	
	/**
     * 查询联系人列表
     * 
     * @param customerLinkman 联系人信息
     * @return 联系人集合
     */
	public List<CustomerLinkman> selectCustomerLinkmanList(CustomerLinkman customerLinkman);
	
	/**
     * 新增联系人
     * 
     * @param customerLinkman 联系人信息
     * @return 结果
     */
	public int insertCustomerLinkman(CustomerLinkman customerLinkman);
	
	/**
     * 修改联系人
     * 
     * @param customerLinkman 联系人信息
     * @return 结果
     */
	public int updateCustomerLinkman(CustomerLinkman customerLinkman);
		
	/**
     * 删除联系人信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteCustomerLinkmanByIds(String ids);
	
}
