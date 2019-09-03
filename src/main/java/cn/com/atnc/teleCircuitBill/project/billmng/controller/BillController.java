package cn.com.atnc.teleCircuitBill.project.billmng.controller;

import cn.com.atnc.teleCircuitBill.framework.aspectj.lang.annotation.Log;
import cn.com.atnc.teleCircuitBill.framework.aspectj.lang.constant.BusinessType;
import cn.com.atnc.teleCircuitBill.framework.web.controller.BaseController;
import cn.com.atnc.teleCircuitBill.framework.web.domain.AjaxResult;
import cn.com.atnc.teleCircuitBill.framework.web.page.TableDataInfo;
import cn.com.atnc.teleCircuitBill.project.billmng.domain.AccessBillDetail;
import cn.com.atnc.teleCircuitBill.project.billmng.domain.Bill;
import cn.com.atnc.teleCircuitBill.project.billmng.domain.BillDetail;
import cn.com.atnc.teleCircuitBill.project.billmng.service.BillService;
import cn.com.atnc.teleCircuitBill.project.circuitmng.circuitInfo.domain.Circuit;
import cn.com.atnc.teleCircuitBill.project.contractmng.contractInfo.domain.Association;
import cn.com.atnc.teleCircuitBill.project.contractmng.contractInfo.domain.ContractAccessInfo;
import cn.com.atnc.teleCircuitBill.project.contractmng.contractInfo.domain.ContractInfo;
import cn.com.atnc.teleCircuitBill.project.contractmng.contractInfo.service.AssociationService;
import cn.com.atnc.teleCircuitBill.project.contractmng.contractInfo.service.ContractService;
import cn.com.atnc.teleCircuitBill.project.contractmng.contractInfo.service.IContractAccessInfoService;
import cn.com.atnc.teleCircuitBill.project.customerInfo.customers.domain.Customer;
import cn.com.atnc.teleCircuitBill.project.customerInfo.customers.service.CustomerService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.*;

/**
 * 账单信息Controller类
 * @author liwenjie
 * @date 2018-11-13
 *
 */
@Controller
@RequestMapping("billmng")
public class BillController extends BaseController {
    private String prefix = "billmng/billInfo";
    @Value("${fineReport.url}")
    private String reportUrl;
    @Autowired
    private BillService billService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private ContractService contractService;
    @Autowired
    private AssociationService associationService;
    @Autowired
    private IContractAccessInfoService contractAccessInfoService;

    /**
     * 获取账单信息-GET
     * @return
     */
    @RequiresPermissions("billmng:bill:view")
    @GetMapping()
    public String circuit() {
        return prefix + "/bill";
    }

    /**
     * 获取账单信息-POST
     * @param bill
     * @return
     */
    @RequiresPermissions("billmng:bill:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(Bill bill)
    {
        startPage();
        List<Bill> list = billService.selectBillList(bill);
        return getDataTable(list);
    }

    /**
     * 新增账单-get
     */
    @GetMapping("/add")
    public String add(ModelMap model) {
        model.put("customers",customerService.selectCustomerList(new Customer()));
        return prefix + "/add";
    }

    /**
     * 新增租用/分成账单-post
     * @param billDetail 账单详细信息
     * @param billType 账单类型
     * @param customerId 客户id
     * @param associationIds 合同电路关联表Id
     * @return AjaxResult
     * @throws ParseException
     */
    @RequiresPermissions("billmng:bill:add")
    @Log(title = "新增租用/分成账单", action = BusinessType.INSERT)
    @PostMapping("/add")
    @Transactional(rollbackFor = Exception.class)
    @ResponseBody
    public AjaxResult addSave(BillDetail billDetail, @RequestParam("billType") String billType,
                              @RequestParam("customerId") String customerId,
                              @RequestParam("associationIds") String associationIds,
                              @RequestParam("billTime") Date billTime) throws ParseException {
        return toAjax(billService.makeBill(billDetail,billType,customerId,associationIds,billTime));
    }

    /**
     * 新增入网账单-post
     * @param accessBillDetail 入网账单详细信息
     * @param billType 账单类型
     * @param customerId 客户Id
     * @param accessTypeId 入网账单类型
     * @return AjaxResult
     * @throws ParseException
     */
    @Log(title = "新增入网账单", action = BusinessType.INSERT)
    @PostMapping("/addacessbill")
    @Transactional(rollbackFor = Exception.class)
    @ResponseBody
    public AjaxResult addAccessBill(AccessBillDetail accessBillDetail, @RequestParam("billType") String billType,
                                    @RequestParam("customerId") String customerId,
                                    @RequestParam("accessTypeId") String accessTypeId,
                                    @RequestParam("billTime") Date billTime) throws ParseException {
        return toAjax(billService.makeAccessBill(accessBillDetail,billType,customerId,accessTypeId,billTime));
    }

    /**
     * 账单修改-GET
     * @param billId 账单ID
     * @param model
     * @return
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") String billId, ModelMap model)
    {
        model.put("bill", billService.findBillByBillId(billId));
        model.put("customers",customerService.selectCustomerList(new Customer()));
        model.put("customerIdSelected",billService.findBillByBillId(billId).getCustomer().getCustomerId());
        return prefix + "/edit";
    }

    /**
     * 电路修改-POST
     */
    @RequiresPermissions("circuitmng:circuit:edit")
    @Log(title = "账单管理", action = BusinessType.UPDATE)
    @PostMapping("/edit")
    @Transactional(rollbackFor = Exception.class)
    @ResponseBody
    public AjaxResult editSave(Bill bill,@RequestParam("customerId") String customerId) {
        Customer customer = customerService.findCustomerByCustomerId(customerId);
        bill.setCustomer(customer);
        return toAjax(billService.updateBill(bill));
    }


    /**
     * 根据选取的账单类型查找客户列表
     * @param billType
     * @param model
     * @return
     */
    @GetMapping("/findCustomerList")
    @ResponseBody
    public List<Customer> findCustomerList(@RequestParam("billType") String billType,ModelMap model) {
        String contractType = null;
        switch(billType) {
            case "HireBill":
                //如果是租用账单：
                contractType = "Hire";
                break;
            case "DevideBill":
                //如果是分成账单
                contractType = "Maintain";
                break;
            case "AccessBill":
                //如果是入网账单
                contractType = "Access";
                break;
        }
        List<ContractInfo> contracts = contractService.selectContractByContractType(contractType);
        List<String> customerIds = new ArrayList<>();
        //获取下拉列表的customerId的值
        for (ContractInfo contract: contracts) {
            customerIds.add(contract.getCustomer().getCustomerId());
        }
        //获取下拉列表的客户值
        List<Customer> customerList = new ArrayList<>();

        for (String customerId: customerIds) {
            //List<Customer> customerList1 = customerService.findCustomerByCustomerId(customerId);
            customerList.add(customerService.findCustomerByCustomerId(customerId));
        }
        List<Customer> result = new ArrayList<>();
        for (Customer customer : customerList) {
            boolean b = result.stream().anyMatch(u -> u.getCustomerId().equals(customer.getCustomerId()));
            if (!b) {
                result.add(customer);
            }
        }
        model.put("customerList",result);

        return result;
    }

    /**
     * 根据客户id找到挂的电路
     * @param customerId
     */
    @GetMapping("/findcircuitlist")
    @ResponseBody
    public List<Association> findAssociationList(@RequestParam("billType") String billType,
                                                 @RequestParam("customerId") String customerId) {

        //根据客户id和所选的账单类型 查找电路-合同关联信息
        List<Association> associationList = associationService.selectAssociationByConditions(billType,customerId);
        //去重操作

        /*List<Association> result = new ArrayList<>();
        for (Association association : associationList) {
            boolean b = result.stream().anyMatch(u -> u.getAssociationId().equals(association.getAssociationId()));
            if (!b) {
                //除去已经开过技术服务账单的合同
                if (billType.equals("HireBill")) {
                    ContractInfo contractInfo = contractService.selectContractByContractId(association.getContract().getContractId());
                    if (contractInfo!=null) {
                        if (contractInfo.getGenerateBill()==0) {
                            result.add(association);
                        }
                    }
                }else {
                    result.add(association);
                }

            }
        }*/

        return associationList;
    }

    /**
     * 根据客户id找到未开帐的合同入网信息列表
     * @param customerId
     * @return
     */
    @PostMapping("/findcontractaccesslist")
    @ResponseBody
    public List<ContractAccessInfo> findContractAccessList(@RequestParam("customerId") String customerId) {
        List<ContractAccessInfo> contractAccessInfoList = contractAccessInfoService.
                                                            selectContractAccessInfoByCustomerId(customerId);
        if (contractAccessInfoList!=null) {
            for (ContractAccessInfo contractAccessInfo:contractAccessInfoList) {
                if (contractAccessInfo.getContractAccessType()!=null) {
                    if (contractAccessInfo.getContractAccessType().equals("CTes")) {
                        contractAccessInfo.setContractAccessTypeName("C波段Tes远端站");
                    }else if (contractAccessInfo.getContractAccessType().equals("CPes")) {
                        contractAccessInfo.setContractAccessTypeName("C波段Pes远端站");
                    }else if (contractAccessInfo.getContractAccessType().equals("KUFir")) {
                        contractAccessInfo.setContractAccessTypeName("KU波段一级节点");
                    }else if (contractAccessInfo.getContractAccessType().equals("KUSec")) {
                        contractAccessInfo.setContractAccessTypeName("KU波段二级节点");
                    }else if (contractAccessInfo.getContractAccessType().equals("KUThi")) {
                        contractAccessInfo.setContractAccessTypeName("KU波段三级节点");
                    }else if (contractAccessInfo.getContractAccessType().equals("KUFou")) {
                        contractAccessInfo.setContractAccessTypeName("KU波段四级节点");
                    }else if (contractAccessInfo.getContractAccessType().equals("ATMWithEquip")) {
                        contractAccessInfo.setContractAccessTypeName("ATM节点采购设备");
                    }else if (contractAccessInfo.getContractAccessType().equals("ATMWithoutEquip")) {
                        contractAccessInfo.setContractAccessTypeName("ATM节点不采购设备");
                    }
                }
            }
        }
        return contractAccessInfoList;
    }

    /**
     * 删除账单（逻辑删除）
     * @param ids id列表
     * @return
     */
    @RequiresPermissions("billmng:bill:remove")
    @Log(title = "账单管理", action = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(billService.removeBillByIds(ids));
    }


    /**
     * 查看报表
     * @param billId
     * @param billType
     * @return
     */
    @GetMapping("/previewbill")
    @ResponseBody
    public String previewBill(@RequestParam("billId") String billId,@RequestParam("billType") String billType,ModelMap map) {
        String url=reportUrl;
        Bill bill = billService.findBillByBillId(billId);

        if (bill.getBillType().equals("AccessBill")) {
            //入网账单
            List<AccessBillDetail> accessBillDetails = billService.selectAccessBillDetailByBillId(billId);
            if (accessBillDetails!=null) {
                AccessBillDetail accessBillDetail = accessBillDetails.get(0);
                if (accessBillDetail.getAccessType().equals("CTes")) {
                    url+="access-tes.cpt&billId="+bill.getBillId();
                }else if (accessBillDetail.getAccessType().equals("CPes")) {
                    url+="access-pes.cpt&billId="+bill.getBillId();
                }else if (accessBillDetail.getAccessType().equals("KUFir")) {
                    url+="access-ku1.cpt&billId="+bill.getBillId();
                }else if (accessBillDetail.getAccessType().equals("KUSec")) {
                    url+="access-ku2.cpt&billId="+bill.getBillId();
                }else if (accessBillDetail.getAccessType().equals("KUThi")) {
                    url+="access-ku3.cpt&billId="+bill.getBillId();
                }else if (accessBillDetail.getAccessType().equals("KUFou")) {
                    url+="access-ku4.cpt&billId="+bill.getBillId();
                }else if (accessBillDetail.getAccessType().equals("ATMWithEquip")) {
                    url+="access-atm.cpt&billId="+bill.getBillId();
                }else if (accessBillDetail.getAccessType().equals("ATMWithoutEquip")) {
                    url+="access-atm.cpt&billId="+bill.getBillId();
                }
            }
        }else if (bill.getBillType().equals("DevideBill")) {
            //分成结算表
            if (billType.equals("divideBill")) {
                //分成账单
                url+="bill-fc.cpt&billId="+bill.getBillId();
            }else if (billType.equals("settlementBill")) {
                //结算账单
                url+="bill-js.cpt&billId="+bill.getBillId();
            }

        }else if (bill.getBillType().equals("HireBill")){
            //技术服务账单
            Customer customer = customerService.findCustomerByCustomerId(bill.getCustomer().getCustomerId());
            if (customer!=null) {
                if (customer.getRegion().equals("0")) {
                    //境内
                    url+="bill-inland.cpt&billId="+bill.getBillId();

                }else if (customer.getRegion().equals("1")) {
                    //境外
                    url+="bill-hk.cpt&billId="+bill.getBillId();

                }else if (customer.getRegion().equals("2")) {
                    //港澳台
                    url+="bill-oversea.cpt&billId="+bill.getBillId();
                }
            }
        }
        //map.put("url",url);
        //return prefix + "/finereport";
        System.out.println(url);
        return url;
    }
}
