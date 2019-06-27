package cn.com.atnc.teleCircuitBill.project.contractmng.contractInfo.service;

import java.util.List;

import cn.com.atnc.teleCircuitBill.common.utils.security.ShiroUtils;
import cn.com.atnc.teleCircuitBill.project.contractmng.contractInfo.domain.ContractAccessInfo;
import cn.com.atnc.teleCircuitBill.project.contractmng.contractInfo.mapper.ContractAccessInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.com.atnc.teleCircuitBill.common.support.Convert;

/**
 * 合同入网 服务层实现
 * 
 * @author 
 * @date 2019-01-15
 */
@Service
public class ContractAccessInfoServiceImpl implements IContractAccessInfoService 
{
	@Autowired
	private ContractAccessInfoMapper contractAccessInfoMapper;

	/**
     * 查询合同入网信息
     * 
     * @param id 合同入网ID
     * @return 合同入网信息
     */
    @Override
	public ContractAccessInfo selectContractAccessInfoById(String id)
	{
	    return contractAccessInfoMapper.selectContractAccessInfoById(id);
	}
	
	/**
     * 查询合同入网列表
     * 
     * @param contractAccessInfo 合同入网信息
     * @return 合同入网集合
     */
	@Override
	public List<ContractAccessInfo> selectContractAccessInfoList(ContractAccessInfo contractAccessInfo)
	{
	    return contractAccessInfoMapper.selectContractAccessInfoList(contractAccessInfo);
	}
	
    /**
     * 新增合同入网
     * 
     * @param contractAccessInfo 合同入网信息
     * @return 结果
     */
	@Override
	public int insertContractAccessInfo(ContractAccessInfo contractAccessInfo)
	{
		contractAccessInfo.setCreateBy(ShiroUtils.getLoginName());
		contractAccessInfo.setGenerateAccessBill(false);
	    return contractAccessInfoMapper.insertContractAccessInfo(contractAccessInfo);
	}
	
	/**
     * 修改合同入网
     * 
     * @param contractAccessInfo 合同入网信息
     * @return 结果
     */
	@Override
	public int updateContractAccessInfo(ContractAccessInfo contractAccessInfo)
	{
	    return contractAccessInfoMapper.updateContractAccessInfo(contractAccessInfo);
	}

	/**
     * 删除合同入网
     * 
     * @param contractAccessId 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteContractAccessInfoById(String contractAccessId)
	{
		return contractAccessInfoMapper.deleteContractAccessInfoById(contractAccessId);
	}

	@Override
	public List<ContractAccessInfo> selectContractAccessInfoByContractId(String contractId) {
		return contractAccessInfoMapper.selectContractAccessInfoByContractId(contractId);
	}

	@Override
	public List<ContractAccessInfo> selectContractAccessInfoByCustomerId(String customerId) {
		return contractAccessInfoMapper.selectContractAccessInfoByCustomerId(customerId);
	}

}
