package cn.com.atnc.teleCircuitBill.project.costmng.configfeemng.service;

import cn.com.atnc.teleCircuitBill.project.costmng.configfeemng.domain.ConfigFee;
import java.util.List;

/**
 * 配置资费 服务层
 * 
 * @author liwenjie
 * @date 2018-12-03
 */
public interface IConfigFeeService 
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
     * 删除配置资费信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteConfigFeeByIds(String ids);
	
}
