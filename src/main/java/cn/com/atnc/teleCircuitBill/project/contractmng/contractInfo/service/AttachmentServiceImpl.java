package cn.com.atnc.teleCircuitBill.project.contractmng.contractInfo.service;

import cn.com.atnc.teleCircuitBill.common.utils.security.ShiroUtils;
import cn.com.atnc.teleCircuitBill.project.contractmng.contractInfo.domain.ContractAttachment;
import cn.com.atnc.teleCircuitBill.project.contractmng.contractInfo.mapper.AttachmentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 合同附件 业务逻辑实现类
 */
@Service
public class AttachmentServiceImpl implements IAttachmentService {
    @Autowired
    private AttachmentMapper attachmentMapper;

    @Override
    public List<ContractAttachment> findAttachmentByContractId(String contractId) {
        return attachmentMapper.findAttachmentByContractId(contractId);
    }

    @Override
    public int insertContractAttachment(ContractAttachment newAttachment) {
        newAttachment.setCreateBy(ShiroUtils.getLoginName());
        return attachmentMapper.insertContractAttachment(newAttachment);
    }

    @Override
    public int removeContractAttachmentById(String id) {
        return attachmentMapper.removeContractAttachmentById(id);
    }

    @Override
    public ContractAttachment findAttachmentById(String attachmentId) {
        return attachmentMapper.findAttachmentById(attachmentId);
    }
}
