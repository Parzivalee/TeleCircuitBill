package cn.com.atnc.teleCircuitBill.project.costmng.configfeemng.service;

import java.util.List;

import cn.com.atnc.teleCircuitBill.project.costmng.configfeemng.domain.ConfigFee;
import cn.com.atnc.teleCircuitBill.project.costmng.configfeemng.mapper.ConfigFeeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.com.atnc.teleCircuitBill.common.support.Convert;

/**
 * 配置资费 服务层实现
 * 
 * @author liwenjie
 * @date 2018-12-03
 */
@Service
public class ConfigFeeServiceImpl implements IConfigFeeService 
{
	@Autowired
	private ConfigFeeMapper configFeeMapper;

	/**
     * 查询配置资费信息
     * 
     * @param id 配置资费ID
     * @return 配置资费信息
     */
    @Override
	public ConfigFee selectConfigFeeById(String id)
	{
	    return configFeeMapper.selectConfigFeeById(id);
	}
	
	/**
     * 查询配置资费列表
     * 
     * @param configFee 配置资费信息
     * @return 配置资费集合
     */
	@Override
	public List<ConfigFee> selectConfigFeeList(ConfigFee configFee)
	{
	    return configFeeMapper.selectConfigFeeList(configFee);
	}
	
    /**
     * 新增配置资费
     * 
     * @param configFee 配置资费信息
     * @return 结果
     */
	@Override
	public int insertConfigFee(ConfigFee configFee)
	{
	    return configFeeMapper.insertConfigFee(configFee);
	}
	
	/**
     * 修改配置资费
     * 
     * @param configFee 配置资费信息
     * @return 结果
     */
	@Override
	public int updateConfigFee(ConfigFee configFee)
	{
	    return configFeeMapper.updateConfigFee(configFee);
	}

	/**
     * 删除配置资费对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteConfigFeeByIds(String ids)
	{
		return configFeeMapper.deleteConfigFeeByIds(Convert.toStrArray(ids));
	}
	
}
