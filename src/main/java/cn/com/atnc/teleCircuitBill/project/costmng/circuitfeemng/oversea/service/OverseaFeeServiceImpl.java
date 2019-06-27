package cn.com.atnc.teleCircuitBill.project.costmng.circuitfeemng.oversea.service;

import cn.com.atnc.teleCircuitBill.common.support.Convert;
import cn.com.atnc.teleCircuitBill.common.utils.security.ShiroUtils;
import cn.com.atnc.teleCircuitBill.project.costmng.circuitfeemng.oversea.domain.OverseaFee;
import cn.com.atnc.teleCircuitBill.project.costmng.circuitfeemng.oversea.mapper.OverseaFeeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 境外电路月租资费Service层实现类
 * @author liwenjie
 * @date 2018-09-07
 */
@Service
public class OverseaFeeServiceImpl implements OverseaFeeService {
    @Autowired
    private OverseaFeeMapper overseaFeeMapper;

    @Override
    public List<OverseaFee> selectOverseaFeeList(OverseaFee overseaFee) {
        return overseaFeeMapper.selectOverseaFeeList(overseaFee);
    }

    /**
     * 新增境外月租电路资费
     * @param overseaFee
     * @return
     */
    @Override
    public int insertOverseaFee(OverseaFee overseaFee) {
        overseaFee.setCreateBy(ShiroUtils.getLoginName());
        return overseaFeeMapper.insertOverseaFee(overseaFee);
    }

    /**
     * 根据id查找记录
     * @param overseaFeeId
     * @return
     */
    @Override
    public OverseaFee selectOverseaFeeById(String overseaFeeId) {
        return overseaFeeMapper.selectOverseaFeeById(overseaFeeId);
    }

    @Override
    public int updateOverseaFee(OverseaFee overseaFee) {
        overseaFee.setUpdateBy(ShiroUtils.getLoginName());
        return overseaFeeMapper.updateOverseaFee(overseaFee);
    }

    @Override
    public int deleteOverseaFeeByIds(String ids) {
        return overseaFeeMapper.deleteOverseaFeeByIds(Convert.toStrArray(ids));
    }
}
