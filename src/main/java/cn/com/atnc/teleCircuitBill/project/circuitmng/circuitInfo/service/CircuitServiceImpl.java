package cn.com.atnc.teleCircuitBill.project.circuitmng.circuitInfo.service;

import cn.com.atnc.teleCircuitBill.common.constant.UserConstants;
import cn.com.atnc.teleCircuitBill.common.support.Convert;
import cn.com.atnc.teleCircuitBill.common.utils.DateUtils;
import cn.com.atnc.teleCircuitBill.common.utils.security.ShiroUtils;
import cn.com.atnc.teleCircuitBill.project.changemng.service.IHistoryDataService;
import cn.com.atnc.teleCircuitBill.project.circuitmng.circuitInfo.domain.Circuit;
import cn.com.atnc.teleCircuitBill.project.circuitmng.circuitInfo.mapper.CircuitMapper;
import cn.com.atnc.teleCircuitBill.project.contractmng.contractInfo.domain.Association;
import cn.com.atnc.teleCircuitBill.project.contractmng.contractInfo.mapper.AssociationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 电路详情业务逻辑接口层
 * @author liwenjie
 */
@Service
public class CircuitServiceImpl implements CircuitService {
    private static final String CIRCUIT = "circuit";
    private static final String ADD = "新增";
    private static final String EDIT = "变更";
    private static final String REMOVE = "取消";

    @Autowired
    private CircuitMapper circuitMapper;
    @Autowired
    private IHistoryDataService historyDataService;
    @Autowired
    private AssociationMapper associationMapper;

    @Override
    public List<Circuit> selectCircuitList(Circuit circuit) {
        return circuitMapper.selectCircuitList(circuit);
    }

    @Override
    public int insertCircuit(Circuit circuit) {
        //新增历史数据信息
        historyDataService.insertCircuitHistoryData(CIRCUIT,ADD,"",circuit.getCircuitCode());
        circuit.setIsFirstConfig(false);
        circuit.setIsExpired(0);
        circuit.setCreateBy(ShiroUtils.getLoginName());
        return circuitMapper.insertCircuit(circuit);
    }

    @Override
    public List<Circuit> selectCircuitByContractId(String contractId) {
        return circuitMapper.selectCircuitByContractId(contractId);
    }

    @Override
    public Circuit selectCircuitById(String circuitId) {
        return circuitMapper.selectCircuitById(circuitId);

    }

    @Override
    public int updateCircuit(Circuit circuit) {
        //新增历史数据信息
        Circuit oldCircuit = this.selectCircuitById(circuit.getCircuitId());
        String changeContent = this.compareCircuit(circuit,oldCircuit);
        historyDataService.insertCircuitHistoryData(CIRCUIT,EDIT,changeContent,oldCircuit.getCircuitCode());

        circuit.setUpdateBy(ShiroUtils.getLoginName());
        return circuitMapper.updateCircuit(circuit);
    }

    @Override
    public int removeCircuitByIds(String ids) {
        //新增历史数据信息
        String[] idArrays = ids.split(",");
        for (int i=0;i<idArrays.length;i++) {
            String circuitCode = this.selectCircuitById(idArrays[i]).getCircuitCode();
            historyDataService.insertCircuitHistoryData(CIRCUIT,REMOVE,"",circuitCode);
        }

        return circuitMapper.removeCircuitByIds(Convert.toStrArray(ids));
    }

    @Override
    public Circuit selectCircuitByCircuitCode(String circuitCode) {
        return circuitMapper.selectCircuitByCircuitCode(circuitCode);
    }

    @Override
    public String compareCircuit(Circuit circuit, Circuit oldCircuit) {
        String difference = "";
        //电路业务类型
        if (circuit.getCircuitServiceType()!=null && oldCircuit.getCircuitServiceType() != null) {
            if (!circuit.getCircuitServiceType().equals(oldCircuit.getCircuitServiceType())) {
                difference+="电路业务类型，";
            }
        }else if (circuit.getCircuitServiceType()!=null || oldCircuit.getCircuitServiceType() != null){
            difference+="电路业务类型，";
        }

        //区域
        if (circuit.getCircuitArea()!=null && oldCircuit.getCircuitArea() != null) {
            if (!circuit.getCircuitArea().equals(oldCircuit.getCircuitArea())) {
                difference+="区域，";
            }
        }else if (circuit.getCircuitArea()!=null || oldCircuit.getCircuitArea() != null){
            difference+="区域，";
        }

        //电路名称
        if (circuit.getCircuitName()!=null && oldCircuit.getCircuitName() != null) {
            if (!circuit.getCircuitName().equals(oldCircuit.getCircuitName())) {
                difference+="电路名称，";
            }
        }else if (circuit.getCircuitName()!=null || oldCircuit.getCircuitName() != null) {
            difference+="电路名称，";
        }
        //电路编号
        if (circuit.getCircuitCode()!=null && oldCircuit.getCircuitCode() != null) {
            if (!circuit.getCircuitCode().equals(oldCircuit.getCircuitCode())) {
                difference+="电路编号，";
            }
        }else if (circuit.getCircuitCode()!=null || oldCircuit.getCircuitCode() != null){
            difference+="电路编号，";
        }
        //电路速率
        if (circuit.getCircuitSpeed()!=null && oldCircuit.getCircuitCode() != null) {
            if (!circuit.getCircuitCode().equals(oldCircuit.getCircuitCode())) {
                difference+="电路速率，";
            }
        }else if (circuit.getCircuitSpeed()!=null || oldCircuit.getCircuitCode() != null) {
            difference+="电路速率，";
        }
        //通信技术服务费
        if (circuit.getCircuitFee()!=null && oldCircuit.getCircuitCode() != null) {
            if (!circuit.getCircuitCode().equals(oldCircuit.getCircuitCode())) {
                difference+="通信技术服务费，";
            }
        }else if (circuit.getCircuitFee()!=null || oldCircuit.getCircuitCode() != null){
            difference+="通信技术服务费，";
        }
        //电路费用
        if (circuit.getCircuitFeeCir()!=null && oldCircuit.getCircuitFeeCir() != null) {
            if (!circuit.getCircuitFeeCir().equals(oldCircuit.getCircuitFeeCir())) {
                difference+="电路费用，";
            }
        }else if (circuit.getCircuitFeeCir()!=null || oldCircuit.getCircuitFeeCir() != null){
            difference+="电路费用，";
        }
        //端口费用
        if (circuit.getCircuitFeePort()!=null && oldCircuit.getCircuitFeePort() != null) {
            if (!circuit.getCircuitFeePort().equals(oldCircuit.getCircuitFeePort())) {
                difference+="端口费用，";
            }
        }else if (circuit.getCircuitFeePort()!=null || oldCircuit.getCircuitFeePort() != null){
            difference+="端口费用，";
        }
        //电路配置费
        if (circuit.getConfigFee()!=null && oldCircuit.getConfigFee() != null) {
            if (!circuit.getConfigFee().equals(oldCircuit.getConfigFee())) {
                difference+="电路配置费，";
            }
        }else if (circuit.getConfigFee()!=null || oldCircuit.getConfigFee() != null){
            difference+="电路配置费，";
        }
        //本端节点
        if (circuit.getHomeEnd()!=null && oldCircuit.getHomeEnd() != null) {
            if (!circuit.getHomeEnd().equals(oldCircuit.getHomeEnd())) {
                difference+="本端节点，";
            }
        }else if (circuit.getHomeEnd()!=null || oldCircuit.getHomeEnd() != null){
            difference+="本端节点，";
        }
        //对端节点
        if (circuit.getOppEnd()!=null && oldCircuit.getOppEnd() != null) {
            if (!circuit.getOppEnd().equals(oldCircuit.getOppEnd())) {
                difference+="对端节点，";
            }
        }else if (circuit.getOppEnd()!=null || oldCircuit.getOppEnd() != null){
            difference+="对端节点，";
        }
        //启付时间
        if (circuit.getOpenTime() !=null && oldCircuit.getOpenTime() != null) {
            if (!circuit.getOpenTime().equals(oldCircuit.getOpenTime())) {
                difference+="启付时间，";
            }
        }else if (circuit.getOpenTime() !=null || oldCircuit.getOpenTime() != null){
            difference+="启付时间，";
        }
        //电路用途
        if (circuit.getUseInfo() !=null && oldCircuit.getUseInfo() != null) {
            if (!circuit.getUseInfo().equals(oldCircuit.getUseInfo())) {
                difference+="电路用途，";
            }
        }else if (circuit.getUseInfo() !=null || oldCircuit.getUseInfo() != null){
            difference+="电路用途，";
        }
        //配置时间
        if (circuit.getConfigTime()!=null && oldCircuit.getConfigTime() != null) {
            if (!circuit.getConfigTime().equals(oldCircuit.getConfigTime())) {
                difference+="配置时间，";
            }
        }else if (circuit.getConfigTime()!=null || oldCircuit.getConfigTime() != null) {
            difference+="配置时间，";
        }
        //取消时间
        if (circuit.getCancelTime()!=null && oldCircuit.getCancelTime() != null) {
            if (!circuit.getCancelTime().equals(oldCircuit.getCancelTime())) {
                difference+="取消时间，";
            }
        }else if (circuit.getCancelTime()!=null || oldCircuit.getCancelTime() != null){
            difference+="取消时间，";
        }
        //分成比例
        if (circuit.getDivideRatio()!=null && oldCircuit.getDivideRatio() != null) {
            if (!circuit.getDivideRatio().equals(oldCircuit.getDivideRatio())) {
                difference+="分成比例，";
            }
        }else if (circuit.getDivideRatio()!=null || oldCircuit.getDivideRatio() != null){
            difference+="分成比例，";
        }
        //运维平台申请编号
        if (circuit.getIomsApplyNumber()!=null && oldCircuit.getIomsApplyNumber() != null) {
            if (!circuit.getIomsApplyNumber().equals(oldCircuit.getIomsApplyNumber())) {
                difference+="运维平台申请编号，";
            }
        }else if (circuit.getIomsApplyNumber()!=null || oldCircuit.getIomsApplyNumber() != null){
            difference+="运维平台申请编号，";
        }
        //依据文件
        if (circuit.getBasisFile()!=null && oldCircuit.getBasisFile() != null) {
            if (!circuit.getBasisFile().equals(oldCircuit.getBasisFile())) {
                difference+="依据文件，";
            }
        }else if (circuit.getBasisFile()!=null || oldCircuit.getBasisFile() != null) {
            difference+="依据文件，";
        }

        return difference;
    }

    @Override
    public List<Circuit> selectCircuitByCustomerAndContract(String customerId, String contractId) {
        List<Association> associationListDB;
        if (contractId != null) {
            associationListDB = associationMapper.selectCircuitByCustomerAndContract(customerId, contractId);
        }else {
            associationListDB = associationMapper.selectAssociationByCustomerId(customerId);
        }
        List<Circuit> circuitListDB = new ArrayList<>();
        if (associationListDB.size()>0) {
            for (Association association:associationListDB) {
                String circuitId = association.getCircuit().getCircuitId();
                Circuit circuit = circuitMapper.selectCircuitById(circuitId);
                circuitListDB.add(circuit);
            }
        }

        return circuitListDB;
    }

    @Override
    public String getLatestOverCircuitInfos() {
        Date today = DateUtils.getNowDate();
        Date day7before = DateUtils.strToDate(DateUtils.getNextDay(DateUtils
                .getStringDateShort(), "7"));

        List<Circuit> list = circuitMapper.selectCircuitListInDays(day7before,today);
        String str = "";
        for (Circuit info : list) {
            str += "<p>" + info.getCircuitName() + " " + info.getCircuitCode()
                    + " " + DateUtils.dateToStr(info.getCancelTime()) + "</p>";
        }
        return str;
    }

    @Override
    public String checkCircuitCodeNameUnique(String circuitCode,String circuitId) {
        List<Circuit> circuits = circuitMapper.checkCircuitCodeNameUnique(circuitCode);

        if (circuits.size() == 0) {
            return UserConstants.USER_NAME_UNIQUE;
        }else if (circuits.size() == 1) {
            if (circuits.get(0).getCircuitId().equals(circuitId)) {
                return UserConstants.USER_NAME_UNIQUE;
            }else {
                return UserConstants.USER_NAME_NOT_UNIQUE;
            }
        }else {
            return UserConstants.USER_NAME_NOT_UNIQUE;
        }

    }

    /**
     * 判断电路是否过期
     */
    @Override
    public void checkCircuitIsExpired() {
        Circuit newCircuit = new Circuit();
        //对所有未过期且有取消日期的电路进行判断
        newCircuit.setIsExpired(0);
        List<Circuit> circuitList = circuitMapper.selectCircuitList(newCircuit);
        //筛选出所有有取消日期的电路
        circuitList.parallelStream()
                .filter(circuit -> circuit.getCancelTime()!=null)
                .forEach(circuit1 -> {
                    Date cancelTime = circuit1.getCancelTime();
                    Date cancelTimeAfter2years = org.apache.commons.lang.time.DateUtils
                            .addYears(cancelTime,2);
                    //获取今天的日期
                    Date todayDate = new Date();

                    //如果电路的取消日期超过2年，则电路过期
                    if (cancelTimeAfter2years.after(todayDate)) {
                        circuit1.setIsExpired(1);
                        circuitMapper.updateCircuit(circuit1);
                    }


                });

    }

}
