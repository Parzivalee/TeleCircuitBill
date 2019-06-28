package cn.com.atnc.teleCircuitBill.project.circuitmng.circuitInfo.controller;

import cn.com.atnc.teleCircuitBill.common.utils.poi.ExcelUtil;
import cn.com.atnc.teleCircuitBill.framework.aspectj.lang.annotation.Log;
import cn.com.atnc.teleCircuitBill.framework.aspectj.lang.constant.BusinessType;
import cn.com.atnc.teleCircuitBill.framework.web.controller.BaseController;
import cn.com.atnc.teleCircuitBill.framework.web.domain.AjaxResult;
import cn.com.atnc.teleCircuitBill.framework.web.page.TableDataInfo;
import cn.com.atnc.teleCircuitBill.project.circuitmng.circuitInfo.domain.Circuit;
import cn.com.atnc.teleCircuitBill.project.circuitmng.circuitInfo.service.CircuitService;
import cn.com.atnc.teleCircuitBill.project.contractmng.contractInfo.domain.Association;
import cn.com.atnc.teleCircuitBill.project.contractmng.contractInfo.domain.ContractInfo;
import cn.com.atnc.teleCircuitBill.project.contractmng.contractInfo.service.AssociationService;
import cn.com.atnc.teleCircuitBill.project.contractmng.contractInfo.service.ContractService;
import cn.com.atnc.teleCircuitBill.project.costmng.circuitfeemng.domestic.domain.DomesticFee;
import cn.com.atnc.teleCircuitBill.project.costmng.circuitfeemng.domestic.service.DomesticFeeService;
import cn.com.atnc.teleCircuitBill.project.costmng.circuitfeemng.oversea.domain.OverseaFee;
import cn.com.atnc.teleCircuitBill.project.costmng.circuitfeemng.oversea.service.OverseaFeeService;
import cn.com.atnc.teleCircuitBill.project.customerInfo.customers.domain.Customer;
import cn.com.atnc.teleCircuitBill.project.customerInfo.customers.service.CustomerService;
import com.alibaba.fastjson.JSON;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * 电路详情控制层
 * @author liwenjie
 */
@Controller
@RequestMapping("circuitmng/circuitInfo")
public class CircuitController extends BaseController {
    private static final String CIRCUIT = "circuit";
    private static final String ADD = "新增";
    private static final String EDIT = "变更";
    private static final String REMOVE = "取消";
    private String prefix = "circuitmng/circuitInfo";

    @Autowired
    private CircuitService circuitService;
    @Autowired
    private AssociationService associationService;
    @Autowired
    private ContractService contractService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private DomesticFeeService domesticFeeService;
    @Autowired
    private OverseaFeeService overseaFeeService;

    /**
     * 获取电路信息-GET
     * @return String
     */
    @RequiresPermissions("circuitmng:circuit:view")
    @GetMapping()
    public String circuit() {
        return prefix + "/circuit";
    }

    /**
     * 获取电路信息-POST
     * @param circuit 电路实体
     * @return TableDateInfo
     */
    @RequiresPermissions("circuitmng:circuit:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(Circuit circuit)
    {
        startPage();
        List<Circuit> circuitList = circuitService.selectCircuitList(circuit);
        //遍历所有的电路信息
        for (Circuit circuit1: circuitList) {
            List<String> hireContractNumbers = new ArrayList<>();
            List<String> maintainContractNumbers = new ArrayList<>();
            //找到所有关联的电路-合同关联信息
            List<Association> associationList = associationService.findAssociationsByCircuitId(circuit1.getCircuitId());
            for (Association association1: associationList) {
                ContractInfo contract = null;
                if (contractService.selectContractByContractId(association1.getContract().getContractId()) != null) {
                    contract = contractService.selectContractByContractId(association1.getContract().getContractId());
                }

                if (contract != null) {
                    if (contract.getContractType().equals("Hire")) {
                        hireContractNumbers.add(contract.getContractNumber());
                    }else if (contract.getContractType().equals("Maintain")) {
                        maintainContractNumbers.add(contract.getContractNumber());
                    }
                }
            }

            circuit1.setHireContractNumber(hireContractNumbers);
            circuit1.setMaintainContractNumber(maintainContractNumbers);
        }


        return getDataTable(circuitList);
    }


    /**
     * 新增电路信息-get
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增电路信息-post
     */
    @RequiresPermissions("circuitmng:circuit:add")
    @Log(title = "电路信息管理", action = BusinessType.INSERT)
    @PostMapping("/add")
    @Transactional(rollbackFor = Exception.class)
    @ResponseBody
    public AjaxResult addSave(Circuit circuit) {
        return toAjax(circuitService.insertCircuit(circuit));


    }

    /**
     * 根据电路类型查找速率列表
     * @param circuitType
     * @return
     */
    @PostMapping("/findspeedlist")
    @ResponseBody
    public List<?> findSpeedList(@RequestParam("circuitType") String circuitType) {
        List<?> list;
        if (circuitType.startsWith("境外")) {
            OverseaFee overseaFee = new OverseaFee();
            overseaFee.setCircuitType(circuitType);
            list = overseaFeeService.selectOverseaFeeList(overseaFee);
        }else {
            DomesticFee domesticFee = new DomesticFee();
            domesticFee.setCircuitType(circuitType);
            list = domesticFeeService.selectDomesticFeeList(domesticFee);
        }

        return list;
    }

    /**
     * 查找资费
     * @param circuitType
     * @param circuitId
     * @return
     */
    @PostMapping("/findcircuitfee")
    @ResponseBody
    public Circuit findCircuitFee(@RequestParam("circuitType") String circuitType,
                                  @RequestParam("circuitId") String circuitId) {
        Circuit circuit = new Circuit();
        if (circuitType.startsWith("境外")) {
           OverseaFee overseaFee  = overseaFeeService.selectOverseaFeeById(circuitId);
           circuit.setCircuitFee(overseaFee.getPrice());
           circuit.setCircuitFeeCir(overseaFee.getPrice());
           circuit.setCircuitFeePort(0.0);
        }else {
            DomesticFee domesticFee = domesticFeeService.selectDomesticFeeById(circuitId);
            circuit.setCircuitFee(domesticFee.getVcMonthFee()+domesticFee.getPortFee());
            circuit.setCircuitFeeCir(domesticFee.getVcMonthFee());
            circuit.setCircuitFeePort(domesticFee.getPortFee());
        }

        return circuit;
    }

    /**
     * 电路修改-GET
     */
    @GetMapping("/edit/{circuitId}")
    public String edit(@PathVariable("circuitId") String circuitId, ModelMap model)
    {
        Circuit circuit = circuitService.selectCircuitById(circuitId);
        String circuitType = circuit.getCircuitType();
        List<?> list = new ArrayList<>();
        //获取编辑模态框的速率下拉列表
        if (circuitType!=null) {
            if (circuitType.startsWith("境外")) {
                OverseaFee overseaFee = new OverseaFee();
                overseaFee.setCircuitType(circuitType);
                list = overseaFeeService.selectOverseaFeeList(overseaFee);
            }else {
                DomesticFee domesticFee = new DomesticFee();
                domesticFee.setCircuitType(circuitType);
                list = domesticFeeService.selectDomesticFeeList(domesticFee);
            }
        }
        model.put("circuit", circuit);
        return prefix + "/edit";
    }

    @PostMapping("/speedlistbyid")
    @ResponseBody
    public List findSpeedListById(@RequestParam("circuitId") String circuitId) {
        Circuit circuit = circuitService.selectCircuitById(circuitId);
        String circuitType = circuit.getCircuitType();
        List<?> list = new ArrayList<>();
        //获取编辑模态框的速率下拉列表
        if (circuitType!=null) {
            if (circuitType.startsWith("境外")) {
                OverseaFee overseaFee = new OverseaFee();
                overseaFee.setCircuitType(circuitType);
                list = overseaFeeService.selectOverseaFeeList(overseaFee);
            }else {
                DomesticFee domesticFee = new DomesticFee();
                domesticFee.setCircuitType(circuitType);
                list = domesticFeeService.selectDomesticFeeList(domesticFee);
            }
        }

        return list;
    }

    /**
     * 电路修改-POST
     */
    @RequiresPermissions("circuitmng:circuit:edit")
    @Log(title = "电路管理", action = BusinessType.UPDATE)
    @PostMapping("/edit")
    @Transactional(rollbackFor = Exception.class)
    @ResponseBody
    public AjaxResult editSave(Circuit circuit) {
        return toAjax(circuitService.updateCircuit(circuit));
    }

    /**
     * 电路删除
     */
    @RequiresPermissions("circuitmng:circuit:remove")
    @Log(title = "电路管理", action = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        
        return toAjax(circuitService.removeCircuitByIds(ids));
    }

    /**
     * Excel导入-GET
     * @return
     */
    @GetMapping("/batchimport")
    public String getBatchImport(ModelMap map) {
        map.put("customers",customerService.selectCustomerList(new Customer()));
        return prefix + "/batchimport";
    }

    /**
     * 电路检测——根据客户选择合同列表
     * @param customerId
     * @param map
     * @return
     */
    @GetMapping("/findContractList")
    @ResponseBody
    public List<ContractInfo> getContractList(@RequestParam("customerId") String customerId,
                                             @RequestParam("contractType") String contractType, ModelMap map) {
        System.out.println(customerId);
        System.out.println(contractType);
        List<ContractInfo> contractList = contractService.selectContractsByCustomerAndType(customerId,contractType);
        /*Set<ContractInfo> contractInfoSet = new HashSet<>();
        if (associationList!=null) {
            for (Association association: associationList) {
                if (association.getContract()!=null) {
                    ContractInfo contractInfo = contractService.selectContractByContractId(association.getContract().getContractId());

                    contractInfoSet.add(contractInfo);

                }
            }
        }*/
        System.err.println(contractList.size());
        map.put("contracts",contractList);
        return contractList;
    }

    /**
     * 电路检测
     * @param request
     * @param session
     * @return
     * @throws Exception
     */
    @PostMapping("/batchimporttest")
    @Log(title = "检测导入电路",action = BusinessType.IMPORT)
    @ResponseBody
    public Map<String, Map<String, String>> readExcel(HttpServletRequest request, HttpSession session) throws Exception {
        Boolean str = false;

        MultipartRequest fileRequest = (MultipartRequest) request;
        MultipartFile excel = fileRequest.getFile("excel");

        String customerId = request.getParameter("customer");
        String contractIds = request.getParameter("contract");
        System.out.println(customerId);
        System.out.println(contractIds);


        ExcelUtil<Circuit> excelUtil = new ExcelUtil<>(Circuit.class);
        List<Circuit> circuitList = excelUtil.importExcel(excel);
        System.out.println(circuitList.size());
        //用Map保存新增或者变更的电路信息
        Map<String, Map<String, String>> circuitMapList = new HashMap<>();
        if (customerId == null && contractIds == null) {
            //遍历获取的list
            for (Circuit circuit : circuitList) {
                //判断电路编号是否存在
                Circuit circuitOld = circuitService.selectCircuitByCircuitCode(circuit.getCircuitCode());
                if (circuitOld != null) {
                    //用于展示的电路对象实体
                    Circuit circuitShow = new Circuit();
                    //新建map保存信息
                    Map<String, String> changeInfo = new HashMap<>();
                    String changeField = "变更字段：";
                    //判断导入的电路信息和原有电路信息是否存在变更
                    boolean changeFlag = false;
                    if (circuitOld.getCircuitName() != null && circuit.getCircuitName() != null) {
                        if (!circuitOld.getCircuitName().equals(circuit.getCircuitName())) {
                            changeFlag = true;
                            circuitOld.setCircuitName(circuit.getCircuitName());
                            circuitShow.setCircuitName(circuit.getCircuitName());
                            changeField += "电路名称，";
                        }
                    }

                    if (circuitOld.getCircuitType() != null && circuit.getCircuitType() != null) {
                        if (!circuitOld.getCircuitType().equals(circuit.getCircuitType())) {
                            changeFlag = true;
                            circuitOld.setCircuitType(circuit.getCircuitType());
                            circuitShow.setCircuitType(circuit.getCircuitType());
                            changeField += "电路信息，";
                        }
                    }

                    if (circuitOld.getCircuitSpeed() != null && circuit.getCircuitSpeed() != null) {
                        if (!circuitOld.getCircuitSpeed().equals(circuit.getCircuitSpeed())) {
                            changeFlag = true;
                            circuitOld.setCircuitSpeed(circuit.getCircuitSpeed());
                            circuitShow.setCircuitSpeed(circuit.getCircuitSpeed());
                            changeField += "速率，";
                        }
                    }

                    if (circuitOld.getHomeEnd() != null && circuit.getHomeEnd() != null) {
                        if (!circuitOld.getHomeEnd().equals(circuit.getHomeEnd())) {
                            changeFlag = true;
                            circuitOld.setHomeEnd(circuit.getHomeEnd());
                            circuitShow.setHomeEnd(circuit.getHomeEnd());
                            changeField += "本端节点名称/槽位号/端口号，";
                        }
                    }

                    if (circuitOld.getOppEnd() != null && circuit.getOppEnd() != null) {
                        if (!circuitOld.getOppEnd().equals(circuit.getOppEnd())) {
                            changeFlag = true;
                            circuitOld.setOppEnd(circuit.getOppEnd());
                            circuitShow.setOppEnd(circuit.getOppEnd());
                            changeField += "对端节点名称/槽位号/端口号，";
                        }
                    }

                    if (circuitOld.getOpenTime() != null && circuit.getOpenTime() != null) {
                        if (!circuitOld.getOpenTime().equals(circuit.getOpenTime())) {
                            changeFlag = true;
                            circuitOld.setOpenTime(circuit.getOpenTime());
                            circuitShow.setOpenTime(circuit.getOpenTime());
                            changeField += "启付时间，";
                        }
                    }

                    if (circuitOld.getConfigTime() != null && circuit.getConfigTime() != null) {
                        if (!circuitOld.getConfigTime().equals(circuit.getConfigTime())) {
                            changeFlag = true;
                            circuitOld.setConfigTime(circuit.getConfigTime());
                            circuitShow.setConfigTime(circuit.getConfigTime());
                            changeField += "配置时间，";
                        }
                    }

                    if (circuitOld.getCancelTime() != null && circuit.getCancelTime() != null) {
                        if (!circuitOld.getCancelTime().equals(circuit.getCancelTime())) {
                            changeFlag = true;
                            circuitOld.setCancelTime(circuit.getCancelTime());
                            circuitShow.setCancelTime(circuit.getCancelTime());
                            changeField += "取消时间，";
                        }
                    }

                    if (circuitOld.getIomsApplyNumber() != null && circuit.getIomsApplyNumber() != null) {
                        if (!circuitOld.getIomsApplyNumber().equals(circuit.getIomsApplyNumber())) {
                            changeFlag = true;
                            circuitOld.setIomsApplyNumber(circuit.getIomsApplyNumber());
                            circuitShow.setIomsApplyNumber(circuit.getIomsApplyNumber());
                            changeField += "运维平台申请编号，";
                        }
                    }

                    if (circuitOld.getBasisFile() != null && circuit.getBasisFile() != null) {
                        if (!circuitOld.getBasisFile().equals(circuit.getBasisFile())) {
                            changeFlag = true;
                            circuitOld.setBasisFile(circuit.getBasisFile());
                            circuitShow.setBasisFile(circuit.getBasisFile());
                            changeField += "依据文件，";
                        }
                    }


                    if (changeFlag) {
                        if (circuitShow != null) {
                            changeInfo.put(changeField, "change");
                            circuitMapList.put(circuit.getCircuitCode(), changeInfo);
                        }

                        //修改电路
                        //circuitService.updateCircuit(circuitOld);
                    }

                } else {
                    Map<String, String> newInfo = new HashMap<>();
                    newInfo.put("", "new");
                    circuitMapList.put(circuit.getCircuitCode(), newInfo);

                    //新增电路
                    //circuitService.insertCircuit(circuit);
                }
            }

            return circuitMapList;
        } else {
            //根据条件查找数据库中的电路列表(待比较list)
            List<Circuit> circuitListDB = circuitService.selectCircuitByCustomerAndContract(customerId, contractIds);
            Set<String> circuitCodeDBSet = new HashSet<>();
            for (Circuit circuit1 : circuitListDB) {
                circuitCodeDBSet.add(circuit1.getCircuitCode());
            }
            Set<String> circuitCodeExcelSet = new HashSet<>();
            for (Circuit circuit2 : circuitList) {
                circuitCodeExcelSet.add(circuit2.getCircuitCode());
            }

            //遍历获取的list
            for (Circuit circuit : circuitList) {
                //如果excel中数据不存在于circuitDB中，说明是新增电路
                if (!circuitCodeDBSet.contains(circuit.getCircuitCode())) {
                    Map<String, String> newInfo = new HashMap<>();
                    newInfo.put("", "new");
                    circuitMapList.put(circuit.getCircuitCode(), newInfo);
                } else {
                    //判断电路编号是否存在
                    Circuit circuitOld = circuitService.selectCircuitByCircuitCode(circuit.getCircuitCode());
                    if (circuitOld != null) {
                        //用于展示的电路对象实体
                        Circuit circuitShow = new Circuit();
                        //新建map保存信息
                        Map<String, String> changeInfo = new HashMap<>();
                        String changeField = "变更字段：";
                        //判断导入的电路信息和原有电路信息是否存在变更
                        boolean changeFlag = false;
                        if (circuitOld.getCircuitName() != null && circuit.getCircuitName() != null) {
                            if (!circuitOld.getCircuitName().equals(circuit.getCircuitName())) {
                                changeFlag = true;
                                circuitOld.setCircuitName(circuit.getCircuitName());
                                circuitShow.setCircuitName(circuit.getCircuitName());
                                changeField += "电路名称，";
                            }
                        }

                        if (circuitOld.getCircuitType() != null && circuit.getCircuitType() != null) {
                            if (!circuitOld.getCircuitType().equals(circuit.getCircuitType())) {
                                changeFlag = true;
                                circuitOld.setCircuitType(circuit.getCircuitType());
                                circuitShow.setCircuitType(circuit.getCircuitType());
                                changeField += "电路信息，";
                            }
                        }

                        if (circuitOld.getCircuitSpeed() != null && circuit.getCircuitSpeed() != null) {
                            if (!circuitOld.getCircuitSpeed().equals(circuit.getCircuitSpeed())) {
                                changeFlag = true;
                                circuitOld.setCircuitSpeed(circuit.getCircuitSpeed());
                                circuitShow.setCircuitSpeed(circuit.getCircuitSpeed());
                                changeField += "速率，";
                            }
                        }

                        if (circuitOld.getHomeEnd() != null && circuit.getHomeEnd() != null) {
                            if (!circuitOld.getHomeEnd().equals(circuit.getHomeEnd())) {
                                changeFlag = true;
                                circuitOld.setHomeEnd(circuit.getHomeEnd());
                                circuitShow.setHomeEnd(circuit.getHomeEnd());
                                changeField += "本端节点名称/槽位号/端口号，";
                            }
                        }

                        if (circuitOld.getOppEnd() != null && circuit.getOppEnd() != null) {
                            if (!circuitOld.getOppEnd().equals(circuit.getOppEnd())) {
                                changeFlag = true;
                                circuitOld.setOppEnd(circuit.getOppEnd());
                                circuitShow.setOppEnd(circuit.getOppEnd());
                                changeField += "对端节点名称/槽位号/端口号，";
                            }
                        }

                        if (circuitOld.getOpenTime() != null && circuit.getOpenTime() != null) {
                            if (!circuitOld.getOpenTime().equals(circuit.getOpenTime())) {
                                changeFlag = true;
                                circuitOld.setOpenTime(circuit.getOpenTime());
                                circuitShow.setOpenTime(circuit.getOpenTime());
                                changeField += "启付时间，";
                            }
                        }

                        if (circuitOld.getConfigTime() != null && circuit.getConfigTime() != null) {
                            if (!circuitOld.getConfigTime().equals(circuit.getConfigTime())) {
                                changeFlag = true;
                                circuitOld.setConfigTime(circuit.getConfigTime());
                                circuitShow.setConfigTime(circuit.getConfigTime());
                                changeField += "配置时间，";
                            }
                        }

                        if (circuitOld.getCancelTime() != null && circuit.getCancelTime() != null) {
                            if (!circuitOld.getCancelTime().equals(circuit.getCancelTime())) {
                                changeFlag = true;
                                circuitOld.setCancelTime(circuit.getCancelTime());
                                circuitShow.setCancelTime(circuit.getCancelTime());
                                changeField += "取消时间，";
                            }
                        }

                        if (circuitOld.getIomsApplyNumber() != null && circuit.getIomsApplyNumber() != null) {
                            if (!circuitOld.getIomsApplyNumber().equals(circuit.getIomsApplyNumber())) {
                                changeFlag = true;
                                circuitOld.setIomsApplyNumber(circuit.getIomsApplyNumber());
                                circuitShow.setIomsApplyNumber(circuit.getIomsApplyNumber());
                                changeField += "运维平台申请编号，";
                            }
                        }

                        if (circuitOld.getBasisFile() != null && circuit.getBasisFile() != null) {
                            if (!circuitOld.getBasisFile().equals(circuit.getBasisFile())) {
                                changeFlag = true;
                                circuitOld.setBasisFile(circuit.getBasisFile());
                                circuitShow.setBasisFile(circuit.getBasisFile());
                                changeField += "依据文件，";
                            }
                        }


                        if (changeFlag) {
                            if (circuitShow != null) {
                                changeInfo.put(changeField, "change");
                                circuitMapList.put(circuit.getCircuitCode(), changeInfo);
                            }

                            //修改电路
                            //circuitService.updateCircuit(circuitOld);
                        }
                    }
                }

                for (Circuit circuit3 : circuitListDB) {
                    //如果DB中数据不存在于Excel数据中，说明是删除电路
                    if (!circuitCodeExcelSet.contains(circuit3.getCircuitCode())) {
                        Map<String, String> deleteInfo = new HashMap<>();
                        deleteInfo.put("", "delete");
                        circuitMapList.put(circuit.getCircuitCode(), deleteInfo);
                    }
                }
            }
            return circuitMapList;
        }

    }

    /**
     * 电路导入-POST
     * @param request
     * @param session
     * @return
     * @throws Exception
     */
    @PostMapping("/batchimport")
    @Log(title = "导入电路",action = BusinessType.IMPORT)
    @ResponseBody
    public AjaxResult importExcel(MultipartRequest request, HttpSession session) throws Exception {
        MultipartFile excel = request.getFile("excel");

        int count = 0;
        ExcelUtil<Circuit> excelUtil = new ExcelUtil<>(Circuit.class);
        List<Circuit> circuitList = excelUtil.importExcel(excel);
        //遍历获取的list
        for (Circuit circuit: circuitList) {
            //判断电路编号是否存在
            Circuit circuitOld = circuitService.selectCircuitByCircuitCode(circuit.getCircuitCode());
            if (circuitOld != null) {
                //用于展示的电路对象实体
                Circuit circuitShow = new Circuit();
                //新建map保存信息
                //判断导入的电路信息和原有电路信息是否存在变更
                boolean changeFlag = false;
                if (circuitOld.getCircuitName()!= null && circuit.getCircuitName()!=null) {
                    if (!circuitOld.getCircuitName().equals(circuit.getCircuitName())) {
                        changeFlag = true;
                        circuitOld.setCircuitName(circuit.getCircuitName());
                        circuitShow.setCircuitName(circuit.getCircuitName());
                    }
                }

                if (circuitOld.getCircuitType()!=null && circuit.getCircuitType()!=null) {
                    if(!circuitOld.getCircuitType().equals(circuit.getCircuitType()) ) {
                        changeFlag = true;
                        circuitOld.setCircuitType(circuit.getCircuitType());
                        circuitShow.setCircuitType(circuit.getCircuitType());
                    }
                }

                if (circuitOld.getCircuitSpeed() != null && circuit.getCircuitSpeed() != null) {
                    if(!circuitOld.getCircuitSpeed().equals(circuit.getCircuitSpeed())) {
                        changeFlag = true;
                        circuitOld.setCircuitSpeed(circuit.getCircuitSpeed());
                        circuitShow.setCircuitSpeed(circuit.getCircuitSpeed());
                    }
                }

                if (circuitOld.getHomeEnd()!=null && circuit.getHomeEnd()!=null ) {
                    if(!circuitOld.getHomeEnd().equals(circuit.getHomeEnd())){
                        changeFlag = true;
                        circuitOld.setHomeEnd(circuit.getHomeEnd());
                        circuitShow.setHomeEnd(circuit.getHomeEnd());
                    }
                }

                if (circuitOld.getOppEnd()!=null && circuit.getOppEnd()!=null) {
                    if(!circuitOld.getOppEnd().equals(circuit.getOppEnd())){
                        changeFlag = true;
                        circuitOld.setOppEnd(circuit.getOppEnd());
                        circuitShow.setOppEnd(circuit.getOppEnd());
                    }
                }

                if (circuitOld.getOpenTime()!= null && circuit.getOpenTime()!=null) {
                    if(!circuitOld.getOpenTime().equals(circuit.getOpenTime())) {
                        changeFlag = true;
                        circuitOld.setOpenTime(circuit.getOpenTime());
                        circuitShow.setOpenTime(circuit.getOpenTime());
                    }
                }

                if (circuitOld.getConfigTime()!=null && circuit.getConfigTime()!=null) {
                    if(!circuitOld.getConfigTime().equals(circuit.getConfigTime())) {
                        changeFlag = true;
                        circuitOld.setConfigTime(circuit.getConfigTime());
                        circuitShow.setConfigTime(circuit.getConfigTime());
                    }
                }

                if (circuitOld.getCancelTime()!=null && circuit.getCancelTime()!=null) {
                    if(!circuitOld.getCancelTime().equals(circuit.getCancelTime())){
                        changeFlag = true;
                        circuitOld.setCancelTime(circuit.getCancelTime());
                        circuitShow.setCancelTime(circuit.getCancelTime());
                    }
                }

                if (circuitOld.getIomsApplyNumber()!=null && circuit.getIomsApplyNumber()!=null) {
                    if(!circuitOld.getIomsApplyNumber().equals(circuit.getIomsApplyNumber()) ){
                        changeFlag = true;
                        circuitOld.setIomsApplyNumber(circuit.getIomsApplyNumber());
                        circuitShow.setIomsApplyNumber(circuit.getIomsApplyNumber());
                    }
                }

                if (circuitOld.getBasisFile()!=null && circuit.getBasisFile()!= null) {
                    if(!circuitOld.getBasisFile().equals(circuit.getBasisFile())) {
                        changeFlag = true;
                        circuitOld.setBasisFile(circuit.getBasisFile());
                        circuitShow.setBasisFile(circuit.getBasisFile());
                    }
                }

                if (changeFlag) {
                    //修改电路
                    count+=circuitService.updateCircuit(circuitOld);
                }

            }else {
                //新增电路
                count+=circuitService.insertCircuit(circuit);
            }
        }

       return toAjax(count);

    }

    /**
     * 获取最新（一周内）的即将到期的电路信息
     * @return
     */
    @PostMapping("/latestOverCircuitInfos")
    @ResponseBody
    public String getLatestOverCircuitInfos() {
        return circuitService.getLatestOverCircuitInfos();
    }

    /**
     * 检验电路编号是否唯一
     * @param circuit
     * @return
     */
    @PostMapping("/checkCircuitCodeNameUnique")
    @ResponseBody
    public String checkCircuitCodeNameUnique(Circuit circuit) {
        String unique = circuitService.checkCircuitCodeNameUnique(circuit.getCircuitCode(),circuit.getCircuitId());
        return unique;
    }
}
