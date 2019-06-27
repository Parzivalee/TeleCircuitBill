package cn.com.atnc.teleCircuitBill.project.billmng.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * 用于记录两个Date之间间隔的Year、Month和Hours
 */
@Getter
@Setter
public class ActualBillDate {
    //账单实际的启付时间
    private Date actualBillDateStart;
    //账单实际的结束时间
    private Date actualBillDateEnd;

    //实际启付时间所在月份需要付账的天数（从1号开始时为0）
    Integer startDays;
    //整月数
    Integer wholeMonths;
    //实际结束时间所在月份需要付账的天数(在当月最后一天结束时为0)
    Integer endDays;
}
