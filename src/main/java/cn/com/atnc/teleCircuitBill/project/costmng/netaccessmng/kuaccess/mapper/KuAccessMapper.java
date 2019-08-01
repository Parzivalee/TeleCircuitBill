package cn.com.atnc.teleCircuitBill.project.costmng.netaccessmng.kuaccess.mapper;

import cn.com.atnc.teleCircuitBill.project.costmng.netaccessmng.caccess.domain.CAccessFee;
import cn.com.atnc.teleCircuitBill.project.costmng.netaccessmng.kuaccess.domain.KuAccessFee;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Ku波段入网资费Mapper接口
 * @author liwenjie
 * @date 2018-10-31
 */
@Repository
public interface KuAccessMapper {

    List<KuAccessFee> selectKuAccessFeeList(KuAccessFee kuAccessFee);

    int insertKuAccessFee(KuAccessFee kuAccessFee);

    KuAccessFee selectKuAccessFeeById(String kuAccessFeeId);

    int updateKuAccessFee(KuAccessFee kuAccessFee);

    int deleteByIds(String[] toStrArray);
}
