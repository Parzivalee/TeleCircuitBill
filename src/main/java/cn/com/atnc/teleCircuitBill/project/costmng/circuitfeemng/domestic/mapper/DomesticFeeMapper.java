package cn.com.atnc.teleCircuitBill.project.costmng.circuitfeemng.domestic.mapper;

import cn.com.atnc.teleCircuitBill.project.costmng.circuitfeemng.domestic.domain.DomesticFee;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 境内电路月租费用 数据层
 * @author liwenjie
 */
@Repository
public interface DomesticFeeMapper {

    List<DomesticFee> selectDomesticFeeList(DomesticFee domesticFee);

    int insertDomesticFee(DomesticFee domesticFee);

    int deleteDomesticFeeByIds(Long[] domesticFeeIds);

    DomesticFee selectDomesticFeeById(String domesticFeeId);

    int updateDomesticFee(DomesticFee domesticFee);
}
