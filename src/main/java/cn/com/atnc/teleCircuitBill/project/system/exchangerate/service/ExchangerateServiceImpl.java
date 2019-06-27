package cn.com.atnc.teleCircuitBill.project.system.exchangerate.service;

import java.util.Date;
import java.util.List;

import cn.com.atnc.teleCircuitBill.project.system.exchangerate.domain.Exchangerate;
import cn.com.atnc.teleCircuitBill.project.system.exchangerate.mapper.ExchangerateMapper;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.com.atnc.teleCircuitBill.common.support.Convert;

/**
 * 汇率信息 服务层实现
 * 
 * @author 
 * @date 2018-12-03
 */
@Service
public class ExchangerateServiceImpl implements IExchangerateService 
{
	@Autowired
	private ExchangerateMapper exchangerateMapper;

	/**
     * 查询汇率信息信息
     * 
     * @param id 汇率信息ID
     * @return 汇率信息信息
     */
    @Override
	public Exchangerate selectExchangerateById(String id)
	{
	    return exchangerateMapper.selectExchangerateById(id);
	}
	
	/**
     * 查询汇率信息列表
     * 
     * @param exchangerate 汇率信息信息
     * @return 汇率信息集合
     */
	@Override
	public List<Exchangerate> selectExchangerateList(Exchangerate exchangerate)
	{
	    return exchangerateMapper.selectExchangerateList(exchangerate);
	}
	
    /**
     * 新增汇率信息
     * 
     * @param exchangerate 汇率信息信息
     * @return 结果
     */
	@Override
	public int insertExchangerate(Exchangerate exchangerate)
	{
	    return exchangerateMapper.insertExchangerate(exchangerate);
	}
	
	/**
     * 修改汇率信息
     * 
     * @param exchangerate 汇率信息信息
     * @return 结果
     */
	@Override
	public int updateExchangerate(Exchangerate exchangerate)
	{
	    return exchangerateMapper.updateExchangerate(exchangerate);
	}

	/**
     * 删除汇率信息对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteExchangerateByIds(String ids)
	{
		return exchangerateMapper.deleteExchangerateByIds(Convert.toStrArray(ids));
	}

	@Override
	public float findExchangeRateByTime(Date rateTime) {
		List<Exchangerate> list = exchangerateMapper.findExchangeRateByTime(rateTime);

		if (!CollectionUtils.isEmpty(list)) {
			return list.get(0).getRate();
		}
		return 0;
	}

}
