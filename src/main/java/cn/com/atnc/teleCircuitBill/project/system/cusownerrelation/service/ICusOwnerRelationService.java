package cn.com.atnc.teleCircuitBill.project.system.cusownerrelation.service;

import cn.com.atnc.teleCircuitBill.project.system.cusownerrelation.domain.CusOwnerRelation;

import java.util.List;

/**
 * 定义客户-开帐单位关系 服务层
 * 
 * @author liwenjie
 * @date 2018-12-07
 */
public interface ICusOwnerRelationService 
{
	/**
     * 查询定义客户-开帐单位关系信息
     * 
     * @param id 定义客户-开帐单位关系ID
     * @return 定义客户-开帐单位关系信息
     */
	public CusOwnerRelation selectCusOwnerRelationById(String id);
	
	/**
     * 查询定义客户-开帐单位关系列表
     * 
     * @param cusOwnerRelation 定义客户-开帐单位关系信息
     * @return 定义客户-开帐单位关系集合
     */
	public List<CusOwnerRelation> selectCusOwnerRelationList(CusOwnerRelation cusOwnerRelation);

	/**
     * 新增定义客户-开帐单位关系
     * 
     * @param cusOwnerRelation 定义客户-开帐单位关系信息
     * @return 结果
     */
	public int insertCusOwnerRelation(CusOwnerRelation cusOwnerRelation);
	
	/**
     * 修改定义客户-开帐单位关系
     * 
     * @param cusOwnerRelation 定义客户-开帐单位关系信息
     * @return 结果
     */
	public int updateCusOwnerRelation(CusOwnerRelation cusOwnerRelation);
		
	/**
     * 删除定义客户-开帐单位关系信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteCusOwnerRelationByIds(String ids);
	
}
