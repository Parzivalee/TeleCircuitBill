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

    /**
     * 删除附件信息
     * @param id
     * @return
     */
    int removeContractAttachmentById(String id);

    /**
     * 根据id查找附件信息
     * @param attachmentId
     * @return
     */
    ContractAttachment findAttachmentById(String attachmentId);

    /**
     * 根据电子文档路径查找附件信息
     * @param path 路径
     * @return ContractAttachment
     */
    ContractAttachment findAttachmentByElecDocumentPath(String path);

    /**
     * 根据扫描文档路径查找附件信息
     * @param path 路径
     * @return ContractAttachment
     */
    ContractAttachment findAttachmentByScanDocumentPath(String path);

    /**
     * 修改附件信息
     * @param attachment 附件实体
     * @return
     */
    int updateAttachment(ContractAttachment attachment);
}
