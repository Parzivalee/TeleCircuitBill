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
     * @param contract
     * @param oldContract
     * @return
     */
    String compareContract(ContractInfo contract,ContractInfo oldContract);

    /**
     * 获取最新（一周内）的新签合同信息
     * @return
     */
    String getLatestContractInfos();

    /**
     * 检验合同编号是否唯一
     * @param contractNumber
     * @return
     */
    String checkContractNumberUnique(String contractNumber,String contractId);

    /**
     * 变更合同
     * @param contract
     * @param contractNumberNew
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
