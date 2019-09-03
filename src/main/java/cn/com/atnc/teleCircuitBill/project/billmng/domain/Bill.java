package cn.com.atnc.teleCircuitBill.project.billmng.domain;

import cn.com.atnc.teleCircuitBill.framework.web.domain.BaseEntity;
import cn.com.atnc.teleCircuitBill.project.customerInfo.customers.domain.Customer;
import cn.com.atnc.teleCircuitBill.project.system.billowner.domain.Billowner;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 账单实体类
 * @author liwenije
 * @date 2018-11-13
 */
@Getter
@Setter
public class Bill extends BaseEntity {

    private static final long serialVersionUID = -1095147751902302315L;

    private String billId;              //Id
    private Customer customer;			//客户实体
    private String billType;            //账单类型
    private Billowner billOwner;		//开账单位
    private String billNumber;			//账单编号
    private String billYear;            //账单年份
    private String billQuarter;         //账单季度
    private String billPeriod;          //账单日期
    private Date billTime;				//开账日期
    private Date billReceiveTime;		//到账时间
    private Double billFeeTotal;		//账单费用
    private String isReceive;			//是否到账
    private String isCancel;			//是否取消
    private String billCheckCode;
    private String isMakeIOBill;		//是否开具收入/分成账单
    private String billInfo;			//账单信息
    private String invoiceCode;       //发票号码
    private Date invoiceTime;           //发票时间
}
