package cn.com.atnc.teleCircuitBill.project.costmng.circuitfeemng.oversea.service;

import cn.com.atnc.teleCircuitBill.project.costmng.circuitfeemng.oversea.domain.OverseaFee;

import java.util.List;

/**
 * 境内电路月租资费业务逻辑层接口
 * @author liwenjie
 * @date 2018-09-07
 */
public interface OverseaFeeService {
    /**
     * 根据条件查询记录
     * @param overseaFee
     * @return
     */
    List<OverseaFee> selectOverseaFeeList(OverseaFee overseaFee);

    /**
     * 新增境外电路月租资费
     * @param overseaFee
     * @return
     */
    int insertOverseaFee(OverseaFee overseaFee);

    /**
     * 根据id查询记录
     * @param overseaFeeId
     * @return
     */
    OverseaFee selectOverseaFeeById(String overseaFeeId);

    /**
     * 修改记录
     * @param overseaFee
     * @return
     */
    int updateOverseaFee(OverseaFee overseaFee);

    /**
     * 删除记录
     * @param ids
     * @return
     */
    int deleteOverseaFeeByIds(String ids);
}
