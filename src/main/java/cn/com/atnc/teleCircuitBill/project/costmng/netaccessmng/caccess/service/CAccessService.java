package cn.com.atnc.teleCircuitBill.project.costmng.netaccessmng.caccess.service;

import cn.com.atnc.teleCircuitBill.project.costmng.netaccessmng.caccess.domain.CAccessFee;

import java.util.List;

/**
 * C波段入网资费业务逻辑层接口
 * @author liwenjie
 * @date 2018-10-19
 */
public interface CAccessService {
    /**
     * 根据条件查询记录
     * @param cAccessFee
     * @return
     */
    List<CAccessFee> selectCAccessFeeList(CAccessFee cAccessFee);

    /**
     * 新增境外电路月租资费
     * @param cAccessFee
     * @return
     */
    int insertCAccessFee(CAccessFee cAccessFee);

    /**
     * 根据id查询记录
     * @param cAccessFeeId
     * @return
     */
    CAccessFee selectCAccessFeeById(String cAccessFeeId);

    /**
     * 修改记录
     * @param cAccessFee
     * @return
     */
    int updateCAccessFee(CAccessFee cAccessFee);

    /**
     * 删除记录
     * @param ids
     * @return
     */
    int deleteCAccessFeeById(String ids);
}
