package cn.com.atnc.teleCircuitBill.project.costmng.netaccessmng.caccess.mapper;

import cn.com.atnc.teleCircuitBill.project.costmng.netaccessmng.caccess.domain.CAccessFee;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * C波段入网资费Mapper接口
 * @author liwenjie
 *
 */
@Repository
public interface CAccessMapper {

    List<CAccessFee> selectCAccessFeeList(CAccessFee cAccessFee);

    int insertCAccessFee(CAccessFee cAccessFee);

    CAccessFee selectCAccessFeeById(String cAccessFeeId);

    int updateCAccessFee(CAccessFee cAccessFee);

    int deleteCAccessFeeByIds(String[] cAccessFeeIds);
}
