package cn.com.atnc.teleCircuitBill.project.system.exchangerate.service;

import cn.com.atnc.teleCircuitBill.project.system.exchangerate.domain.Exchangerate;

import java.util.Date;
import java.util.List;

/**
 * 汇率信息 服务层
 * 
 * @author 
 * @date 2018-12-03
 */
public interface IExchangerateService 
{
	/**
     * 查询汇率信息
     * 
     * @param id 汇率信息ID
     * @return 汇率信息
     */
	public Exchangerate selectExchangerateById(String id);
	
	/**
     * 查询汇率信息列表
     * 
     * @param exchangerate 汇率信息
     * @return 汇率信息集合
     */
	public List<Exchangerate> selectExchangerateList(Exchangerate exchangerate);
	
	/**
     * 新增汇率信息
     * 
     * @param exchangerate 汇率信息
     * @return 结果
     */
	public int insertExchangerate(Exchangerate exchangerate);
	
	/**
     * 修改汇率信息
     * 
     * @param exchangerate 汇率信息
     * @return 结果
     */
	public int updateExchangerate(Exchangerate exchangerate);
		
	/**
     * 删除汇率信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteExchangerateByIds(String ids);

	/**
	 * 根据时间获取汇率信息
	 * @param time
	 * @return
	 */
	float findExchangeRateByTime(Date time);
}
