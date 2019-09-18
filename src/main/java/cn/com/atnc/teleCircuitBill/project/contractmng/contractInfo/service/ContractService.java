package cn.com.atnc.teleCircuitBill.project.contractmng.contractInfo.service;

import cn.com.atnc.teleCircuitBill.project.contractmng.contractInfo.domain.ContractInfo;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 合同信息Service层接口
 * @author liwenjie
 */
public interface ContractService {
    /**
     * 根据条件查询合同信息
     * @param contractInfo
     * @return
     */
    List<ContractInfo> selectContractList(ContractInfo contractInfo);

    /**
     * 根据合同id查找合同
     * @param contractId
     * @return
     */
    ContractInfo selectContractByContractId(String contractId);

    /**
     * 根据合同类型查找合同列表
     * @param contractType
     * @return List<ContractInfo>
     */
    List<ContractInfo> selectContractByContractType(String contractType);

    /**
     * 根据id逻辑删除合同
     * @param ids
     * @return
     */
    int removeContractByIds(String ids);

    /**
     * 新增合同
     * @param contractInfo
     * @return int
     */
    int insertContract(ContractInfo contractInfo);

    /**
     * 修改合同
     * @param contract
     * @return
     */
    int updateContract(ContractInfo contract);

    /**
     * 根据客户id查找合同
     * @param customerId
     * @return
     */
    List<ContractInfo> selectContractByCustomerId(String customerId);

    /**
     * 上传合同附件
     * @param contractId
     * @param attachmentType
     * @param file
     */
    void uploadFiles(String contractId, String attachmentType, MultipartFile[] file);

    /**
     *
     * @param contract 修改的合同信息
     * @param oldContract 数据库的合同比较信息
     * @return String
     */
    String compareContract(ContractInfo contract,ContractInfo oldContract);

    /**
     * 获取最新（一周内）的新签合同信息
     * @return
     */
    String getLatestContractInfos();

    /**
     * 检验合同编号是否唯一（修改）
     * @param contractNumber 合同编号
     * @param contractId 合同Id
     * @return String
     */
    String checkContractNumberUnique(String contractNumber,String contractId);

    /**
     * 检验合同编号是否唯一（变更）
     * @param contractNumber 合同编号
     * @return String
     */
    String checkContractNumberUniqueChange(String contractNumber);

    /**
     * 变更合同
     * @param contract 合同Model
     * @param contractNumberNew 新的合同编号
     * @return
     */
    int changeContract(ContractInfo contract, String contractNumberNew);

    /**
     * 判断合同是否过期
     */
    void checkContractIsExpired();

    /**
     * 根据客户和合同类型查找生效的合同
     * @param customerId
     * @param contractType
     * @return
     */
    List<ContractInfo> selectContractsByCustomerAndType(String customerId, String contractType);


}
