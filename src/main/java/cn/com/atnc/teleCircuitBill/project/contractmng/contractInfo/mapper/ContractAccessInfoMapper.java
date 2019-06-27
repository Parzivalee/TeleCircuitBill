package cn.com.atnc.teleCircuitBill.project.contractmng.contractInfo.mapper;

import cn.com.atnc.teleCircuitBill.project.contractmng.contractInfo.domain.ContractAccessInfo;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 合同入网 数据层
 * 
 * @author 
 * @date 2019-01-15
 */
@Repository
public interface ContractAccessInfoMapper 
{
	/**
     * 查询合同入网信息
     * 
     * @param contractAccessId 合同入网ID
     * @return 合同入网信息
     */
	public ContractAccessInfo selectContractAccessInfoById(String contractAccessId);
	
	/**
     * 查询合同入网列表
     * 
     * @param contractAccessInfo 合同入网信息
     * @return 合同入网集合
     */
	public List<ContractAccessInfo> selectContractAccessInfoList(ContractAccessInfo contractAccessInfo);
	
	/**
     * 新增合同入网
     * 
     * @param contractAccessInfo 合同入网信息
     * @return 结果
     */
	public int insertContractAccessInfo(ContractAccessInfo contractAccessInfo);
	
	/**
     * 修改合同入网
     * 
     * @param contractAccessInfo 合同入网信息
     * @return 结果
     */
	public int updateContractAccessInfo(ContractAccessInfo contractAccessInfo);
	
	/**
     * 删除合同入网
     * 
     * @param contractAccessId 合同入网ID
     * @return 结果
     */
	int deleteContractAccessInfoById(String contractAccessId);
	
	/**
     * 批量删除合同入网
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	int deleteContractAccessInfoByIds(String[] ids);

	/**
	 * 根据合同id查找合同入网信息列表
	 * @param contractId
	 * @return
	 */
	List<ContractAccessInfo> selectContractAccessInfoByContractId(String contractId);

	List<ContractAccessInfo> selectContractAccessInfoByCustomerId(String customerId);
}