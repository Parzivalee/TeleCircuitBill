package cn.com.atnc.project.customerInfo.countrys.service;

import cn.com.atnc.project.customerInfo.countrys.domain.Country;
import cn.com.atnc.project.customerInfo.countrys.mapper.CountryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 洲与国家 Service层实现类
 */
@Service
public class CountryServiceImpl implements CountryService{

    @Autowired
    private CountryMapper countryMapper;

    @Override
    public List<Country> selectCountryList(Country country) {
        return countryMapper.selectCountryList(country);
    }

    @Override
    public Country selectCountryByCountryName(String countryName) {
        return countryMapper.selectCountryByCountryName(countryName);
    }

    @Override
    public List<Country> selectCountryByContinent(String continent) {
        return countryMapper.selectCountryByContinent(continent);
    }

    @Override
    public Country selectCountryById(Long countryId) {
        return countryMapper.selectCountryById(countryId);
    }

    @Override
    public int deleteCountryById(Long countryId) {
        return countryMapper.deleteCountryById(countryId);
    }

    @Override
    public int deleteCountryByIds(Long[] ids) {
        return countryMapper.deleteCountryByIds(ids);
    }

    @Override
    public int updateCountry(Country country) {
        return countryMapper.updateCountry(country);
    }

    @Override
    public int insertCountry(Country country) {
        return countryMapper.insertCountry(country);
    }

}
