package cn.com.atnc.teleCircuitBill.project.customerInfo.countrys.domain;

import cn.com.atnc.teleCircuitBill.framework.web.domain.BaseEntity;

/**
 * 国家对象
 * @author lwj
 */
public class Country extends BaseEntity {
    private static final long serialVersionUID = 1L;

    private int countryId;
    //国家名称（中文）
    private String countryCN;
    //国家名称（英文）
    private String countryEN;
    //国家代码
    private String countryCode;
    //国家航空代码
    private String aviationCode;
    //所属大洲
    private String continent;
    //查询model
    private String countryName;

    public String getCountryName() {
        return countryName;
    }



    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public int getCountryId() {
        return countryId;
    }

    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    public String getCountryCN() {
        return countryCN;
    }

    public void setCountryCN(String countryCN) {
        this.countryCN = countryCN;
    }

    public String getCountryEN() {
        return countryEN;
    }

    public void setCountryEN(String countryEN) {
        this.countryEN = countryEN;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getAviationCode() {
        return aviationCode;
    }

    public void setAviationCode(String aviationCode) {
        this.aviationCode = aviationCode;
    }

    public String getContinent() {
        return continent;
    }

    public void setContinent(String continent) {
        this.continent = continent;
    }
}
