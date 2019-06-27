package cn.com.atnc.teleCircuitBill.project.billmng.service;

import cn.com.atnc.teleCircuitBill.project.billmng.domain.AccessBillDetail;
import cn.com.atnc.teleCircuitBill.project.billmng.domain.ActualBillDate;
import cn.com.atnc.teleCircuitBill.project.billmng.domain.Bill;
import cn.com.atnc.teleCircuitBill.project.billmng.domain.BillDetail;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

/**
 * 账单信息service层接口
 * @author liwenjie
 * @date 2018-11-13
 */
public interface BillService {

    /**
     * 根据查询条件查找账单信息
     * @param bill
     * @return
     */
    List<Bill> selectBillList(Bill bill);

    /**
     * 生成租用/分成账单
     * @param billDetail 账单明细
     * @param billType 账单类型
     * @param customerId 客户id
     * @param associationIds 合同-电路关联id
     * @return
     * @throws ParseException
     */
    int makeBill(BillDetail billDetail, String billType, String customerId, String associationIds) throws ParseException;

    /**
     * 删除账单信息
     * @param ids
     * @return
     */
    int removeBillByIds(String ids);

    /**
     * 根据billId查找账单信息
     * @param billId
     * @return
     */
    Bill findBillByBillId(String billId);

    /**
     * 修改保存账单
     * @param bill
     * @return
     */
    int updateBill(Bill bill);

    /**
     * 根据日期获取电路有效日期和账单日期的交集
     * @param srcBegin 电路启付时间
     * @param srcEnd 电路取消时间
     * @param stdBegin 账单起始时间
     * @param stdEnd 账单结束时间
     * @return
     */
    ActualBillDate accountMonthsDays(Date srcBegin, Date srcEnd,
                                     Date stdBegin, Date stdEnd);

    /**
     * 生成入网账单
     * @param accessBillDetail
     * @param billType
     * @param customerId
     * @param accessTypeId
     * @return
     */
    int makeAccessBill(AccessBillDetail accessBillDetail, String billType, String customerId, String accessTypeId);

    /**
     * 根据账单id查找入网账单明细
     * @param billId
     * @return
     */
    List<AccessBillDetail> selectAccessBillDetailByBillId(String billId);
}
