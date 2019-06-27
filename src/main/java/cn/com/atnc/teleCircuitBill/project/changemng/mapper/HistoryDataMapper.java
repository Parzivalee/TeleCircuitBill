package cn.com.atnc.teleCircuitBill.project.changemng.mapper;

import cn.com.atnc.teleCircuitBill.project.changemng.domain.HistoryData;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 历史数据管理 数据层
 * 
 * @author 
 * @date 2019-01-02
 */
@Repository
public interface HistoryDataMapper
{
	/*
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
	int insertHistoryData(HistoryData historyData);
	
	/**
     * 修改历史数据管理
     * 
     * @param historyData 历史数据管理信息
     * @return 结果
     */
	int updateHistoryData(HistoryData historyData);

	HistoryData selectCircuitChangeById(String id);

	HistoryData selectContractChangeById(String id);

}