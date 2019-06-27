package cn.com.atnc.teleCircuitBill.project.contractmng.contractInfo.mapper;

import cn.com.atnc.teleCircuitBill.project.contractmng.contractInfo.domain.ContractAttachment;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 合同附件Mapper接口
 */
@Repository
public interface AttachmentMapper {

    List<ContractAttachment> findAttachmentByContractId(String contractId);

    int insertContractAttachment(ContractAttachment contractAttachment);
}
