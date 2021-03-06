package cn.com.atnc.teleCircuitBill.project.customerInfo.customers.mapper;

import cn.com.atnc.teleCircuitBill.project.customerInfo.customers.domain.Customer;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 客户表持久层Mapper
 * @author liwenjie
 */
@Repository
public interface CustomerMapper {

    /**
     * 查询所有客户信息
     *
     * @param customer
     * @return List<Customer>
     */
    List<Customer> selectCustomerList(Customer customer);

    /**
     * 新增客户信息
     * @param customer
     * @return int
     */
    int insertCustomer(Customer customer);

    Customer findCustomerByCustomerId(String customerId);

    int updateCustomer(Customer customer);

    int deleteCountryByIds(String[] toStrArray);
}
