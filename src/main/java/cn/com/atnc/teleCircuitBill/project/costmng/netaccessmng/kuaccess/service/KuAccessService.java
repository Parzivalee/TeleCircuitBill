package cn.com.atnc.teleCircuitBill.project.costmng.netaccessmng.kuaccess.service;

import cn.com.atnc.teleCircuitBill.project.costmng.netaccessmng.caccess.domain.CAccessFee;
import cn.com.atnc.teleCircuitBill.project.costmng.netaccessmng.kuaccess.domain.KuAccessFee;

import java.util.List;

/**
 * Ku波段入网资费业务逻辑接口
 * @author liwenjie
 * @date 2018-10-31
 */
public interface KuAccessService {
    /**
     * 根据条件查询记录
     * @param kuAccessFee
     * @return
     */
    List<KuAccessFee> selectKuAccessFeeList(KuAccessFee kuAccessFee);

    /**
     * 新增Ku波段入网资费
     * @param kuAccessFee
     * @return
     */
    int insertKuAccessFee(KuAccessFee kuAccessFee);

    /**
     * 根据id查询记录
     * @param kuAccessFeeId
     * @return
     */
    KuAccessFee selectKuAccessFeeById(String kuAccessFeeId);

    /**
     * 修改记录
     * @param kuAccessFee
     * @return
     */
    int updateKuAccessFee(KuAccessFee kuAccessFee);

    /**
     * 删除记录
     * @param ids
     * @return
     */
    int deleteByIds(String ids);

}
