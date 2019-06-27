package cn.com.atnc.teleCircuitBill.project.contractmng.contractInfo.mapper;

import cn.com.atnc.teleCircuitBill.project.contractmng.contractInfo.domain.ContractInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * 合同信息Mapper接口
 * @author liwenjie
 */
@Repository
public interface ContractMapper {

    List<ContractInfo> selectContractList(ContractInfo contractInfo);

    ContractInfo selectContractByContractId(String contractId);

    List<ContractInfo> selectContractByContractType(String contractType);

    int removeContractByIds(String[] ids);

    int insertContract(ContractInfo contractInfo);

    int updateContract(ContractInfo contract);

    List<ContractInfo> selectContractByCustomerId(String customerId);

    List<ContractInfo> selectContractListInDays(@Param("day7before") Date day7before, @Param("today") Date today);

    List<ContractInfo> checkCircuitCodeNameUnique(String contractNumber);
}
