package cn.com.atnc.teleCircuitBill.enums.customerInfo;

/**
 * 客户等级
 * @author liwenjie
 */
public enum CustomerLevel {

    High("高"),
    Middle("中"),
    Low("低");

    private String value;

    CustomerLevel(String value) {
        this.value = value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static CustomerLevel getCustomerLevelByValue(int value) {
        for (CustomerLevel level : CustomerLevel.values()) {
            if (level.ordinal() == value) {
                return level;
            }
        }
        return null;
    }

    public String toString() {
        return value;

    }
}
