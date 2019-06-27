package cn.com.atnc.teleCircuitBill.project.system.cusownerrelation.service;

import java.util.List;

import cn.com.atnc.teleCircuitBill.common.utils.security.ShiroUtils;
import cn.com.atnc.teleCircuitBill.project.system.cusownerrelation.domain.CusOwnerRelation;
import cn.com.atnc.teleCircuitBill.project.system.cusownerrelation.mapper.CusOwnerRelationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.com.atnc.teleCircuitBill.common.support.Convert;

/**
 * 定义客户-开帐单位 服务层实现
 * 
 * @author liwenjie
 * @date 2018-12-07
 */
@Service
public class CusOwnerRelationServiceImpl implements ICusOwnerRelationService
{
	@Autowired
	private CusOwnerRelationMapper cusOwnerRelationMapper;

	/**
     * 查询定义客户-开帐单位信息
     * 
     * @param id 定义客户-开帐单位ID
     * @return 定义客户-开帐单位信息
     */
    @Override
	public CusOwnerRelation selectCusOwnerRelationById(String id)
	{
	    return cusOwnerRelationMapper.selectCusOwnerRelationById(id);
	}
	
	/**
     * 查询定义客户-开帐单位列表
     * 
     * @param cusOwnerRelation 定义客户-开帐单位信息
     * @return 定义客户-开帐单位集合
     */
	@Override
	public List<CusOwnerRelation> selectCusOwnerRelationList(CusOwnerRelation cusOwnerRelation)
	{
	    return cusOwnerRelationMapper.selectCusOwnerRelationList(cusOwnerRelation);
	}
	
    /**
     * 新增定义客户-开帐单位
     * 
     * @param cusOwnerRelation 定义客户-开帐单位信息
     * @return 结果
     */
	@Override
	public int insertCusOwnerRelation(CusOwnerRelation cusOwnerRelation)
	{
		cusOwnerRelation.setCreateBy(ShiroUtils.getLoginName());
	    return cusOwnerRelationMapper.insertCusOwnerRelation(cusOwnerRelation);
	}
	
	/**
     * 修改定义客户-开帐单位
     * 
     * @param cusOwnerRelation 定义客户-开帐单位信息
     * @return 结果
     */
	@Override
	public int updateCusOwnerRelation(CusOwnerRelation cusOwnerRelation)
	{
		cusOwnerRelation.setUpdateBy(ShiroUtils.getLoginName());
	    return cusOwnerRelationMapper.updateCusOwnerRelation(cusOwnerRelation);
	}

	/**
     * 删除定义客户-开帐单位对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteCusOwnerRelationByIds(String ids)
	{
		return cusOwnerRelationMapper.deleteCusOwnerRelationByIds(Convert.toStrArray(ids));
	}
	
}
