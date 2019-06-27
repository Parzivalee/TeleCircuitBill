package cn.com.atnc.teleCircuitBill.project.costmng.circuitfeemng.oversea.mapper;

import cn.com.atnc.teleCircuitBill.project.costmng.circuitfeemng.oversea.domain.OverseaFee;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *  境外电路月租资费持久层接口
 */
@Repository
public interface OverseaFeeMapper {

    List<OverseaFee> selectOverseaFeeList(OverseaFee overseaFee);

    int insertOverseaFee(OverseaFee overseaFee);

    OverseaFee selectOverseaFeeById(String overseaFeeId);

    int updateOverseaFee(OverseaFee overseaFee);

    int deleteOverseaFeeByIds(String[] toStrArray);

}
