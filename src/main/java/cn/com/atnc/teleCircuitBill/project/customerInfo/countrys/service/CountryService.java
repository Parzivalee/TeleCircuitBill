package cn.com.atnc.teleCircuitBill.project.customerInfo.countrys.service;

import cn.com.atnc.teleCircuitBill.project.customerInfo.countrys.domain.Country;

import java.util.List;

/**
 * 国家 Service层接口
 * @author liwenjie
 */
public interface CountryService {
    /**
     * 根据条件分页查询国家对象
     *
     * @param country
     * @return List<Country>
     */
    List<Country> selectCountryList(Country country);

    /**
     * 通过用户名查询国家对象
     *
     * @param countryName 用户名
     * @return Country
     */
    Country selectCountryByCountryName(String countryName);

    /**
     * 通过大洲查询国家信息
     *
     * @param continent 手机号码
     * @return List<Country>
     */
    List<Country> selectCountryByContinent(String continent);

    /**
     * 通过用户ID查询用户
     *
     * @param countryId 用户ID
     * @return Country
     */
    public Country selectCountryById(Long countryId);

    /**
     * 通过用户ID删除用户
     *
     * @param countryId 国家ID
     * @return
     */
    int deleteCountryById(Long countryId);

    /**
     * 批量删除用户信息
     *
     * @param ids 需要删除的数据ID
     * @return
     */
    int deleteCountryByIds(Long[] ids);

    /**
     * 修改用户信息
     *
     * @param country 用户信息
     * @return 结果
     */
    int updateCountry(Country country);

    /**
     * 新增用户信息
     *
     * @param country 用户信息
     * @return 结果
     */
    int insertCountry(Country country);

    int deleteCountryByIds(String ids);
}
