package cn.com.atnc.teleCircuitBill.project.contractmng.contractInfo.controller;

import cn.com.atnc.teleCircuitBill.common.utils.file.FileUploadUtils;
import cn.com.atnc.teleCircuitBill.common.utils.file.FileUtils;
import cn.com.atnc.teleCircuitBill.framework.aspectj.lang.annotation.Log;
import cn.com.atnc.teleCircuitBill.framework.aspectj.lang.constant.BusinessType;
import cn.com.atnc.teleCircuitBill.framework.web.controller.BaseController;
import cn.com.atnc.teleCircuitBill.framework.web.domain.AjaxResult;
import cn.com.atnc.teleCircuitBill.framework.web.page.TableDataInfo;
import cn.com.atnc.teleCircuitBill.project.circuitmng.circuitInfo.domain.Circuit;
import cn.com.atnc.teleCircuitBill.project.circuitmng.circuitInfo.service.CircuitService;
import cn.com.atnc.teleCircuitBill.project.contractmng.contractInfo.domain.Association;
import cn.com.atnc.teleCircuitBill.project.contractmng.contractInfo.domain.ContractAccessInfo;
import cn.com.atnc.teleCircuitBill.project.contractmng.contractInfo.domain.ContractAttachment;
import cn.com.atnc.teleCircuitBill.project.contractmng.contractInfo.domain.ContractInfo;
import cn.com.atnc.teleCircuitBill.project.contractmng.contractInfo.service.AssociationService;
import cn.com.atnc.teleCircuitBill.project.contractmng.contractInfo.service.ContractService;
import cn.com.atnc.teleCircuitBill.project.contractmng.contractInfo.service.IAttachmentService;
import cn.com.atnc.teleCircuitBill.project.contractmng.contractInfo.service.IContractAccessInfoService;
import cn.com.atnc.teleCircuitBill.project.costmng.netaccessmng.atmaccess.domain.AtmAccessFee;
import cn.com.atnc.teleCircuitBill.project.costmng.netaccessmng.atmaccess.service.AtmAccessService;
import cn.com.atnc.teleCircuitBill.project.costmng.netaccessmng.caccess.domain.CAccessFee;
import cn.com.atnc.teleCircuitBill.project.costmng.netaccessmng.caccess.service.CAccessService;
import cn.com.atnc.teleCircuitBill.project.costmng.netaccessmng.kuaccess.domain.KuAccessFee;
import cn.com.atnc.teleCircuitBill.project.costmng.netaccessmng.kuaccess.service.KuAccessService;
import cn.com.atnc.teleCircuitBill.project.customerInfo.customers.domain.Customer;
import cn.com.atnc.teleCircuitBill.project.customerInfo.customers.service.CustomerService;
import cn.com.atnc.teleCircuitBill.project.system.user.controller.ProfileController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;

import static cn.com.atnc.teleCircuitBill.common.utils.file.FileUploadUtils.getDefaultBaseDir;
import static cn.com.atnc.teleCircuitBill.common.utils.file.FileUploadUtils.upload;

/**
 * 合同信息Controller层
 * @author  liwenjie
 */
@Controller
@RequestMapping("contractmng/contractInfo")
public class ContractController extends BaseController {

    private static final String DOCUMENT_PDF_EXTENSSION = ".pdf";

    private static final String DOCUMENT_DOC_EXTENSSION = ".doc";

    private static final String DOCUMENT_DOCX_EXTENSSION = ".docx";

    private static final Logger log = LoggerFactory.getLogger(ProfileController.class);
    private String prefix = "contractmng/contractInfo";

    @Autowired
    private ContractService contractService;
    @Autowired
    private CircuitService circuitService;
    @Autowired
    private AssociationService associationService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private IAttachmentService attachmentService;
    @Autowired
    private IContractAccessInfoService contractAccessInfoService;
    @Autowired
    private CAccessService cAccessService;
    @Autowired
    private KuAccessService kuAccessService;
    @Autowired
    private AtmAccessService atmAccessService;

    /**
     * 获取合同信息-GET
     * @return
     */
    @RequiresPermissions("contractmng:contract:view")
    @GetMapping()
    public String contract() {
        return prefix + "/contract";
    }

    /**
     * 获取合同信息-POST
     * @param contractInfo
     * @return
     */
    @RequiresPermissions("contractmng:contract:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(ContractInfo contractInfo)
    {
        startPage();
        List<ContractInfo> list = contractService.selectContractList(contractInfo);
        //判断当前合同是否在生效日期内
        int flag = 0;
        for (ContractInfo contract: list) {
            if (contract.getContractStopDate()==null) {
                //如果终止日期为空
                flag=0;
            }else {
                Date today = new Date();
                if (today.getTime()>contract.getContractStopDate().getTime()) {
                    //当前日期在终止日期时间范围内
                    flag=1;
                }
            }

            if (flag==1) {
                contract.setStatus("1");
            }else {
                contract.setStatus("0");
            }


        }


        return getDataTable(list);
    }


    /**
     * 合同新增-get
     */
    @GetMapping("/add")
    public String add(ModelMap map) {
        map.put("customers",customerService.selectCustomerList(new Customer()));
        return prefix + "/add";
    }

    /**
     * 合同新增-post
     */
    @RequiresPermissions("contractmng:contract:add")
    @Log(title = "合同管理", action = BusinessType.INSERT)
    @PostMapping("/add")
    @Transactional(rollbackFor = Exception.class)
    @ResponseBody
    public AjaxResult addSave(ContractInfo contractInfo, @RequestParam("customerId") String customerId,
                              @RequestParam("partAId") String partAId,@RequestParam("partBId") String partBId) {
        Customer customer = customerService.findCustomerByCustomerId(customerId);
        contractInfo.setCustomer(customer);
        contractInfo.setPartA(customerService.findCustomerByCustomerId(partAId));
        contractInfo.setPartB(customerService.findCustomerByCustomerId(partBId));
        return toAjax(contractService.insertContract(contractInfo));
    }

    /**
     * 合同修改-GET
     */
    @GetMapping("/edit/{contractId}")
    public String edit(@PathVariable("contractId") String contractId, ModelMap model)
    {
        model.put("contract", contractService.selectContractByContractId(contractId));
        model.put("customers",customerService.selectCustomerList(new Customer()));
        ContractInfo contract  = contractService.selectContractByContractId(contractId);
        model.put("customerIdSelected",contractService.selectContractByContractId(contractId).getCustomer().getCustomerId());
        if (contractService.selectContractByContractId(contractId).getPartA() != null) {
            model.put("partASelected",contractService.selectContractByContractId(contractId).getPartA().getCustomerId());

        }
        if (contractService.selectContractByContractId(contractId).getPartB() != null) {
            model.put("partBSelected",contractService.selectContractByContractId(contractId).getPartB().getCustomerId());
        }
        return prefix + "/edit";
    }

    /**
     * 修改保存合同-POST
     */
    @RequiresPermissions("contractmng:contract:edit")
    @Log(title = "合同管理", action = BusinessType.UPDATE)
    @PostMapping("/edit")
    @Transactional(rollbackFor = Exception.class)
    @ResponseBody
    public AjaxResult editSave(ContractInfo contract,@RequestParam("customerId") String customerId,
                               @RequestParam("partAId")String partAId,@RequestParam("partBId") String partBId) {
        Customer customer = customerService.findCustomerByCustomerId(customerId);
        contract.setCustomer(customer);
        contract.setPartA(customerService.findCustomerByCustomerId(partAId));
        contract.setPartB(customerService.findCustomerByCustomerId(partBId));
        return toAjax(contractService.updateContract(contract));
    }

    /**
     * 合同变更-GET
     */
    @GetMapping("/change/{contractId}")
    public String change(@PathVariable("contractId") String contractId, ModelMap model)
    {
        model.put("contract", contractService.selectContractByContractId(contractId));
        return prefix + "/change";
    }

    /**
     * 变更保存合同-POST
     */
    @RequiresPermissions("contractmng:contract:change")
    @Log(title = "合同变更", action = BusinessType.CHANGE)
    @PostMapping("/change")
    @Transactional(rollbackFor = Exception.class)
    @ResponseBody
    public AjaxResult changeSave(ContractInfo contract,@RequestParam("contractIdOld") String contractIdOld,
            @RequestParam("contractNumberNew") String contractNumberNew) {
        contract.setContractId(contractIdOld);
        return toAjax(contractService.changeContract(contract,contractNumberNew));
    }


    /**
     * 合同删除
     */
    @RequiresPermissions("contractmng:contract:remove")
    @Log(title = "合同管理", action = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(contractService.removeContractByIds(ids));
    }

    /**
     * 获取合同所挂的所有电路信息-GET
     * @param contractId 合同id
     * @param model ModelMap对象
     * @return String
     */
    @GetMapping("/editcircuit/{contractId}")
    public String editCircuit(@PathVariable("contractId") String contractId, ModelMap model) {
        List<String> circuitIds = new ArrayList<>();
        //获取合同所在的电路列表
        List<Association> associationList = associationService.selectAssociationByContractId(contractId);
        Set<Circuit> circuitList = new HashSet<>();
        for (Association association: associationList) {
            if (association.getCircuit()!=null) {
                    circuitList.add(association.getCircuit());
            }
        }
        //获得合同类型
        String contractType = contractService.selectContractByContractId(contractId).getContractType();
        for (Circuit circuit: circuitList) {
            circuitIds.add(circuit.getCircuitId());
        }
        //所有的电路列表
        List<Circuit> circuitAll = circuitService.selectCircuitList(new Circuit());
        //circuitAll.remove(circuitList);

        model.put("circuits",circuitAll);
        //合同以选中的电路列表
        model.put("circuitIds",circuitIds);
        model.put("contractId",contractId);
        model.put("contractType",contractType);
        model.put("divideRatio",contractType);
        return prefix + "/editcircuit";
    }

    /**
     * 终止取消选中的电路-合同关联信息
     * @param circuitIds 多选框选中的电路ids
     * @param contractId 合同id
     * @return AjaxResult
     */
    @RequiresPermissions("contractmng:contract:cancelassociationold")
    @Log(title = "删除取消的合同-电路关系", action = BusinessType.UPDATE)
    @PostMapping("/cancelassociationold")
    @Transactional(rollbackFor = Exception.class)
    @ResponseBody
    public AjaxResult cancelAssociationOld(@RequestParam("circuitIds") String circuitIds,
                                           @RequestParam("contractId") String contractId) {

        try {
            //根据合同id获取电路-合同关联信息
            List<Association> associationList = associationService.selectAssociationByContractId(contractId);
            if (associationList.size()>0) {
                for (Association association: associationList) {
                    //判断该合同id的电路id是否包含在上传的电路id列表中
                    if (association.getCircuit()!=null) {
                        if (circuitIds.indexOf(association.getCircuit().getCircuitId())==-1) {
                            //如果等于-1说明该值不包含在电路id中，需要从从电路-合同挂接信息表中取消这条信息
                            associationService.cancelAssociation(association);
                        }
                    }

                }
            }

            return toAjax(1);
        }catch (Exception e) {
            e.printStackTrace();

            return toAjax(0);
        }

    }

    /**
     * 新增电路-合同关联表-POST
     * @param circuitId 电路id
     * @param contractId 合同id
     * @return AjaxResult
     */
    @RequiresPermissions("contractmng:contract:editcircuit")
    @Log(title = "新增合同-电路关系", action = BusinessType.UPDATE)
    @PostMapping("/editcircuit")
    @Transactional(rollbackFor = Exception.class)
    @ResponseBody
    public AjaxResult createCircuitContractAssociation(@RequestParam("circuitId") String circuitId,
                                    @RequestParam("contractId") String contractId,
                                    @RequestParam("feePercent") String feePercent) {
        int flag = 0;
        //根据合同id查找原有的电路-合同关联信息
        List<Association> associationList = associationService.selectAssociationByContractId(contractId);
        Circuit circuit = circuitService.selectCircuitById(circuitId);
        //获取合同id列表
        List<String> circuitIdList = new ArrayList<>();
        for (Association association: associationList) {
            if (association.getCircuit()!=null) {
                if (association.getCircuit().getCircuitId()!=null) {
                    circuitIdList.add(association.getCircuit().getCircuitId());
                }
            }
        }
        //判断circuitId是否存在
        if (circuitIdList.indexOf(circuitId)!=-1) {
            //原有list中存在
            for (Association association: associationList) {
                if (association.getCircuit().getCircuitId()!=null) {
                    if (association.getCircuit().getCircuitId().equals(circuitId)) {
                        if (feePercent!=association.getFeePercent()) {
                            //feePercent修改
                            //设置费用占比
                            association.setFeePercent(feePercent);
                            if (feePercent.equals("100%")) {
                                //电路费用
                                association.setFeeCir(circuit.getCircuitFeeCir());
                                //端口费用
                                association.setFeePort(circuit.getCircuitFeePort());
                                //总费用
                                association.setFeeSum(circuit.getCircuitFeeCir()+circuit.getCircuitFeePort());
                            }else if (feePercent.equals("70%")) {
                                //电路费用
                                association.setFeeCir(circuit.getCircuitFeeCir()*0.7);
                                //端口费用
                                association.setFeePort(circuit.getCircuitFeePort());
                                //总费用
                                association.setFeeSum(circuit.getCircuitFeeCir()*0.7+circuit.getCircuitFeePort());
                            }else if (feePercent.equals("40%")) {
                                //电路费用
                                association.setFeeCir(circuit.getCircuitFeeCir()*0.4);
                                //端口费用
                                association.setFeePort(circuit.getCircuitFeePort()*0.5);
                                //总费用
                                association.setFeeSum(circuit.getCircuitFeeCir()*0.4+circuit.getCircuitFeePort()*0.5);
                            }else if (feePercent.equals("30%")) {
                                //电路费用
                                association.setFeeCir(circuit.getCircuitFeeCir()*0.3);
                                //端口费用
                                association.setFeePort(circuit.getCircuitFeePort()*0.5);
                                //总费用
                                association.setFeeSum(circuit.getCircuitFeeCir()*0.3+circuit.getCircuitFeePort()*0.5);
                            }

                            flag+=associationService.updateAssociation(association);

                        }
                    }
                }
            }


        }else {
           //原有list中不存在
            flag+=associationService.createAssociation(circuitId,contractId,feePercent);
        }

        return toAjax(flag);

    }

    /**
     * 在弹出框中显示合同已经挂中的电路列表
     * @param contractId
     * @return
     */
    @PostMapping("/findselectedcircuit")
    @ResponseBody
    public  TableDataInfo findSelectedCircuit(@RequestParam("contractId") String contractId) {
        startPage();
        List<Circuit> circuitList = new ArrayList<>();
        List<Association> associationList = associationService.selectAssociationByContractId(contractId);
        if (associationList!=null) {
            for (Association association: associationList) {
                Circuit circuit = association.getCircuit();
                circuit.setFeePercent(association.getFeePercent());
                circuitList.add(circuit);
            }
        }
        return getDataTable(circuitList);
    }

    /**
     * 在弹出框中显示多选框选中的电路
     * @param circuitIds
     * @return
     */
    @PostMapping("/findselectedcircuitchecked")
    @ResponseBody
    public  TableDataInfo findSelectedCircuitNew(@RequestParam("circuitIds") String circuitIds,
                                                 @RequestParam("contractId") String contractId) {
        //拆分多选框选择的ids
        String[] circuitIdArray = circuitIds.split(",");
        //开启分页
        startPage();
        //根据合同id 查找这条合同的合同-电路关联信息
        List<Association> associationList = associationService.selectAssociationByContractId(contractId);
        List<Circuit> circuitList = new ArrayList<>();
        for (int i=0;i<circuitIdArray.length;i++) {
            if (circuitService.selectCircuitById(circuitIdArray[i])!=null) {
                circuitList.add(circuitService.selectCircuitById(circuitIdArray[i]));
            }
        }

        //遍历电路列表，添加原有电路已有的费用占比
        circuitList.stream().forEach(circuit ->
            associationList.stream().forEach(association -> {
                if (circuit.getCircuitId().equals(association.getCircuit().getCircuitId()))
                    circuit.setFeePercent(association.getFeePercent());
            })
         );

        return getDataTable(circuitList);
    }

    /**
     * 上传文件-GET
     * @param contractId
     * @param model
     * @return
     */
    @GetMapping("/upload/{id}")
    public String uploadFiles(@PathVariable("id") String contractId, ModelMap model)
    {
        model.put("contractId", contractId);
        return prefix + "/upload";
    }

    /**
     * 上传附件-POST
     * @param request
     * @param contractId 合同id
     * @return
     */
    @RequiresPermissions("contractmng:contract:upload")
    @Log(title = "上传附件", action = BusinessType.IMPORT)
    @PostMapping("/uploadfile")
    @ResponseBody
    public Map<String,String> uploadWord(HttpServletRequest request,
                                         @RequestParam("contractId") String contractId) throws Exception{
//           String pathName = FileUploadUtils.upload(file);
        MultipartRequest fileRequest = (MultipartRequest) request;
        Map<String,String> attachmentMap = new LinkedHashMap<>();
        String returnName = null;
        MultipartFile file = null;
        if (fileRequest.getFile("elec_document")!=null) {
            file = fileRequest.getFile("elec_document");
        }else if (fileRequest.getFile("scan_document")!=null) {
            file = fileRequest.getFile("scan_document");
        }
        String name = file.getOriginalFilename();
        if (name.endsWith(".pdf")) {
            returnName = upload(getDefaultBaseDir(),file,DOCUMENT_PDF_EXTENSSION);
            attachmentMap.put("scanDocumentPath",returnName);
            return attachmentMap;
        }else if (name.endsWith(".doc")) {
            returnName = upload(getDefaultBaseDir(),file,DOCUMENT_DOC_EXTENSSION);
            attachmentMap.put("elecDocumentPath",returnName);
            return attachmentMap;
        }else {
            returnName = upload(getDefaultBaseDir(),file,DOCUMENT_DOC_EXTENSSION);
            attachmentMap.put("elecDocumentPath",returnName);
            return attachmentMap;
        }

    }

    /**
     * 显示附件列表
     * @param contractId
     * @return
     */
    @PostMapping("/findContractAttachment")
    @ResponseBody
    public  TableDataInfo findAttachmentList(@RequestParam("contractId") String contractId) {
        startPage();
        List<ContractAttachment> attachmentList = attachmentService.findAttachmentByContractId(contractId);
        return getDataTable(attachmentList);
    }

    /**
     * 新增合同附件信息
     * @param contractId
     * @param attachmentType
     * @param elecDocumentName
     * @param elecDocumentPath
     * @param scanDocumentName
     * @param scanDocumentPath
     * @return
     */
    @PostMapping("/addAttachment")
    @Log(title = "合同附件信息", action = BusinessType.INSERT)
    @ResponseBody
    public AjaxResult addContractAttachment(@RequestParam("contractId") String contractId,
                                        @RequestParam("attachmentType") String attachmentType,
                                        @RequestParam("elecDocumentName") String elecDocumentName,
                                        @RequestParam("elecDocumentPath") String elecDocumentPath,
                                        @RequestParam("scanDocumentName") String scanDocumentName,
                                        @RequestParam("scanDocumentPath") String scanDocumentPath) {
        ContractAttachment newAttachment = new ContractAttachment();
        ContractInfo contractInfo = contractService.selectContractByContractId(contractId);
        newAttachment.setContractInfo(contractInfo);
        newAttachment.setAttachmentType(attachmentType);
        newAttachment.setElecDocumentName(elecDocumentName);
        newAttachment.setElecDocumentPath(elecDocumentPath);
        newAttachment.setScanDocumentName(scanDocumentName);
        newAttachment.setScanDocumentPath(scanDocumentPath);

        return toAjax(attachmentService.insertContractAttachment(newAttachment));
    }

    /**
     * 附件信息修改-GET
     */
    @GetMapping("/editattach/{attachmentId}")
    public String editAttaches(@PathVariable("attachmentId") String attachmentId, ModelMap model)
    {
        ContractAttachment attachment = attachmentService.findAttachmentById(attachmentId);
        System.out.println(attachment.getAttachmentType());
        model.put("attachment",attachment);
        model.put("attachmentType",attachment.getAttachmentType());
        model.put("elecSelected",attachment.getElecDocumentName());
        model.put("scanSelected",attachment.getScanDocumentName());

        return prefix + "/editattach";
    }

    @PostMapping("/editAttachment")
    @Log(title = "合同附件信息", action = BusinessType.UPDATE)
    @ResponseBody
    public AjaxResult editContractAttachment(@RequestParam("attachmentId") String attachmentId,
                                            @RequestParam("attachmentType") String attachmentType,
                                            @RequestParam("elecDocumentName") String elecDocumentName,
                                            @RequestParam("elecDocumentPath") String elecDocumentPath,
                                            @RequestParam("scanDocumentName") String scanDocumentName,
                                            @RequestParam("scanDocumentPath") String scanDocumentPath) {
        ContractAttachment attachment = attachmentService.findAttachmentById(attachmentId);
        attachment.setAttachmentType(attachmentType);
        attachment.setElecDocumentName(elecDocumentName);
        attachment.setElecDocumentPath(elecDocumentPath);
        attachment.setScanDocumentName(scanDocumentName);
        attachment.setScanDocumentPath(scanDocumentPath);

        return toAjax(attachmentService.updateAttachment(attachment));
    }

    @Log(title = "附件信息", action = BusinessType.DELETE)
    @PostMapping("/removeattachs")
    @ResponseBody
    public AjaxResult delAttachments(String ids)
    {
        return toAjax(attachmentService.removeContractAttachmentById(ids));
    }

    /**
     * 合同入网信息-GET
     * @param contractId
     * @param model
     * @return
     */
    @GetMapping("/contractaccess/{id}")
    public String contractAccess(@PathVariable("id") String contractId, ModelMap model)
    {
        model.put("contractId",contractId);
        return prefix + "/accessInfo";
    }

    /**
     * 新增合同-入网信息-POST
     * @param contractId
     * @return
     */
    @PostMapping("/contractaccess")
    @Log(title = "合同-入网信息", action = BusinessType.INSERT)
    @Transactional(rollbackFor = Exception.class)
    @ResponseBody
    public AjaxResult addContractAccessInfo(ContractAccessInfo contractAccessInfo, @RequestParam("contractId") String contractId) {
        ContractInfo contract = contractService.selectContractByContractId(contractId);
        contractAccessInfo.setContractInfo(contract);
        contractAccessInfo.setCustomer(contract.getCustomer());
        return toAjax(contractAccessInfoService.insertContractAccessInfo(contractAccessInfo));
    }


    /**
     * 显示合同入网信息列表
     * @param contractId 合同Id
     * @return
     */
    @PostMapping("/contractaccessinfo")
    @ResponseBody
    public  TableDataInfo findContractAccessInfo(@RequestParam("contractId") String contractId) {
        startPage();
        List<ContractAccessInfo> contractAccessList = contractAccessInfoService.selectContractAccessInfoByContractId(contractId);
        return getDataTable(contractAccessList);
    }

    /**
     * 根据入网合同类型查找入网资费
     * @param contractAccessType 入网合同类型
     * @param contractId 合同Id
     * @return double
     */
    @PostMapping("/findaccessfee")
    @ResponseBody
    public double findAccessFee(@RequestParam("contractAccessType") String contractAccessType,
                                       @RequestParam("contractId") String contractId) {
        Customer customer = contractService.selectContractByContractId(contractId).getCustomer();
        //客户的区域和资费的站点位置value值相同，可直接对应
        String area = customer.getRegion();
        double contractAccessFee = 0;
        if (contractAccessType.equals("CTes")) {
            CAccessFee cAccessFee = new CAccessFee();
            cAccessFee.setSiteArea(area);
            cAccessFee.setSiteType("0");
            contractAccessFee =   cAccessService.selectCAccessFeeList(cAccessFee).get(0).getSiteAccessAmount();
        }else if (contractAccessType.equals("CPes")) {
            CAccessFee cAccessFee = new CAccessFee();
            cAccessFee.setSiteArea(area);
            cAccessFee.setSiteType("1");
            contractAccessFee =   cAccessService.selectCAccessFeeList(cAccessFee).get(0).getSiteAccessAmount();
        }else if (contractAccessType.equals("KUFir")) {
            KuAccessFee kuAccessFee = new KuAccessFee();
            kuAccessFee.setSiteArea(area);
            kuAccessFee.setSiteLevel("0");
            contractAccessFee =   kuAccessService.selectKuAccessFeeList(kuAccessFee).get(0).getSiteAccessAmount();
        }else if (contractAccessType.equals("KUSec")) {
            KuAccessFee kuAccessFee = new KuAccessFee();
            kuAccessFee.setSiteArea(area);
            kuAccessFee.setSiteLevel("1");
            contractAccessFee =   kuAccessService.selectKuAccessFeeList(kuAccessFee).get(0).getSiteAccessAmount();
        }else if (contractAccessType.equals("KUThi")) {
            KuAccessFee kuAccessFee = new KuAccessFee();
            kuAccessFee.setSiteArea(area);
            kuAccessFee.setSiteLevel("2");
            contractAccessFee =   kuAccessService.selectKuAccessFeeList(kuAccessFee).get(0).getSiteAccessAmount();
        }else if (contractAccessType.equals("KUFou")) {
            KuAccessFee kuAccessFee = new KuAccessFee();
            kuAccessFee.setSiteArea(area);
            kuAccessFee.setSiteLevel("3");
            contractAccessFee =   kuAccessService.selectKuAccessFeeList(kuAccessFee).get(0).getSiteAccessAmount();
        }else if (contractAccessType.equals("ATMWithEquip")) {
            AtmAccessFee atmAccessFee = new AtmAccessFee();
            atmAccessFee.setSiteBuyEquipment("1");
            contractAccessFee =   atmAccessService.selectAtmAccessFeeList(atmAccessFee).get(0).getSiteAccessAmount();
        }else if (contractAccessType.equals("ATMWithoutEquip")) {
            AtmAccessFee atmAccessFee = new AtmAccessFee();
            atmAccessFee.setSiteBuyEquipment("0");
            contractAccessFee =   atmAccessService.selectAtmAccessFeeList(atmAccessFee).get(0).getSiteAccessAmount();
        }
        return contractAccessFee;
    }

    /**
     * 删除合同-入网信息（逻辑删除）
     * @param ids 合同Id
     * @return AjaxResult
     */
    @RequiresPermissions("contractmng:contract:cancelAccessInfo")
    @Log(title = "合同-入网信息", action = BusinessType.DELETE)
    @PostMapping("/removeAccessInfo")
    @ResponseBody
    public AjaxResult removeAccessInfo(String ids)
    {
        return toAjax(contractAccessInfoService.deleteContractAccessInfoById(ids));
    }

    /**
     * 获取最新的合同信息
     * @return String
     */
    @PostMapping("/latestContractInfos")
    @ResponseBody
    public String getLatestContractInfos()
    {
        return contractService.getLatestContractInfos();
    }

    /**
     * 检验合同编号是否唯一(修改)
     * @param contractInfo 合同信息
     * @return String
     */
    @PostMapping("/checkContractNumberUnique")
    @ResponseBody
    public String checkContractNumberUnique(ContractInfo contractInfo) {
        return contractService.checkContractNumberUnique(contractInfo.getContractNumber(),contractInfo.getContractId());
    }

    /**
     * 检验合同编号是否唯一(变更)
     * @param contractInfo 合同信息
     * @return String
     */
    @PostMapping("/checkContractNumberUniqueChange")
    @ResponseBody
    public String checkContractNumberUniqueChange(ContractInfo contractInfo) {
        return contractService.checkContractNumberUniqueChange(contractInfo.getContractNumber());
    }

    /**
     * 下载附件
     * @param path 附件的存储路径
     * @param type 附件类型
     * @param response 响应体
     */
    @GetMapping("/download")
    @ResponseBody
    public void downloadFile(@RequestParam("path") String path,@RequestParam("type") String type,
                             HttpServletResponse response) throws UnsupportedEncodingException {
        String fileName = "";
        if (type.equals("elecFile")) {
            //电子文档
            ContractAttachment attachment = attachmentService.findAttachmentByElecDocumentPath(path);
            if (attachment != null) {
                fileName += attachment.getElecDocumentName();
            }
        } else if (type.equals("scanFile")) {
            //扫描文档
            ContractAttachment attachment = attachmentService.findAttachmentByScanDocumentPath(path);
            if (attachment != null) {
                fileName += attachment.getScanDocumentName();
            }
        }

        System.err.println(fileName);
        System.err.println(path);

        //真实路径
        String realPath = FileUploadUtils.getDefaultBaseDir() + path;
        System.err.println(realPath);
        File file = new File(realPath);

        response.reset();
        response.setContentType("application/octet-stream");
        response.setCharacterEncoding("utf-8");
        response.setContentLength((int) file.length());
        /*response.setHeader("content-type", "text/plain");
        response.setHeader("content-type", "application/x-msdownload;");
        response.setContentType("text/plain; charset=utf-8");*/
        response.setHeader("Content-Disposition", "attachment;filename=" + java.net.URLEncoder.encode(fileName,"UTF-8"));
        byte[] buff = new byte[1024];
        BufferedInputStream bis = null;
        OutputStream os = null;
        try {
            os = response.getOutputStream();
            bis = new BufferedInputStream(new FileInputStream(file));
            int i = bis.read(buff);
            while (i != -1) {
                os.write(buff, 0, i);
                os.flush();
                i = bis.read(buff);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }



}
