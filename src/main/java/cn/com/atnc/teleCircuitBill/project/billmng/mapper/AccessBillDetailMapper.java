package cn.com.atnc.teleCircuitBill.project.billmng.mapper;

import cn.com.atnc.teleCircuitBill.project.billmng.domain.AccessBillDetail;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 入网账单明细 数据层
 * 
 * @author lwj
 * @date 2019-01-16
 */
@Repository
public interface AccessBillDetailMapper {
	/**
     * 查询入网账单明细信息
     * 
     * @param id 入网账单明细ID
     * @return 入网账单明细信息
     */
	public AccessBillDetail selectAccessBillDetailById(String id);
	
	/**
     * 查询入网账单明细列表
     * 
     * @param accessBillDetail 入网账单明细信息
     * @return 入网账单明细集合
     */
	public List<AccessBillDetail> selectAccessBillDetailList(AccessBillDetail accessBillDetail);
	
	/**
     * 新增入网账单明细
     * 
     * @param accessBillDetail 入网账单明细信息
     * @return 结果
     */
	public int insertAccessBillDetail(AccessBillDetail accessBillDetail);
	
	/**
     * 修改入网账单明细
     * 
     * @param accessBillDetail 入网账单明细信息
     * @return 结果
     */
	public int updateAccessBillDetail(AccessBillDetail accessBillDetail);
	
	/**
     * 删除入网账单明细
     * 
     * @param id 入网账单明细ID
     * @return 结果
     */
	public int deleteAccessBillDetailById(String id);
	
	/**
     * 批量删除入网账单明细
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteAccessBillDetailByIds(String[] ids);

    List<AccessBillDetail> selectAccessBillDetailByBillId(String billId);
}