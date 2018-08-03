package cn.com.atnc.project.customerInfo.countrys.domain;

/**
 * 国家对象
 * @author lwj
 */
public class Country {
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
