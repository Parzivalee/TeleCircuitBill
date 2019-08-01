package cn.com.atnc.teleCircuitBill.project.costmng.netaccessmng.atmaccess.service;

import cn.com.atnc.teleCircuitBill.project.costmng.netaccessmng.atmaccess.domain.AtmAccessFee;
import cn.com.atnc.teleCircuitBill.project.costmng.netaccessmng.caccess.domain.CAccessFee;

import java.util.List;

/**
 * ATM数据通信网节点入网资费Service层
 * @author liwenjie
 * @date 2018-11-01
 */
public interface AtmAccessService {

    /**
     * 根据条件查询记录
     * @param atmAccessFee
     * @return
     */
    List<AtmAccessFee> selectAtmAccessFeeList(AtmAccessFee atmAccessFee);

    /**
     * 新增ATM节点入网资费
     * @param atmAccessFee
     * @return
     */
    int insertAtmAccessFee(AtmAccessFee atmAccessFee);

    /**
     * 根据id查询记录
     * @param atmAccessFeeId
     * @return
     */
    AtmAccessFee selectAtmAccessFeeById(String atmAccessFeeId);

    /**
     * 修改记录
     * @param atmAccessFee
     * @return
     */
    int updateAtmAccessFee(AtmAccessFee atmAccessFee);

    /**
     * 删除记录
     * @param ids
     * @return
     */
    int deleteByIds(String ids);
}
