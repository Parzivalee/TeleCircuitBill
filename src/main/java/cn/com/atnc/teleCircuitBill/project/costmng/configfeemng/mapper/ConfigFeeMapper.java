package cn.com.atnc.teleCircuitBill.project.costmng.configfeemng.mapper;

import cn.com.atnc.teleCircuitBill.project.costmng.configfeemng.domain.ConfigFee;
import org.springframework.stereotype.Repository;

import java.util.List;	

/**
 * 配置资费 数据层
 * 
 * @author liwenjie
 * @date 2018-12-03
 */
@Repository
public interface ConfigFeeMapper 
{
	/**
     * 查询配置资费信息
     * 
     * @param id 配置资费ID
     * @return 配置资费信息
     */
	public ConfigFee selectConfigFeeById(String id);
	
	/**
     * 查询配置资费列表
     * 
     * @param configFee 配置资费信息
     * @return 配置资费集合
     */
	public List<ConfigFee> selectConfigFeeList(ConfigFee configFee);
	
	/**
     * 新增配置资费
     * 
     * @param configFee 配置资费信息
     * @return 结果
     */
	public int insertConfigFee(ConfigFee configFee);
	
	/**
     * 修改配置资费
     * 
     * @param configFee 配置资费信息
     * @return 结果
     */
	public int updateConfigFee(ConfigFee configFee);
	
	/**
     * 删除配置资费
     * 
     * @param id 配置资费ID
     * @return 结果
     */
	public int deleteConfigFeeById(String id);
	
	/**
     * 批量删除配置资费
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteConfigFeeByIds(String[] ids);
	
}