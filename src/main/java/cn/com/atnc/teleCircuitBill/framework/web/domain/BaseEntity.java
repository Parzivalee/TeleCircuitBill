package cn.com.atnc.teleCircuitBill.framework.web.domain;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

/**
 * Entity基类
 * 
 * @author
 */
@Getter
@Setter
public class BaseEntity implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 搜索值 */
    private String searchValue;

    /** 创建者 */
    private String createBy;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createTime;

    /** 更新者 */
    private String updateBy;

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date updateTime;

    //是否删除
    private String isdel;

    /** 备注 */
    private String remark;

    /** 请求参数 */
    private Map<String, Object> params;

}
