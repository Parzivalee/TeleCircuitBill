package cn.com.atnc.teleCircuitBill.project.costmng.netaccessmng.atmaccess.mapper;

import cn.com.atnc.teleCircuitBill.project.costmng.netaccessmng.atmaccess.domain.AtmAccessFee;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * ATM节点入网资费Mapper接口
 * @author liwenjie
 * @date 2018-11-01
 */
@Repository
public interface AtmAccessMapper {

    List<AtmAccessFee> selectAtmAccessFeeList(AtmAccessFee atmAccessFee);

    int insertAtmAccessFee(AtmAccessFee atmAccessFee);

    AtmAccessFee selectAtmAccessFeeById(String atmAccessFeeId);

    int updateAtmAccessFee(AtmAccessFee atmAccessFee);

    int deleteByIds(String[] toStrArray);
}
