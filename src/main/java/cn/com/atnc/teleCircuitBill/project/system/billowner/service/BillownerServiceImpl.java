package cn.com.atnc.teleCircuitBill.project.system.billowner.service;

import java.util.List;

import cn.com.atnc.teleCircuitBill.common.utils.security.ShiroUtils;
import cn.com.atnc.teleCircuitBill.project.system.billowner.domain.Billowner;
import cn.com.atnc.teleCircuitBill.project.system.billowner.mapper.BillownerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.com.atnc.teleCircuitBill.common.support.Convert;

/**
 * 开帐单位 服务层实现
 * 
 * @author 
 * @date 2018-12-03
 */
@Service
public class BillownerServiceImpl implements IBillownerService 
{
	@Autowired
	private BillownerMapper billownerMapper;

	/**
     * 查询开帐单位信息
     * 
     * @param id 开帐单位ID
     * @return 开帐单位信息
     */
    @Override
	public Billowner selectBillownerById(String id)
	{
	    return billownerMapper.selectBillownerById(id);
	}
	
	/**
     * 查询开帐单位列表
     * 
     * @param billowner 开帐单位信息
     * @return 开帐单位集合
     */
	@Override
	public List<Billowner> selectBillownerList(Billowner billowner)
	{
	    return billownerMapper.selectBillownerList(billowner);
	}
	
    /**
     * 新增开帐单位
     * 
     * @param billowner 开帐单位信息
     * @return 结果
     */
	@Override
	public int insertBillowner(Billowner billowner)
	{
		billowner.setCreateBy(ShiroUtils.getLoginName());
	    return billownerMapper.insertBillowner(billowner);
	}
	
	/**
     * 修改开帐单位
     * 
     * @param billowner 开帐单位信息
     * @return 结果
     */
	@Override
	public int updateBillowner(Billowner billowner)
	{
		billowner.setUpdateBy(ShiroUtils.getLoginName());
	    return billownerMapper.updateBillowner(billowner);
	}

	/**
     * 删除开帐单位对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteBillownerByIds(String ids)
	{
		return billownerMapper.deleteBillownerByIds(Convert.toStrArray(ids));
	}
	
}
