package cn.com.atnc.teleCircuitBill.project.billmng.service;

import cn.com.atnc.teleCircuitBill.common.support.Convert;
import cn.com.atnc.teleCircuitBill.common.utils.DateUtils;
import cn.com.atnc.teleCircuitBill.common.utils.security.ShiroUtils;
import cn.com.atnc.teleCircuitBill.project.billmng.domain.AccessBillDetail;
import cn.com.atnc.teleCircuitBill.project.billmng.domain.ActualBillDate;
import cn.com.atnc.teleCircuitBill.project.billmng.domain.BillDetail;
import cn.com.atnc.teleCircuitBill.project.billmng.mapper.AccessBillDetailMapper;
import cn.com.atnc.teleCircuitBill.project.billmng.mapper.BillDetailMapper;
import cn.com.atnc.teleCircuitBill.project.billmng.mapper.BillMapper;
import cn.com.atnc.teleCircuitBill.project.billmng.domain.Bill;
import cn.com.atnc.teleCircuitBill.project.circuitmng.circuitInfo.domain.Circuit;
import cn.com.atnc.teleCircuitBill.project.circuitmng.circuitInfo.service.CircuitService;
import cn.com.atnc.teleCircuitBill.project.contractmng.contractInfo.domain.Association;
import cn.com.atnc.teleCircuitBill.project.contractmng.contractInfo.domain.ContractAccessInfo;
import cn.com.atnc.teleCircuitBill.project.contractmng.contractInfo.domain.ContractInfo;
import cn.com.atnc.teleCircuitBill.project.contractmng.contractInfo.mapper.AssociationMapper;
import cn.com.atnc.teleCircuitBill.project.contractmng.contractInfo.mapper.ContractMapper;
import cn.com.atnc.teleCircuitBill.project.contractmng.contractInfo.service.IContractAccessInfoService;
import cn.com.atnc.teleCircuitBill.project.customerInfo.customers.domain.Customer;
import cn.com.atnc.teleCircuitBill.project.customerInfo.customers.mapper.CustomerMapper;
import cn.com.atnc.teleCircuitBill.project.system.billowner.domain.Billowner;
import cn.com.atnc.teleCircuitBill.project.system.billowner.mapper.BillownerMapper;
import cn.com.atnc.teleCircuitBill.project.system.cusownerrelation.domain.CusOwnerRelation;
import cn.com.atnc.teleCircuitBill.project.system.cusownerrelation.mapper.CusOwnerRelationMapper;
import cn.com.atnc.teleCircuitBill.project.system.exchangerate.service.IExchangerateService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 账单信息Service层看接口实现类
 * @author liwenjie
 * @date 2018-11-13
 */
@Service
@Configuration
public class BillServiceImpl implements BillService {

    @Autowired
    private BillMapper billMapper;
    @Autowired
    private AssociationMapper associationMapper;
    @Autowired
    private CusOwnerRelationMapper cusOwnerRelationMapper;
    @Autowired
    private BillownerMapper billownerMapper;
    @Autowired
    private CustomerMapper customerMapper;
    @Autowired
    private IExchangerateService exchangerateService;
    @Autowired
    private BillDetailMapper billDetailMapper;
    @Autowired
    private CircuitService circuitService;
    @Autowired
    private IContractAccessInfoService contractAccessInfoService;
    @Autowired
    private AccessBillDetailMapper accessBillDetailMapper;
    @Autowired
    private ContractMapper contractMapper;
    @Value("${bill.config}")
    private String configBillCodeType;
    @Value("${bill.access}")
    private String accessBillCodeType;
    @Value("${bill.dlzf}")
    private String hireBillCodeType;
    @Value("${bill.dlfc}")
    private String dlfcBillCodeType;

    @Override
    public List<Bill> selectBillList(Bill bill) {
        return billMapper.selectBillList(bill);
    }

    @Override
    public int makeBill(BillDetail billDetail, String billType,
                        String customerId, String associationIds,Date billTime) throws ParseException {
        //返回值标识
        int flag = 0;
        //境内境外
        boolean isAreaIn = true;

        //根据选取的电路获取电路-合同关联信息List
        List<Association> associationList = new ArrayList<>();
        String[] associationIdList = associationIds.split(",");
        for (int i=0;i<associationIdList.length;i++) {
            Association association = associationMapper.selectAssociationByAssociationId(associationIdList[i]);
            if (association!=null)
                associationList.add(association);
        }

        //获取客户信息
        Customer customer  = customerMapper.findCustomerByCustomerId(customerId);
        //找到开帐客户对应的开帐单位信息
        CusOwnerRelation cusOwnerRelation = cusOwnerRelationMapper.selectCusOwnerRelationByCustomerId(customerId);
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String monthBillStart = dateFormat.format(billDetail.getMonthBillStart());
        String monthBillEnd = dateFormat.format(billDetail.getMonthBillEnd());
        String startYear = StringUtils.split(monthBillStart, "-")[0];  //获取开始年份
        String endYear = StringUtils.split(monthBillEnd,"-")[0];
        String startMonth = StringUtils.split(monthBillStart, "-")[1]; //获取开始月份
        String endMonth = StringUtils.split(monthBillEnd, "-")[1];     //获取结束月份

        /*int startMonthI = Integer.parseInt(startMonth);
        int endMonthI = Integer.parseInt(endMonth);
        int startYearI = Integer.parseInt(startYear);
        int endYearI = Integer.parseInt(endYear);*/

        //账单编号
        String billNumber = null;
        //根据客户id找到客户-开帐单位关联信息
        String billOwnerId = null;
        //账单信息
        String billInfo = null;
        //todo（是什么？）
        String billCheckCode = null;

        //生成新的账单
        Bill newBill = new Bill();
        newBill.setCustomer(customer);
        newBill.setBillType(billType);

        if (billType.equals("HireBill")) {
            billNumber = hireBillCodeType + "("+ customer.getCode()+ ")"
                    + startYear + "(" + startMonth + "-" + endMonth + ")";
            //获取客户的租用账单开帐单位信息
            billOwnerId = cusOwnerRelation.getMonthBillOwner().getBillOwnerId();
            //是否生成分成账单
            newBill.setIsMakeIOBill("0");
        }
        if (billType.equals("DevideBill")) {
            //如果是分成账单，查找在同一帐期内的账单数量
            int billCounts = billMapper.findBillsByCustomerAndBillPeriod(customer.getCustomerId(),startYear + "(" + startMonth + "-" + endMonth + ")")+1;

            billNumber = dlfcBillCodeType + "("+ customer.getCode()+ ")"
                    + startYear + "(" + startMonth + "-" + endMonth + ")-"+billCounts;
            //获取客户的分成账单开帐单位信息(TODO)
            billOwnerId = cusOwnerRelation.getMonthBillOwner().getBillOwnerId();
            //是否生成分成账单
            newBill.setIsMakeIOBill("1");
        }
        //如果新增账单是租用账单或者分成账单
        //生成账单时间
        //Date billTime = new Date();

            /*//此电路的账单总费用
            Double billFeeSum = association.getFeeSum()*monthSum;
            if (association.getFeeSum() != null) {
                //账单billInfo
                billInfo = "电路："+ association.getCircuit().getCircuitName()+ " 付费比例：";
                // TODO 付费比例
            }*/


        Billowner billowner = billownerMapper.selectBillownerById(billOwnerId);

        if (billowner != null) {
            newBill.setBillOwner(billowner);
        }
        newBill.setBillYear(startYear);
        int startQuarter = (Integer.parseInt(startMonth) + 2) / 3;  //判断开始月份所属季度
        int endQuarter = (Integer.parseInt(endMonth) + 2) / 3;      //判断结束月份所属季度
        int quarterResult = endQuarter - startQuarter;
        if (quarterResult == 3) {
            newBill.setBillQuarter("1234");
        } else if (quarterResult == 2) {
            if (startQuarter == 1) {
                newBill.setBillQuarter("123");
            } else {
                newBill.setBillQuarter("234");
            }
        } else if (quarterResult == 1) {
            if (startQuarter == 1) {
                newBill.setBillQuarter("12");
            } else if (startQuarter == 2) {
                newBill.setBillQuarter("23");
            } else if (startQuarter == 3) {
                newBill.setBillQuarter("34");
            }
        } else {
            newBill.setBillQuarter(String.valueOf(startQuarter));
        }
        newBill.setBillPeriod(startYear + "(" + startMonth + "-" + endMonth + ")");
        newBill.setBillNumber(billNumber);
        newBill.setBillTime(billTime);
        newBill.setBillFeeTotal(0.00); //先设置总费用为0
        newBill.setIsCancel("0");
        newBill.setIsReceive("0");
        newBill.setCreateBy(ShiroUtils.getLoginName());
        //newBill.setBillInfo(billInfo + "\n");
        flag = billMapper.insertBill(newBill);

        //生成billDetail
        if (associationList.size() > 0) {
            for (Association association : associationList) {
                double actorMonthFeeTotal = 0;
                Circuit circuit = association.getCircuit();
                if (circuit.getCircuitType().startsWith("境外")) {
                    isAreaIn = false;
                }
                //获取账单实际的开始日期和结束日期
                ActualBillDate actualBillDate = new ActualBillDate();
                actualBillDate = this.accountMonthsDays(circuit.getOpenTime(), circuit.getCancelTime(),
                        billDetail.getMonthBillStart(), billDetail.getMonthBillEnd());
                //判断是否为空，为空说明此账单没有billDetial
                if (actualBillDate.getActualBillDateStart() != null && actualBillDate.getActualBillDateEnd() != null) {
                    BillDetail newBillDetail = new BillDetail();
                    newBillDetail.setBill(newBill);
                    newBillDetail.setAssociation(association);

                    newBillDetail.setMonthBillStart(actualBillDate.getActualBillDateStart());
                    newBillDetail.setMonthBillEnd(actualBillDate.getActualBillDateEnd());

                    //Date->String("yyyy-MM-dd")
                    String actualBillDateStartStr = DateUtils.dateToStr(actualBillDate.getActualBillDateStart());
                    //Date->String("yyyy-MM-dd")
                    String actualBillDateEndStr = DateUtils.dateToStr(actualBillDate.getActualBillDateEnd());

                    //判断账期
                    String billDetailMonthStart = DateUtils.dateToStr(billDetail.getMonthBillStart());
                    String billDetailMonthEnd = DateUtils.dateToStr(billDetail.getMonthBillEnd());

                    if (actualBillDateStartStr.equals(billDetailMonthStart) && actualBillDateEndStr.equals(billDetailMonthEnd)) {
                        newBillDetail.setAccountPeriod(actualBillDateStartStr.split("-")[0]+"年"+
                                actualBillDateStartStr.split("-")[1]+"月-"+actualBillDateEndStr.split("-")[1]+"月");
                    }else {
                        newBillDetail.setAccountPeriod(actualBillDateStartStr+"至"+actualBillDateEndStr);
                    }

                    //获取账单开始时间所在月份的天数
                    String billStartDaysOfMonth = DateUtils.getEndDateOfMonth(actualBillDateStartStr);
                    //实际开始账单最后一天
                    String lastDayOfStartMonth = StringUtils.split(billStartDaysOfMonth, "-")[2];
                    //获取账单结束时间所在月份的天数
                    String billEndDaysOfMonth = DateUtils.getEndDateOfMonth(actualBillDateEndStr);
                    //实际结束账单最后一天
                    String lastDayOfEndMonth = StringUtils.split(billEndDaysOfMonth, "-")[2];

                    //开始月份是否是整月
                    if (Integer.parseInt(StringUtils.split(actualBillDateStartStr, "-")[2]) == 1) {
                        //开始月份为整月
                        actualBillDate.setStartDays(0);
                        String skr = StringUtils.split(actualBillDateEndStr, "-")[2];
                        //判断结束月份是否为整月
                        if (StringUtils.split(actualBillDateEndStr, "-")[2].equals(lastDayOfEndMonth)) {
                            actualBillDate.setEndDays(0);
                            actualBillDate.setWholeMonths(DateUtils.getTwoMonths(actualBillDate.getActualBillDateStart(), actualBillDate.getActualBillDateEnd()) + 1);
                        } else {
                            //结束月份不为整月
                            actualBillDate.setEndDays(Integer.parseInt(StringUtils.split(actualBillDateEndStr, "-")[2]));
                            actualBillDate.setWholeMonths(DateUtils.getTwoMonths(actualBillDate.getActualBillDateStart(), actualBillDate.getActualBillDateEnd()));
                        }
                    } else {
                        //开始月份不为整月
                        actualBillDate.setStartDays(Integer.parseInt(lastDayOfStartMonth) - Integer.parseInt(StringUtils.split(actualBillDateStartStr, "-")[2]) + 1);
                        //判断结束月份是否为整月
                        if (StringUtils.split(actualBillDateEndStr, "-")[2] == lastDayOfEndMonth) {
                            actualBillDate.setEndDays(0);
                            actualBillDate.setWholeMonths(DateUtils.getTwoMonths(actualBillDate.getActualBillDateStart(), actualBillDate.getActualBillDateEnd()));
                        } else {
                            //结束月份不为整月
                            actualBillDate.setEndDays(Integer.parseInt(StringUtils.split(actualBillDateEndStr, "-")[2]));
                            actualBillDate.setWholeMonths(DateUtils.getTwoMonths(actualBillDate.getActualBillDateStart(), actualBillDate.getActualBillDateEnd()) - 1);
                        }

                    }

                    if (actualBillDate.getStartDays() == 0 && actualBillDate.getEndDays() == 0) {
                        newBillDetail.setMonthSum(String.valueOf(actualBillDate.getWholeMonths()));
                        //整月* 月租费用
                        actorMonthFeeTotal = Math.round(association.getFeeSum() * actualBillDate.getWholeMonths());
                    } else {
                        int daySum = actualBillDate.getStartDays() + actualBillDate.getEndDays();
                        newBillDetail.setMonthSum(actualBillDate.getWholeMonths() + "个月" + daySum + "天");
                        //整月* 月租费用+开始月份天数费用（若不为整月）+ 结束月份天数费用（若不为整月）
                        actorMonthFeeTotal = association.getFeeSum() * actualBillDate.getWholeMonths() +
                                association.getFeeSum() / (Integer.parseInt(lastDayOfStartMonth)) * actualBillDate.getStartDays() +
                                association.getFeeSum() / (Integer.parseInt(lastDayOfEndMonth)) * actualBillDate.getEndDays();

                    }
                    //获取汇率信息
                    float exchangeRate = exchangerateService.findExchangeRateByTime(dateFormat.parse(dateFormat.format(billTime)));
                    //如果是境外，则乘以汇率
                    if (!isAreaIn) {
                        actorMonthFeeTotal = actorMonthFeeTotal * exchangeRate;
                    }

                    newBillDetail.setMonthBillStart(actualBillDate.getActualBillDateStart());
                    newBillDetail.setMonthBillEnd(actualBillDate.getActualBillDateEnd());
                    newBillDetail.setMonthEveryFee(association.getFeeSum());
                    //更新Bill账单的费用总值
                    newBill.setBillFeeTotal(newBill.getBillFeeTotal() + actorMonthFeeTotal);
                    newBillDetail.setMonthTotalFee(actorMonthFeeTotal);

                    int contractFlag = billDetailMapper.insertBillDetail(newBillDetail);
                    if (contractFlag==1) {
                        ContractInfo contractInfo = contractMapper.selectContractByContractId(association.getContract().getContractId());
                        if (contractInfo!=null) {
                            contractInfo.setGenerateBill(1);
                            contractMapper.updateContract(contractInfo);
                        }
                    }
                }
            }

            billMapper.updateBill(newBill);
        }


            /*// 添加电路检查码“circuitCheckCode”，用以标识该条电路所开过的月租费年月，
            String circuitCheckCodeStart = hireBillCodeType + "("
                    + customer.getCode()+ ")" + startYear;
            String circuitCode = association.getCircuit().getCircuitCode();
            String circuitCheckCode = "";
            for (int i = startMonthI;i <= endMonthI;i++) {
                circuitCheckCode += circuitCheckCodeStart + i + circuitCode + ",";
            }*/

            /*if (association.getCircuit().getCircuitType().getTypeName()
                    .startsWith("境外")
                    && circuitAndContract.getContractInfo().getCustomer()
                    .getInlandoversea() == CustomerCountryArea.Inland) {
                billMonthFeeDetail.setMonthTotalFee(String.valueOf(Math.rint(Double
                        .parseDouble(billMonthFeeDetail.getMonthTotalFee())
                        * exchangeRate)));
            }*/

        return flag;
    }

    @Override
    public int removeBillByIds(String ids) {
        return billMapper.removeBillByIds(Convert.toStrArray(ids));
    }

    @Override
    public Bill findBillByBillId(String billId) {
        return billMapper.findBillByBillId(billId);
    }

    @Override
    public int updateBill(Bill bill) {
        bill.setUpdateBy(ShiroUtils.getLoginName());
        return billMapper.updateBill(bill);
    }


    /**
     *
     * @param srcBegin 电路启付时间
     * @param srcEnd 电路取消时间
     * @param stdBegin 账单起始时间
     * @param stdEnd 账单结束时间
     * @return
     */
    @Override
    public ActualBillDate accountMonthsDays(Date srcBegin, Date srcEnd,
                                          Date stdBegin, Date stdEnd) {

        ActualBillDate actualBillDate = new ActualBillDate();

        /*DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String circuitOpenTime = dateFormat.format(srcBegin);
        if (srcEnd!=null) {
            String circuitCancelTime = dateFormat.format(srcEnd);
        }

        String billDateStart = dateFormat.format(stdBegin);
        String billDateEnd = dateFormat.format(stdEnd);
        //获取电路启付时间所在月份的天数
        String circuitStartDaysOfMonth = DateUtils.getEndDateOfMonth(circuitOpenTime);
        //获取电路取消时间所在月份的天数
        String circuitEndDaysOfMonth = DateUtils.getEndDateOfMonth(circuitCancelTime);
        //获取账单开始时间所在月份的天数
        String billStartDaysOfMonth = DateUtils.getEndDateOfMonth(billDateStart);
        //获取账单结束时间所在月份的天数
        String billEndDaysOfMonth = DateUtils.getEndDateOfMonth(billDateEnd);*/

        int months = 0;
        int startDays = 0;
        int hours = 0;
            //判断电路的取消时间是否为空
            if (srcEnd == null) {
                if (srcBegin.before(stdBegin) || srcBegin.equals(stdBegin)) {
                    //电路启付时间小于等于账单开始时间
                    actualBillDate.setActualBillDateStart(stdBegin);
                    actualBillDate.setActualBillDateEnd(stdEnd);
                    /*//开始月份是否是整月
                    if (StringUtils.split(billDateStart,"-")[2]=="01") {
                        //开始月份为整月
                        actualBillDate.setStartDays(0);
                        //判断结束月份是否为整月
                        if (StringUtils.split(billDateEnd,"-")[2]==billEndDaysOfMonth) {
                            actualBillDate.setEndDays(0);
                            actualBillDate.setWholeMonths(DateUtils.getTwoMonths(stdBegin,stdEnd)+1);
                        }else {
                            //结束月份不为整月
                            actualBillDate.setEndDays(Integer.parseInt(StringUtils.split(billDateEnd,"-")[2]));
                            actualBillDate.setWholeMonths(DateUtils.getTwoMonths(stdBegin,stdEnd));
                        }
                    }else {
                        //开始月份不为整月
                        actualBillDate.setStartDays(Integer.parseInt(StringUtils.split(billDateEnd,"-")[2]));
                        //判断结束月份是否为整月
                        if (StringUtils.split(billDateEnd,"-")[2]==billEndDaysOfMonth) {
                            actualBillDate.setEndDays(0);
                            actualBillDate.setWholeMonths(DateUtils.getTwoMonths(stdBegin,stdEnd));
                        }else {
                            //结束月份不为整月
                            actualBillDate.setEndDays(Integer.parseInt(StringUtils.split(billDateEnd,"-")[2]));
                            actualBillDate.setWholeMonths(DateUtils.getTwoMonths(stdBegin,stdEnd)-1);
                        }

                    }*/

                } else if (srcBegin.after(stdEnd)) {

                } else {
                    //电路启付时间在账单开始时间和结束时间之间


                    actualBillDate.setActualBillDateStart(srcBegin);
                    actualBillDate.setActualBillDateEnd(stdEnd);
                    /*//开始月份是否是整月
                    if (StringUtils.split(circuitOpenTime,"-")[2]=="01") {
                        //开始月份为整月
                        actualBillDate.setStartDays(0);
                        //判断结束月份是否为整月
                        if (StringUtils.split(billDateEnd,"-")[2]==billEndDaysOfMonth) {
                            actualBillDate.setEndDays(0);
                            actualBillDate.setWholeMonths(DateUtils.getTwoMonths(srcBegin,stdEnd)+1);
                        }else {
                            //结束月份不为整月
                            actualBillDate.setEndDays(Integer.parseInt(StringUtils.split(billDateEnd,"-")[2]));
                            actualBillDate.setWholeMonths(DateUtils.getTwoMonths(srcBegin,stdEnd));
                        }
                    }else {
                        //开始月份不为整月
                        actualBillDate.setStartDays(Integer.parseInt(StringUtils.split(billDateEnd,"-")[2]));
                        //判断结束月份是否为整月
                        if (StringUtils.split(billDateEnd,"-")[2]==billEndDaysOfMonth) {
                            actualBillDate.setEndDays(0);
                            actualBillDate.setWholeMonths(DateUtils.getTwoMonths(srcBegin,stdEnd));
                        }else {
                            //结束月份不为整月
                            actualBillDate.setEndDays(Integer.parseInt(StringUtils.split(billDateEnd,"-")[2]));
                            actualBillDate.setWholeMonths(DateUtils.getTwoMonths(srcBegin,stdEnd)-1);
                        }

                    }*/
                }
            } else {
                //电路取消时间不为空
                if (srcEnd.before(stdBegin) || srcEnd.equals(stdBegin) || srcBegin.after(stdEnd)) {
                /**    |____________|                                   |_____________|
                 *         circuit                                          circuit
                 *                   |_____________| or |_____________|
                 *                        bill              bill
                 */

                } else if (srcBegin.before(stdBegin) || srcBegin.equals(stdBegin)) {
                    /**    |__________________              |_____________
                     *         circuit                          circuit
                     *                   |_____________ or  |_____________
                     *                        bill                bill
                     */

                    if (srcEnd.after(stdEnd)) {
                        /**    |________________________|
                         *         circuit
                         *          |______________|
                         *          bill
                         */
                        actualBillDate.setActualBillDateStart(stdBegin);
                        actualBillDate.setActualBillDateEnd(stdEnd);
                    } else {
                        /**|______________________|
                         *         circuit
                         *     |____________________________|
                         *          bill
                         */
                        actualBillDate.setActualBillDateStart(stdBegin);
                        //如果实际账单结束日期为电路取消日期，则日期需减1
                        Calendar ca = Calendar.getInstance();//得到一个Calendar的实例
                        ca.setTime(srcEnd); //获取时间
                        ca.add(Calendar.DATE, -1); //天数减1
                        Date lastMonth = ca.getTime(); //结果
                        actualBillDate.setActualBillDateEnd(lastMonth);
                    }
                } else if (srcBegin.after(stdBegin)) {
                    /**         |__________________
                     *              circuit
                     *     |_______________________
                     *                bill
                     */

                    if (srcEnd.after(stdEnd)) {
                        /**         |______________________|
                         *              circuit
                         *     |_______________________|
                         *                bill
                         */
                        actualBillDate.setActualBillDateStart(srcBegin);
                        actualBillDate.setActualBillDateEnd(stdBegin);
                    } else {
                        /**         |_______________|
                         *              circuit
                         *     |_______________________|
                         *                bill
                         */
                        actualBillDate.setActualBillDateStart(srcBegin);
                        //如果实际账单结束日期为电路取消日期，则日期需减1
                        Calendar ca = Calendar.getInstance();//得到一个Calendar的实例
                        ca.setTime(srcEnd); //获取时间
                        ca.add(Calendar.DATE, -1); //天数减1
                        Date lastMonth = ca.getTime(); //结果
                        actualBillDate.setActualBillDateEnd(lastMonth);
                    }
                }
            }

        return actualBillDate;

    }

    @Override
    public int makeAccessBill(AccessBillDetail accessBillDetail, String billType,
                              String customerId, String accessTypeId,Date billTime) {
        AccessBillDetail newAccessBillDetail = new AccessBillDetail();
        Calendar nowCalendar = Calendar.getInstance();
        // 定义日期格式转换Format
        DateFormat format = new SimpleDateFormat("yyyyMMdd");
        String timeStr = format.format(nowCalendar.getTime());
        //生成账单编号
        String billNumber = "";
        ContractAccessInfo contractAccessInfo = contractAccessInfoService.selectContractAccessInfoById(accessTypeId);
        Customer customer = customerMapper.findCustomerByCustomerId(contractAccessInfo.getCustomer().getCustomerId());
        //找到开帐客户对应的开帐单位信息
        CusOwnerRelation cusOwnerRelation = cusOwnerRelationMapper.selectCusOwnerRelationByCustomerId(customerId);
        String billOwnerId = cusOwnerRelation.getAccessBillOwner().getBillOwnerId();
        if (contractAccessInfo != null) {
            String accessTypeShort;
            String accessType = contractAccessInfo.getContractAccessType();
            switch (accessType) {
                case "CPes":
                case "CTes":
                    accessTypeShort = "C";
                    break;
                case "KUFir":
                case "KUSec":
                case "KUThi":
                case "KUFou":
                    accessTypeShort = "KU";
                    break;
                default:
                    accessTypeShort = "ATM";

            }
            // 拼接账单编号，账单编号由三部分组成，入网代号+付费单位三字代码+入网类型简称_ATM/KU/C_+开账日期，如：DLRW(HFE)_ATM_(20090808)
            // 得到账单编号
            billNumber = accessBillCodeType + "(" + customer.getCode() + ")_" + accessTypeShort + "_(" + timeStr + ")";
        }

        // 检查该账单编号是否已经开过账单，防止重复开账
        List<Bill> accessBillList = billMapper.findBillsByBillNumber(billNumber);
        if (accessBillList.size() > 0) {
            Bill billOld = accessBillList.get(0);
            billOld.setBillFeeTotal(billOld.getBillFeeTotal() + contractAccessInfo.getContractAccessFee());
            billMapper.updateBill(billOld);
            newAccessBillDetail.setBill(billOld);
        }else {
            //生成新账单
            Bill newBill = new Bill();
            newBill.setBillType(billType);
            newBill.setCustomer(customer);
            Billowner billowner = billownerMapper.selectBillownerById(billOwnerId);
            if (billowner != null) {
                newBill.setBillOwner(billowner);
            }
            newBill.setBillNumber(billNumber);
            newBill.setBillTime(billTime);
            newBill.setBillFeeTotal(contractAccessInfo.getContractAccessFee());
            newBill.setIsCancel("0");
            newBill.setIsReceive("0");
            newBill.setCreateBy(ShiroUtils.getLoginName());

            int flag = billMapper.insertBill(newBill);
            if (flag==1) {
                //生成账单成功，将is_generate_access_bill标识设置为1
                contractAccessInfo.setGenerateAccessBill(true);
                contractAccessInfoService.updateContractAccessInfo(contractAccessInfo);
            }
            newAccessBillDetail.setBill(newBill);
        }

        newAccessBillDetail.setContractAccessInfo(contractAccessInfo);
        newAccessBillDetail.setAccessType(contractAccessInfo.getContractAccessType());
        newAccessBillDetail.setBillAccessSum(contractAccessInfo.getContractAccessSum());
        newAccessBillDetail.setBillAccessFee(contractAccessInfo.getContractAccessFee());
        newAccessBillDetail.setCreateBy(ShiroUtils.getLoginName());

        return accessBillDetailMapper.insertAccessBillDetail(newAccessBillDetail);

    }

    @Override
    public List<AccessBillDetail> selectAccessBillDetailByBillId(String billId) {
        return accessBillDetailMapper.selectAccessBillDetailByBillId(billId);
    }
}
