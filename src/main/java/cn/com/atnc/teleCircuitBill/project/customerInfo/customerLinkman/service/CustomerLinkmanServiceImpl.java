package cn.com.atnc.teleCircuitBill.project.customerInfo.customerLinkman.service;

import java.util.List;

import cn.com.atnc.teleCircuitBill.common.utils.security.ShiroUtils;
import cn.com.atnc.teleCircuitBill.project.customerInfo.customerLinkman.domain.CustomerLinkman;
import cn.com.atnc.teleCircuitBill.project.customerInfo.customerLinkman.mapper.CustomerLinkmanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.com.atnc.teleCircuitBill.common.support.Convert;

/**
 * 联系人 服务层实现
 * 
 * @author 李文杰
 * @date 2018-12-05
 */
@Service
public class CustomerLinkmanServiceImpl implements ICustomerLinkmanService 
{
	@Autowired
	private CustomerLinkmanMapper customerLinkmanMapper;

	/**
     * 查询联系人信息
     * 
     * @param id 联系人ID
     * @return 联系人信息
     */
    @Override
	public CustomerLinkman selectCustomerLinkmanById(String id)
	{
	    return customerLinkmanMapper.selectCustomerLinkmanById(id);
	}
	
	/**
     * 查询联系人列表
     * 
     * @param customerLinkman 联系人信息
     * @return 联系人集合
     */
	@Override
	public List<CustomerLinkman> selectCustomerLinkmanList(CustomerLinkman customerLinkman)
	{
	    return customerLinkmanMapper.selectCustomerLinkmanList(customerLinkman);
	}
	
    /**
     * 新增联系人
     * 
     * @param customerLinkman 联系人信息
     * @return 结果
     */
	@Override
	public int insertCustomerLinkman(CustomerLinkman customerLinkman)
	{
		customerLinkman.setCreateBy(ShiroUtils.getLoginName());
	    return customerLinkmanMapper.insertCustomerLinkman(customerLinkman);
	}
	
	/**
     * 修改联系人
     * 
     * @param customerLinkman 联系人信息
     * @return 结果
     */
	@Override
	public int updateCustomerLinkman(CustomerLinkman customerLinkman)
	{
		customerLinkman.setUpdateBy(ShiroUtils.getLoginName());
	    return customerLinkmanMapper.updateCustomerLinkman(customerLinkman);
	}

	/**
     * 删除联系人对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteCustomerLinkmanByIds(String ids)
	{
		return customerLinkmanMapper.deleteCustomerLinkmanByIds(Convert.toStrArray(ids));
	}
	
}
