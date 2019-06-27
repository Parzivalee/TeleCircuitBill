package cn.com.atnc.teleCircuitBill.project.costmng.netaccessmng.kuaccess.service;


import cn.com.atnc.teleCircuitBill.common.utils.security.ShiroUtils;
import cn.com.atnc.teleCircuitBill.project.costmng.netaccessmng.caccess.domain.CAccessFee;
import cn.com.atnc.teleCircuitBill.project.costmng.netaccessmng.kuaccess.domain.KuAccessFee;
import cn.com.atnc.teleCircuitBill.project.costmng.netaccessmng.kuaccess.mapper.KuAccessMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Ku波段入网资费业务逻辑层接口实现类
 * @author liwenjie
 * @date 2018-10-31
 */
@Service
public class KuAccessServiceImpl implements KuAccessService {

    @Autowired
    private KuAccessMapper kuAccessMapper;

    @Override
    public List<KuAccessFee> selectKuAccessFeeList(KuAccessFee kuAccessFee) {
        return kuAccessMapper.selectKuAccessFeeList(kuAccessFee);
    }

    @Override
    public int insertKuAccessFee(KuAccessFee kuAccessFee) {
        kuAccessFee.setCreateBy(ShiroUtils.getLoginName());
        return kuAccessMapper.insertKuAccessFee(kuAccessFee);
    }

    @Override
    public KuAccessFee selectKuAccessFeeById(String kuAccessFeeId) {
        return kuAccessMapper.selectKuAccessFeeById(kuAccessFeeId);
    }

    @Override
    public int updateKuAccessFee(KuAccessFee kuAccessFee) {
        kuAccessFee.setUpdateBy(ShiroUtils.getLoginName());
        return kuAccessMapper.updateKuAccessFee(kuAccessFee);
    }
}
