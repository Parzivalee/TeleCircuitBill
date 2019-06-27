package cn.com.atnc.teleCircuitBill.project.costmng.circuitfeemng.domestic.service;

import cn.com.atnc.teleCircuitBill.common.support.Convert;
import cn.com.atnc.teleCircuitBill.common.utils.security.ShiroUtils;
import cn.com.atnc.teleCircuitBill.project.costmng.circuitfeemng.domestic.domain.DomesticFee;
import cn.com.atnc.teleCircuitBill.project.costmng.circuitfeemng.domestic.mapper.DomesticFeeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;

/**
 * 境内电路月租费用service层实现类
 *
 */
@Service
public class DomesticFeeServiceImpl implements DomesticFeeService {

    @Autowired
    private DomesticFeeMapper domesticFeeMapper;

    /**
     * 查找所有的境内电路月租费用
     * @param domesticFee
     * @return
     */
    @Override
    public List<DomesticFee> selectDomesticFeeList(DomesticFee domesticFee) {
        List<DomesticFee> domesticFees =  domesticFeeMapper.selectDomesticFeeList(domesticFee);
        //获取每条电路的总月租资费
        for (DomesticFee domesticFee1:domesticFees) {
            domesticFee1.setTotalMonthFee(domesticFee1.getPortFee()+domesticFee1.getVcMonthFee());
        }

        return domesticFees;
    }

    /**
     * 新增境内电路月租资费
     * @param domesticFee
     * @return
     */
    @Override
    public int insertDomesticFee(DomesticFee domesticFee) {
        domesticFee.setCreateBy(ShiroUtils.getLoginName());
        return domesticFeeMapper.insertDomesticFee(domesticFee);
    }

    /**
     * 批量删除记录
     * @param ids
     * @return
     */
    @Override
    public int deleteDomesticFeeByIds(String ids) {
        Long[] domesticFeeIds = Convert.toLongArray(ids);
        return domesticFeeMapper.deleteDomesticFeeByIds(domesticFeeIds);
    }

    /**
     * 根据id查找记录
     * @param domesticFeeId
     * @return
     */
    @Override
    public DomesticFee selectDomesticFeeById(String domesticFeeId) {
        return domesticFeeMapper.selectDomesticFeeById(domesticFeeId);
    }

    /**
     * 更新境内电路月租资费信息
     * @param domesticFee
     * @return
     */
    @Override
    public int updateDomesticFee(DomesticFee domesticFee) {
        domesticFee.setUpdateBy(ShiroUtils.getLoginName());
        return domesticFeeMapper.updateDomesticFee(domesticFee);
    }
}
