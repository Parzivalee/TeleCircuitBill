package cn.com.atnc.teleCircuitBill.project.costmng.netaccessmng.caccess.service;

import cn.com.atnc.teleCircuitBill.common.utils.security.ShiroUtils;
import cn.com.atnc.teleCircuitBill.project.costmng.netaccessmng.caccess.domain.CAccessFee;
import cn.com.atnc.teleCircuitBill.project.costmng.netaccessmng.caccess.mapper.CAccessMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * C波段入网资费业务逻辑层接口
 * @author liwenjie
 * @date 2018-10-19
 */
@Service
public class CAccessServiceImpl implements CAccessService {

    @Autowired
    private CAccessMapper cAccessMapper;

    @Override
    public List<CAccessFee> selectCAccessFeeList(CAccessFee cAccessFee) {
        return cAccessMapper.selectCAccessFeeList(cAccessFee);
    }

    @Override
    public int insertCAccessFee(CAccessFee cAccessFee) {
        cAccessFee.setCreateBy(ShiroUtils.getLoginName());
        return cAccessMapper.insertCAccessFee(cAccessFee);
    }

    @Override
    public CAccessFee selectCAccessFeeById(String cAccessFeeId) {
        return cAccessMapper.selectCAccessFeeById(cAccessFeeId);
    }

    @Override
    public int updateCAccessFee(CAccessFee cAccessFee) {
        cAccessFee.setUpdateBy(ShiroUtils.getLoginName());
        return cAccessMapper.updateCAccessFee(cAccessFee);
    }


}
