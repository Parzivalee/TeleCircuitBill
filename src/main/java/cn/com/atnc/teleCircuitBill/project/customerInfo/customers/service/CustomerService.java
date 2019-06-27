package cn.com.atnc.teleCircuitBill.project.customerInfo.customers.service;

import cn.com.atnc.teleCircuitBill.project.customerInfo.customers.domain.Customer;

import java.util.List;

/**
 * 客户信息Service层接口
 * @author liwenjie
 */
public interface CustomerService {
    /**
     * 查询所有客户对象
     * @param customer
     * @return List<Customer>
     */
    List<Customer> selectCustomerList(Customer customer);

    /**
     * 新增客户
     * @param customer
     * @return
     */
    int insertCustomer(Customer customer);

    /**
     * 根据customerId查找客户信息
     * @param customerId
     * @return Customer
     */
    Customer findCustomerByCustomerId(String customerId);

    /**
     * 修改客户
     * @param customer
     * @return
     */
    int updateCustomer(Customer customer);

    /**
     * 删除客户
     * @param ids
     * @return
     */
    int deleteCountryByIds(String ids);
}
