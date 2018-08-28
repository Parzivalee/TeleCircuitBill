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
     * @return
     */
    List<Customer> selectCountryList(Customer customer);
}
