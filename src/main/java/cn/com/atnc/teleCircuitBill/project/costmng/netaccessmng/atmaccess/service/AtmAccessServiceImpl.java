package cn.com.atnc.teleCircuitBill.project.costmng.netaccessmng.atmaccess.service;

import cn.com.atnc.teleCircuitBill.common.support.Convert;
import cn.com.atnc.teleCircuitBill.common.utils.security.ShiroUtils;
import cn.com.atnc.teleCircuitBill.project.costmng.netaccessmng.atmaccess.domain.AtmAccessFee;
import cn.com.atnc.teleCircuitBill.project.costmng.netaccessmng.atmaccess.mapper.AtmAccessMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * ATM网节点入网资费Service层接口实现类
 * @author liwenjie
 */
@Service
public class AtmAccessServiceImpl implements AtmAccessService {

    @Autowired
    private AtmAccessMapper atmAccessMapper;

    @Override
    public List<AtmAccessFee> selectAtmAccessFeeList(AtmAccessFee atmAccessFee) {
        return atmAccessMapper.selectAtmAccessFeeList(atmAccessFee);
    }

    @Override
    public int insertAtmAccessFee(AtmAccessFee atmAccessFee) {
        atmAccessFee.setCreateBy(ShiroUtils.getLoginName());
        return atmAccessMapper.insertAtmAccessFee(atmAccessFee);
    }

    @Override
    public AtmAccessFee selectAtmAccessFeeById(String atmAccessFeeId) {
        return atmAccessMapper.selectAtmAccessFeeById(atmAccessFeeId);
    }

    @Override
    public int updateAtmAccessFee(AtmAccessFee atmAccessFee) {
        atmAccessFee.setUpdateBy(ShiroUtils.getLoginName());
        return atmAccessMapper.updateAtmAccessFee(atmAccessFee);
    }

    @Override
    public int deleteByIds(String ids) {
        return atmAccessMapper.deleteByIds(Convert.toStrArray(ids));
    }
}
