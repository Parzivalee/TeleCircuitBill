package cn.com.atnc.teleCircuitBill.project.system.exchangerate.mapper;

import cn.com.atnc.teleCircuitBill.project.system.exchangerate.domain.Exchangerate;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * 定义系统中的汇率标准 数据层
 * 
 * @author liwenjie
 * @date 2018-12-03
 */
@Repository
public interface ExchangerateMapper 
{
	/**
     * 查询汇率信息
     * 
     * @param id 定义系统中的汇率标准ID
     * @return 汇率信息
     */
	public Exchangerate selectExchangerateById(String id);
	
	/**
     * 查询定义系统中的汇率标准列表
     * 
     * @param exchangerate 汇率信息
     * @return 定义系统中的汇率标准集合
     */
	public List<Exchangerate> selectExchangerateList(Exchangerate exchangerate);
	
	/**
     * 新增定义系统中的汇率标准
     * 
     * @param exchangerate 汇率信息
     * @return 结果
     */
	public int insertExchangerate(Exchangerate exchangerate);
	
	/**
     * 修改定义系统中的汇率标准
     * 
     * @param exchangerate 汇率信息
     * @return 结果
     */
	public int updateExchangerate(Exchangerate exchangerate);
	
	/**
     * 删除定义系统中的汇率标准
     * 
     * @param id 定义系统中的汇率标准ID
     * @return 结果
     */
	public int deleteExchangerateById(String id);
	
	/**
     * 批量删除定义系统中的汇率标准
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteExchangerateByIds(String[] ids);

	/**
	 * 根据时间获取汇率信息
	 * @param rateTime
	 * @return
	 */
    List<Exchangerate> findExchangeRateByTime(Date rateTime);
}