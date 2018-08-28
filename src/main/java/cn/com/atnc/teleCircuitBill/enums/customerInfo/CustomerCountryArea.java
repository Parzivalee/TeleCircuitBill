package cn.com.atnc.teleCircuitBill.enums.customerInfo;

/**
 * 客户所属国别
 * @author liwenjie
 */
public enum CustomerCountryArea {
    Oversea("境外"),
    Inland("境内"),
    Hk("港澳台");

    private String value;

    CustomerCountryArea(String value) {
        this.value = value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static CustomerCountryArea getCustomerCountryAreaByValue(
            int value) {
        for (CustomerCountryArea type : CustomerCountryArea.values()) {
            if (type.ordinal() == value) {
                return type;
            }
        }
        return null;
    }

    public String toString() {
        return value;

    }
}
