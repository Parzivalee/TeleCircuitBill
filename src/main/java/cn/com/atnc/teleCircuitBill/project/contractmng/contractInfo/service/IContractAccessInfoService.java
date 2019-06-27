package cn.com.atnc.teleCircuitBill.project.contractmng.contractInfo.service;

import cn.com.atnc.teleCircuitBill.project.contractmng.contractInfo.domain.ContractAccessInfo;
import java.util.List;

/**
 * 合同入网 服务层
 * 
 * @author 
 * @date 2019-01-15
 */
public interface IContractAccessInfoService 
{
	/**
     * 查询合同入网信息
     * 
     * @param id 合同入网ID
     * @return 合同入网信息
     */
	public ContractAccessInfo selectContractAccessInfoById(String id);
	
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
     * 删除合同入网信息
     * 
     * @param contractAccessId 需要删除的数据ID
     * @return 结果
     */
	int deleteContractAccessInfoById(String contractAccessId);

	/**
	 * 根据合同Id查找合同入网信息
	 * @param contractId
	 * @return
	 */
	List<ContractAccessInfo> selectContractAccessInfoByContractId(String contractId);

	/**
	 * 根据客户Id查找合同入网信息
	 * @param customerId
	 * @return
	 */
	List<ContractAccessInfo> selectContractAccessInfoByCustomerId(String customerId);
}
