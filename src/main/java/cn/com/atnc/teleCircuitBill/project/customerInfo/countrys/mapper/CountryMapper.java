package cn.com.atnc.teleCircuitBill.project.customerInfo.countrys.mapper;

import cn.com.atnc.teleCircuitBill.project.customerInfo.countrys.domain.Country;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 国家表数据层
 * @author lwj
 */
@Repository
public interface CountryMapper {
    /**
     * 根据条件分页查询国家对象
     *
     * @param country
     * @return List<Country>
     */
    List<Country> selectCountryList(Country country);

    /**
     * 通过国家名称查询国家对象
     *
     * @param countryName 国家名称
     * @return Country
     */
    Country selectCountryByCountryName(String countryName);

    /**
     * 通过大洲查询国家信息
     *
     * @param continent 大洲
     * @return List<Country>
     */
    List<Country> selectCountryByContinent(String continent);

    /**
     * 通过国家ID查询国家
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

    /**
     * 批量删除国家
     *
     * @param countryIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteCountryByIds(String[] countryIds);

}
