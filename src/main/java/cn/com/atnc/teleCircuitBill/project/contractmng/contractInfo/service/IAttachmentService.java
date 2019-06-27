package cn.com.atnc.teleCircuitBill.project.contractmng.contractInfo.service;

import cn.com.atnc.teleCircuitBill.project.contractmng.contractInfo.domain.ContractAttachment;

import java.util.List;

/**
 * 合同附件接口
 */
public interface IAttachmentService {

    /**
     * 根据合同id找到附件列表
     * @param contractId
     * @return
     */
    List<ContractAttachment> findAttachmentByContractId(String contractId);

    /**
     * 新增合同附件信息
     * @param newAttachment
     * @return
     */
    int insertContractAttachment(ContractAttachment newAttachment);
}
