package cn.com.atnc.teleCircuitBill.project.customerInfo.customers.service;

import cn.com.atnc.teleCircuitBill.common.support.Convert;
import cn.com.atnc.teleCircuitBill.common.utils.security.ShiroUtils;
import cn.com.atnc.teleCircuitBill.project.customerInfo.customers.domain.Customer;
import cn.com.atnc.teleCircuitBill.project.customerInfo.customers.mapper.CustomerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 客户信息Service层接口实体类
 * @author liwenjie
 * @date 2018-11-13
 */
@Service
public class CustomerServiceImpl implements CustomerService{

    @Autowired
    private CustomerMapper customerMapper;

    /**
     * 查询客户信息
     * @param customer
     * @return List<Customer>
     */
    @Override
    public List<Customer> selectCustomerList(Customer customer) {
        return customerMapper.selectCustomerList(customer);
    }

    /**
     * 新增客户
     * @param customer
     * @return int
     */
    @Override
    public int insertCustomer(Customer customer) {
        customer.setCreateBy(ShiroUtils.getLoginName());
        return customerMapper.insertCustomer(customer);
    }

    @Override
    public Customer findCustomerByCustomerId(String customerId) {
        return customerMapper.findCustomerByCustomerId(customerId);
    }

    @Override
    public int updateCustomer(Customer customer) {
        customer.setUpdateBy(ShiroUtils.getLoginName());
        return customerMapper.updateCustomer(customer);
    }

    @Override
    public int deleteCountryByIds(String ids) {
        return customerMapper.deleteCountryByIds(Convert.toStrArray(ids));
    }
}
