package cn.com.atnc.teleCircuitBill.project.changemng.service;

import java.util.Date;
import java.util.List;

import cn.com.atnc.teleCircuitBill.common.utils.security.ShiroUtils;
import cn.com.atnc.teleCircuitBill.project.changemng.domain.HistoryData;
import cn.com.atnc.teleCircuitBill.project.changemng.mapper.HistoryDataMapper;
import cn.com.atnc.teleCircuitBill.project.circuitmng.circuitInfo.domain.Circuit;
import cn.com.atnc.teleCircuitBill.project.contractmng.contractInfo.domain.ContractInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.com.atnc.teleCircuitBill.common.support.Convert;

/**
 * 历史数据管理 服务层实现
 * 
 * @author 
 * @date 2019-01-02
 */
@Service
public class HistoryDataServiceImpl implements IHistoryDataService
{
	@Autowired
	private HistoryDataMapper historyDataMapper;

	/**
     * 查询电路变更信息
     * 
     * @param id 历史数据管理ID
     * @return 历史数据管理信息
     */
    @Override
	public HistoryData selectCircuitChangeById(String id)
	{
	    return historyDataMapper.selectCircuitChangeById(id);
	}

	/**
	 * 查询合同变更信息
	 *
	 * @param id 历史数据管理ID
	 * @return 历史数据管理信息
	 */
	@Override
	public HistoryData selectContractChangeById(String id)
	{
		return historyDataMapper.selectContractChangeById(id);
	}

	/**
     * 查询历史数据管理列表
     * 
     * @param historyData 历史数据管理信息
     * @return 历史数据管理集合
     */
	@Override
	public List<HistoryData> selectHistoryDataList(HistoryData historyData)
	{
	    return historyDataMapper.selectHistoryDataList(historyData);
	}
	
    /**
     * 新增历史数据管理
     * 
     * @param historyData 历史数据管理信息
     * @return 结果
     */
	@Override
	public int insertHistoryData(HistoryData historyData)
	{
	    return historyDataMapper.insertHistoryData(historyData);
	}
	
	/**
     * 修改历史数据管理
     * 
     * @param historyData 历史数据管理信息
     * @return 结果
     */
	@Override
	public int updateHistoryData(HistoryData historyData)
	{
		historyData.setUpdateBy(ShiroUtils.getLoginName());
	    return historyDataMapper.updateHistoryData(historyData);
	}

	@Override
	public int insertCircuitHistoryData(String changeType, String operating,String changeContent, String changNumber) {
		HistoryData circuitHistoryData= new HistoryData();
		circuitHistoryData.setChangeType(changeType);
		circuitHistoryData.setChangeNumber(changNumber);
		circuitHistoryData.setOperatorDate(new Date());
		circuitHistoryData.setOperating(operating);
		circuitHistoryData.setChangeContent(changeContent);
		circuitHistoryData.setCreateBy(ShiroUtils.getLoginName());

		return historyDataMapper.insertHistoryData(circuitHistoryData);
	}

}
