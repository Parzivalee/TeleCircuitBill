package cn.com.atnc.teleCircuitBill.project.billmng.mapper;

import cn.com.atnc.teleCircuitBill.project.billmng.domain.Bill;
import cn.com.atnc.teleCircuitBill.project.customerInfo.customers.domain.Customer;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 账单信息mapper映射
 * @author liwenjie
 * @date 2018-11-13
 */
@Repository
public interface BillMapper {

    List<Bill> selectBillList(Bill bill);

    int insertBill(Bill bill);

    int removeBillByIds(String[] id);

    List<Bill> findBillsByBillNumber(String billNumber);

    List<Bill> findBillsByBillNumberAndBillCheckCode(String billNumber, String billCheckCode);

    int updateBill(Bill bill);

    Bill findBillByBillId(String billId);

    int findBillsByCustomerAndBillPeriod(@Param("customerId") String customerId,
                                         @Param("billPeriod") String billPeriod);
}
