package cn.com.atnc.teleCircuitBill.project.customerInfo.customers.service;

import cn.com.atnc.teleCircuitBill.project.customerInfo.customers.domain.Customer;
import cn.com.atnc.teleCircuitBill.project.customerInfo.customers.mapper.CustomerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService{

    @Autowired
    private CustomerMapper customerMapper;

    /**
     * 查询客户信息
     * @param customer
     * @return
     */
    @Override
    public List<Customer> selectCountryList(Customer customer) {
        return customerMapper.selectCountryList(customer);
    }
}
