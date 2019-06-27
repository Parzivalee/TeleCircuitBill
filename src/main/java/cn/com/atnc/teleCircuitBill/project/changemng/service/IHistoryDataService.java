package cn.com.atnc.teleCircuitBill.project.changemng.service;


import cn.com.atnc.teleCircuitBill.project.changemng.domain.HistoryData;
import cn.com.atnc.teleCircuitBill.project.circuitmng.circuitInfo.domain.Circuit;
import cn.com.atnc.teleCircuitBill.project.contractmng.contractInfo.domain.ContractInfo;

import java.util.List;

/**
 * 历史数据管理 服务层
 * 
 * @author 
 * @date 2019-01-02
 */
public interface IHistoryDataService
{
	/**
     * 查询电路变更信息
     * 
     * @param id id
     * @return 历史数据管理信息
     */
	public HistoryData selectCircuitChangeById(String id);

	/**
	 * 查询合同变更信息
	 *
	 * @param id id
	 * @return 历史数据管理信息
	 */
	public HistoryData selectContractChangeById(String id);
	
	/**
     * 查询历史数据管理列表
     * 
     * @param historyData 历史数据管理信息
     * @return 历史数据管理集合
     */
	public List<HistoryData> selectHistoryDataList(HistoryData historyData);
	
	/**
     * 新增历史数据管理
     * 
     * @param historyData 历史数据管理信息
     * @return 结果
     */
	public int insertHistoryData(HistoryData historyData);
	
	/**
     * 修改历史数据管理
     * 
     * @param historyData 历史数据管理信息
     * @return 结果
     */
	public int updateHistoryData(HistoryData historyData);

	/**
	 * 新增历史数据信息
	 * @param operating
	 * @param changeType
	 * @param changeContent
	 * @param changeNumber
	 * @return
	 */
	int insertCircuitHistoryData(String changeType,String operating,String changeContent,String changeNumber);

}

