package cn.com.atnc.teleCircuitBill.project.contractmng.contractInfo.domain;

import cn.com.atnc.teleCircuitBill.framework.web.domain.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * 合同附件 实体类
 * @author liwenjie
 */
@Getter
@Setter
public class ContractAttachment extends BaseEntity {

    private static final long serialVersionUID = 7962793743299442383L;
    //
    private String attachmentId;
    //关联合同
    private ContractInfo contractInfo;
    //附件类型
    private String attachmentType;
    //上传日期
    private Date uploadDate;
    //电子文件名称
    private String elecDocumentName;
    //电子文件路径
    private String elecDocumentPath;
    //扫描文件名称
    private String scanDocumentName;
    //扫描文件路径
    private String scanDocumentPath;
}
