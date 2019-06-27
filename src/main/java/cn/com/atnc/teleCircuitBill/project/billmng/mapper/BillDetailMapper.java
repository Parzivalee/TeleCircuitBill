package cn.com.atnc.teleCircuitBill.project.billmng.mapper;

import cn.com.atnc.teleCircuitBill.project.billmng.domain.BillDetail;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 账单明细 数据层
 * 
 * @author  liwenjie
 * @date 2018-12-10
 */
@Repository
public interface BillDetailMapper 
{
	/**
     * 查询账单明细信息
     * 
     * @param id 账单明细ID
     * @return 账单明细信息
     */
	public BillDetail selectBillDetailById(String id);
	
	/**
     * 查询账单明细列表
     * 
     * @param billDetail 账单明细信息
     * @return 账单明细集合
     */
	public List<BillDetail> selectBillDetailList(BillDetail billDetail);
	
	/**
     * 新增账单明细
     * 
     * @param billDetail 账单明细信息
     * @return 结果
     */
	public int insertBillDetail(BillDetail billDetail);
	
	/**
     * 修改账单明细
     * 
     * @param billDetail 账单明细信息
     * @return 结果
     */
	public int updateBillDetail(BillDetail billDetail);

	/**
     * 删除账单明细
     * 
     * @param id 账单明细ID
     * @return 结果
     */
	public int deleteBillDetailById(String id);
	
	/**
     * 批量删除账单明细
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteBillDetailByIds(String[] ids);
	
}