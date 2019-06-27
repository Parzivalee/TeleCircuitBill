package cn.com.atnc.teleCircuitBill.project.system.taxrate.service;

import java.util.List;

import cn.com.atnc.teleCircuitBill.common.utils.security.ShiroUtils;
import cn.com.atnc.teleCircuitBill.project.system.taxrate.domain.Taxrate;
import cn.com.atnc.teleCircuitBill.project.system.taxrate.mapper.TaxrateMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.com.atnc.teleCircuitBill.common.support.Convert;

/**
 * 税率信息 服务层实现
 * 
 * @author 
 * @date 2018-12-03
 */
@Service
public class TaxrateServiceImpl implements ITaxrateService 
{
	@Autowired
	private TaxrateMapper taxrateMapper;

	/**
     * 查询税率信息信息
     * 
     * @param id 税率信息ID
     * @return 税率信息信息
     */
    @Override
	public Taxrate selectTaxrateById(String id)
	{
	    return taxrateMapper.selectTaxrateById(id);
	}
	
	/**
     * 查询税率信息列表
     * 
     * @param taxrate 税率信息信息
     * @return 税率信息集合
     */
	@Override
	public List<Taxrate> selectTaxrateList(Taxrate taxrate)
	{
	    return taxrateMapper.selectTaxrateList(taxrate);
	}
	
    /**
     * 新增税率信息
     * 
     * @param taxrate 税率信息信息
     * @return 结果
     */
	@Override
	public int insertTaxrate(Taxrate taxrate)
	{
		taxrate.setCreateBy(ShiroUtils.getLoginName());
	    return taxrateMapper.insertTaxrate(taxrate);
	}
	
	/**
     * 修改税率信息
     * 
     * @param taxrate 税率信息信息
     * @return 结果
     */
	@Override
	public int updateTaxrate(Taxrate taxrate)
	{
		taxrate.setUpdateBy(ShiroUtils.getLoginName());
	    return taxrateMapper.updateTaxrate(taxrate);
	}

	/**
     * 删除税率信息对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteTaxrateByIds(String ids)
	{
		return taxrateMapper.deleteTaxrateByIds(Convert.toStrArray(ids));
	}
	
}
