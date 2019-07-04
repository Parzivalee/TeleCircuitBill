package cn.com.atnc.teleCircuitBill.project.contractmng.contractInfo.mapper;

import cn.com.atnc.teleCircuitBill.project.circuitmng.circuitInfo.domain.Circuit;
import cn.com.atnc.teleCircuitBill.project.contractmng.contractInfo.domain.Association;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 合同-电路关联信息Mapper接口
 * @author liwenjie
 * @date 2018-11-12
 */
@Repository
public interface AssociationMapper {

    List<Association> selectAssociationByContractId(String contractId);

    int insertAssociation(Association association);

    List<Association> findAssociationsByCircuitId(String circuitId);

    List<Association> selectAssociationByCustomerId(String customerId);

    List<Association> selectAssociationByCustomerAndType(String customerId,String contractType);

    Association selectAssociationByAssociationId(String associationId);

    int updateAssociation(Association association);

    List<Association> selectCircuitByCustomerAndContract(@Param("customerId") String customerId,
                                                     @Param("contractId") String contractId);

    List<Association> selectAssociationByConditions(@Param("contractType") String contractType,@Param("customerId") String customerId);

}
