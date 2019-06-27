package cn.com.atnc.teleCircuitBill.project.system.billowner.service;

import cn.com.atnc.teleCircuitBill.project.system.billowner.domain.Billowner;

import java.util.List;

/**
 * 开帐单位 服务层
 * 
 * @author 
 * @date 2018-12-03
 */
public interface IBillownerService 
{
	/**
     * 查询开帐单位
     * 
     * @param id 开帐单位ID
     * @return 开帐单位
     */
	public Billowner selectBillownerById(String id);
	
	/**
     * 查询开帐单位列表
     * 
     * @param billowner 开帐单位
     * @return 开帐单位集合
     */
	public List<Billowner> selectBillownerList(Billowner billowner);
	
	/**
     * 新增开帐单位
     * 
     * @param billowner 开帐单位
     * @return 结果
     */
	public int insertBillowner(Billowner billowner);
	
	/**
     * 修改开帐单位
     * 
     * @param billowner 开帐单位
     * @return 结果
     */
	public int updateBillowner(Billowner billowner);
		
	/**
     * 删除开帐单位
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteBillownerByIds(String ids);
	
}
