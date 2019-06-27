package cn.com.atnc.teleCircuitBill.project.system.taxrate.service;

import cn.com.atnc.teleCircuitBill.project.system.taxrate.domain.Taxrate;

import java.util.List;

/**
 * 税率信息 服务层
 * 
 * @author liwenjie
 * @date 2018-12-03
 */
public interface ITaxrateService 
{
	/**
     * 查询税率信息信息
     * 
     * @param id 税率信息ID
     * @return 税率信息信息
     */
	public Taxrate selectTaxrateById(String id);
	
	/**
     * 查询税率信息列表
     * 
     * @param taxrate 税率信息信息
     * @return 税率信息集合
     */
	public List<Taxrate> selectTaxrateList(Taxrate taxrate);
	
	/**
     * 新增税率信息
     * 
     * @param taxrate 税率信息信息
     * @return 结果
     */
	public int insertTaxrate(Taxrate taxrate);
	
	/**
     * 修改税率信息
     * 
     * @param taxrate 税率信息信息
     * @return 结果
     */
	public int updateTaxrate(Taxrate taxrate);
		
	/**
     * 删除税率信息信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteTaxrateByIds(String ids);
	
}
