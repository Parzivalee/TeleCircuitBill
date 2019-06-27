package cn.com.atnc.teleCircuitBill.project.system.cusownerrelation.mapper;

import cn.com.atnc.teleCircuitBill.project.system.cusownerrelation.domain.CusOwnerRelation;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 客户-开帐单位关系 数据层
 * 
 * @author 
 * @date 2018-12-07
 */
@Repository
public interface CusOwnerRelationMapper 
{
	/**
     * 查询客户-开帐单位关系信息
     * 
     * @param id 客户-开帐单位关系ID
     * @return 客户-开帐单位关系信息
     */
	public CusOwnerRelation selectCusOwnerRelationById(String id);
	
	/**
     * 查询客户-开帐单位关系列表
     * 
     * @param cusOwnerRelation 客户-开帐单位关系信息
     * @return 客户-开帐单位关系集合
     */
	public List<CusOwnerRelation> selectCusOwnerRelationList(CusOwnerRelation cusOwnerRelation);
	
	/**
     * 新增客户-开帐单位关系
     * 
     * @param cusOwnerRelation 客户-开帐单位关系信息
     * @return 结果
     */
	public int insertCusOwnerRelation(CusOwnerRelation cusOwnerRelation);
	
	/**
     * 修改客户-开帐单位关系
     * 
     * @param cusOwnerRelation 客户-开帐单位关系信息
     * @return 结果
     */
	public int updateCusOwnerRelation(CusOwnerRelation cusOwnerRelation);
	
	/**
     * 删除客户-开帐单位关系
     * 
     * @param id 客户-开帐单位关系ID
     * @return 结果
     */
	public int deleteCusOwnerRelationById(String id);
	
	/**
     * 批量删除客户-开帐单位关系
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteCusOwnerRelationByIds(String[] ids);

	/**
	 * 根据客户id找到开帐单位信息
	 * @param customerId
	 * @return
	 */
	CusOwnerRelation selectCusOwnerRelationByCustomerId(String customerId);
}