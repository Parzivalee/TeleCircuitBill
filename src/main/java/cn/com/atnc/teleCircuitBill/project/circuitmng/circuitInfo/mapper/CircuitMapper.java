package cn.com.atnc.teleCircuitBill.project.circuitmng.circuitInfo.mapper;

import cn.com.atnc.teleCircuitBill.project.circuitmng.circuitInfo.domain.Circuit;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * 电路详情dao层接口
 * @author liwenjie
 */
@Repository
public interface CircuitMapper {

    List<Circuit> selectCircuitList(Circuit circuit);

    int insertCircuit(Circuit circuit);

    List<Circuit> selectCircuitByContractId(String contractId);

    Circuit selectCircuitById(String circuitId);

    int updateCircuit(Circuit circuit);

    int removeCircuitByIds(String[] toStrArray);

    Circuit selectCircuitByCircuitCode(String circuitCode);

    List<Circuit> selectCircuitListInDays(@Param("day7before") Date day7before, @Param("today") Date today);

    List<Circuit> checkCircuitCodeNameUnique(String circuitCode);
}
