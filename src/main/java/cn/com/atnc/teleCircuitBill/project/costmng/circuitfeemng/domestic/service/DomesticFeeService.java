package cn.com.atnc.teleCircuitBill.project.costmng.circuitfeemng.domestic.service;

import cn.com.atnc.teleCircuitBill.project.costmng.circuitfeemng.domestic.domain.DomesticFee;

import java.util.List;

/**
 * 境内电路月租费用Service层接口
 * @author liwenjie
 * @date 2018-09-03
 */
public interface DomesticFeeService {
    /**
     * 查找所有的境内电路月租费用
     * @param domesticFee
     * @return
     */
    List<DomesticFee> selectDomesticFeeList(DomesticFee domesticFee);

    /**
     * 新增境内电路月租资费
     * @param domesticFee
     * @return
     */
    int insertDomesticFee(DomesticFee domesticFee);

    /**
     * 删除记录
     * @param ids
     * @return
     */
    int deleteDomesticFeeByIds(String ids);

    /**
     * 根据id查找记录
     * @param domesticFeeId
     * @return
     */
    DomesticFee selectDomesticFeeById(String domesticFeeId);

    /**
     * 更新境内电路月租资费信息
     * @param domesticFee
     * @return
     */
    int updateDomesticFee(DomesticFee domesticFee);
}
