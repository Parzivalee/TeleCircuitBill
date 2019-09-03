package cn.com.atnc.teleCircuitBill.project.contractmng.contractInfo.service;

import cn.com.atnc.teleCircuitBill.common.constant.UserConstants;
import cn.com.atnc.teleCircuitBill.common.support.Convert;
import cn.com.atnc.teleCircuitBill.common.utils.DateUtils;
import cn.com.atnc.teleCircuitBill.common.utils.file.FileUploadUtils;
import cn.com.atnc.teleCircuitBill.common.utils.security.ShiroUtils;
import cn.com.atnc.teleCircuitBill.project.changemng.service.IHistoryDataService;
import cn.com.atnc.teleCircuitBill.project.circuitmng.circuitInfo.domain.Circuit;
import cn.com.atnc.teleCircuitBill.project.contractmng.contractInfo.domain.Association;
import cn.com.atnc.teleCircuitBill.project.contractmng.contractInfo.domain.ContractAttachment;
import cn.com.atnc.teleCircuitBill.project.contractmng.contractInfo.domain.ContractInfo;
import cn.com.atnc.teleCircuitBill.project.contractmng.contractInfo.mapper.AssociationMapper;
import cn.com.atnc.teleCircuitBill.project.contractmng.contractInfo.mapper.ContractMapper;
import cn.com.atnc.teleCircuitBill.project.customerInfo.customers.domain.Customer;
import cn.com.atnc.teleCircuitBill.project.customerInfo.customers.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

/**
 * 合同信息Service层接口实现类
 * @author liwenjie
 */
@Service
public class ContractServiceImpl implements ContractService {
    private static final String CONTRACT = "contract";
    private static final String ADD = "新增";
    private static final String EDIT = "变更";
    private static final String REMOVE = "取消";
    private static final String CIRCUITCHANGE = "电路变更";

    @Autowired
    private ContractMapper contractMapper;
    @Autowired
    private AssociationMapper associationMapper;
    @Autowired
    private IHistoryDataService historyDataService;
    @Autowired
    private CustomerService customerService;

    @Override
    public List<ContractInfo> selectContractList(ContractInfo contractInfo) {
        return contractMapper.selectContractList(contractInfo);
    }

    @Override
    public ContractInfo selectContractByContractId(String contractId) {
        return contractMapper.selectContractByContractId(contractId);
    }

    @Override
    public List<ContractInfo> selectContractByContractType(String contractType) {
        return contractMapper.selectContractByContractType(contractType);
    }

    @Override
    public int removeContractByIds(String ids) {
        //新增历史数据信息
        String[] idArrays = ids.split(",");
        for (int i=0;i<idArrays.length;i++) {
            String circuitCode = this.selectContractByContractId(idArrays[i]).getContractNumber();
            historyDataService.insertCircuitHistoryData(CONTRACT,REMOVE,"",circuitCode);
        }

        return contractMapper.removeContractByIds(Convert.toStrArray(ids));
    }

    @Override
    public int insertContract(ContractInfo contract) {
        //新增历史数据信息
        historyDataService.insertCircuitHistoryData(CONTRACT,ADD,"",contract.getContractNumber());
        Customer customer = contract.getCustomer();
        //新增合同的同时，修改客户的合同属性
        String contractType = contract.getContractType();
        if (contractType.equals("Access")) {
            customer.setAccess(true);
            customer.setAccessSum(customer.getAccessSum()+1);
        }else if (contractType.equals("Hire")) {
            customer.setRent(true);
            customer.setRentSum(customer.getRentSum()+1);
        }else if (contractType.equals("Maintain")) {
            customer.setMaintain(true);
            customer.setMaintainSum(customer.getMaintainSum()+1);
        }
        customerService.updateCustomer(customer);
        contract.setCreateBy(ShiroUtils.getLoginName());
        //是否生成账单
        contract.setGenerateBill(0);
        //是否过期
        contract.setIsExpired(0);
        //是否变更
        contract.setChangeStatus(0);
        return contractMapper.insertContract(contract);
    }

    @Override
    public int updateContract(ContractInfo contract) {
        //新增历史数据信息
        ContractInfo oldContract = this.selectContractByContractId(contract.getContractId());
        String changeContent = this.compareContract(contract,oldContract);
        historyDataService.insertCircuitHistoryData(CONTRACT,EDIT,changeContent,oldContract.getContractNumber());

        contract.setUpdateBy(ShiroUtils.getLoginName());
        return contractMapper.updateContract(contract);
    }

    @Override
    public List<ContractInfo> selectContractByCustomerId(String customerId) {
        return contractMapper.selectContractByCustomerId(customerId);
    }

    @Override
    public void uploadFiles(String contractId, String attachmentType, MultipartFile[] file) {
        //新建附件信息
        ContractAttachment attachment = new ContractAttachment();
        ContractInfo contractInfo = contractMapper.selectContractByContractId(contractId);
        attachment.setContractInfo(contractInfo);
        attachment.setAttachmentType(attachmentType);
        String elecDocument = null;
        String scanDocument = null;
        for (int i=0;i<file.length;i++) {
            System.out.println(file[i]);
        }
       /* String avatar = FileUploadUtils.upload(file);
        user.setAvatar(avatar);
        if (userService.updateUserInfo(user) > 0)
        {
            setUser(userService.selectUserById(user.getUserId()));
            return success();
        }*/
    }

    @Override
    public String compareContract(ContractInfo contract, ContractInfo oldContract) {
        String difference = "";
        //合同类型
        if (contract.getContractType()!=null && oldContract.getContractType() != null) {
            if (!contract.getContractType().equals(oldContract.getContractType())) {
                difference+="合同类型，";
            }
        }else if (contract.getContractType()!=null || oldContract.getContractType() != null){
            difference+="合同类型，";
        }
        //签订类型
        if (contract.getContractSignType()!=null && oldContract.getContractSignType() != null) {
            if (!contract.getContractSignType().equals(oldContract.getContractSignType())) {
                difference+="签订类型，";
            }
        }else if (contract.getContractSignType()!=null || oldContract.getContractSignType() != null){
            difference+="签订类型，";
        }
        //区域
        if (contract.getArea()!=null && oldContract.getArea() != null) {
            if (!contract.getArea().equals(oldContract.getArea())) {
                difference+="区域，";
            }
        }else if (contract.getArea()!=null || oldContract.getArea() != null){
            difference+="区域，";
        }
        //客户
        if (contract.getCustomer().getCustomerId()!=null && oldContract.getCustomer().getCustomerId() != null) {
            if (!contract.getCustomer().getCustomerId().equals(oldContract.getCustomer().getCustomerId())) {
                difference+="客户，";
            }
        }else if (contract.getCustomer().getCustomerId()!=null || oldContract.getCustomer().getCustomerId() != null){
            difference+="客户，";
        }
        //合同编号
        if (contract.getContractNumber()!=null && oldContract.getContractNumber()!= null) {
            if (!contract.getContractNumber().equals(oldContract.getContractNumber())) {
                difference+="合同编号，";
            }
        }else if (contract.getContractNumber()!=null || oldContract.getContractNumber() != null){
            difference+="合同编号，";
        }
        //空管局合同编号
        if (contract.getAirTrafficContractNumber()!=null && oldContract.getAirTrafficContractNumber() != null) {
            if (!contract.getAirTrafficContractNumber().equals(oldContract.getAirTrafficContractNumber())) {
                difference+="空管局合同编号，";
            }
        }else if (contract.getAirTrafficContractNumber()!=null || oldContract.getAirTrafficContractNumber() != null){
            difference+="空管局合同编号，";
        }
        //合同名称
        if (contract.getContractName()!=null && oldContract.getContractName() != null) {
            if (!contract.getContractName().equals(oldContract.getContractName())) {
                difference+="合同名称，";
            }
        }else if (contract.getContractName()!=null || oldContract.getContractName() != null){
            difference+="合同名称，";
        }
        //甲方
        if (contract.getPartA().getCustomerId()!=null && oldContract.getPartA() != null) {
            if (!contract.getPartA().getCustomerId().equals(oldContract.getPartA().getCustomerId())) {
                difference+="甲方，";
            }
        }else if (contract.getPartA().getCustomerId()!=null || oldContract.getPartA() != null){
            difference+="甲方，";
        }
        //乙方
        if (contract.getPartB().getCustomerId()!=null && oldContract.getPartB() != null) {
            if (!contract.getPartB().getCustomerId().equals(oldContract.getPartB().getCustomerId())) {
                difference+="乙方，";
            }
        }else if (contract.getPartB().getCustomerId()!=null || oldContract.getPartB() != null){
            difference+="乙方，";
        }
        //合同签订日期
        if (contract.getContractConfigDate()!=null && oldContract.getContractConfigDate() != null) {
            if (!contract.getContractConfigDate().equals(oldContract.getContractConfigDate())) {
                difference+="合同签订日期，";
            }
        }else if (contract.getContractConfigDate()!=null || oldContract.getContractConfigDate() != null){
            difference+="合同签订日期，";
        }
        //合同生效日期
        if (contract.getContractStartDate()!=null && oldContract.getContractStartDate() != null) {
            if (!contract.getContractStartDate().equals(oldContract.getContractStartDate())) {
                difference+="合同生效日期，";
            }
        }else if (contract.getContractStartDate()!=null || oldContract.getContractStartDate() != null){
            difference+="合同生效日期，";
        }

        //是否顺延
        if (contract.getIsContractAutoPostpone()!=null && oldContract.getIsContractAutoPostpone() != null) {
            if (!contract.getIsContractAutoPostpone().equals(oldContract.getIsContractAutoPostpone())) {
                difference+="是否顺延，";
            }
        }else if (contract.getIsContractAutoPostpone()!=null || oldContract.getIsContractAutoPostpone() != null){
            difference+="是否顺延，";
        }

        return difference;
    }

    @Override
    public String getLatestContractInfos() {
        Date today = DateUtils.getNowDate();
        Date day7before = DateUtils.strToDate(DateUtils.getNextDay(DateUtils
                .getStringDateShort(), "-7"));
        List<ContractInfo> list = contractMapper.selectContractListInDays(day7before,today);
        String str = "";
        for (ContractInfo info : list) {
            str += "<p>" + info.getContractNumber() + " "
                    + info.getCustomer().getCustomerName() + " "
                    + DateUtils.dateToStr(info.getContractConfigDate())
                    + "</p>";
        }
        return str;
    }

    @Override
    public String checkContractNumberUnique(String contractNumber,String contractId) {
        List<ContractInfo> contracts = contractMapper.selectContractByContractNumber(contractNumber);

        if (contracts.size() == 0) {
            return UserConstants.USER_NAME_UNIQUE;
        }else if (contracts.size() == 1) {
            if (contracts.get(0).getContractId().equals(contractId)) {
                return UserConstants.USER_NAME_UNIQUE;
            }else {
                return UserConstants.USER_NAME_NOT_UNIQUE;
            }
        }else {
            return UserConstants.USER_NAME_NOT_UNIQUE;
        }
    }

    @Override
    public int changeContract(ContractInfo contract, String contractNumberNew) {
        ContractInfo contractOld = contractMapper.selectContractByContractId(contract.getContractId());
        if (contract.getContractStopDate() != null) {
            contractOld.setContractStopDate(contract.getContractStopDate());
        }else {
            System.out.println("合同终止日期为空");
            return 0;
        }
        contractOld.setChangeStatus(1);
        contractOld.setUpdateBy(ShiroUtils.getLoginName());
        //将原有合同设置为已变更
        contractMapper.updateContract(contractOld);

        //生成新的合同
        ContractInfo contractNew = new ContractInfo();
        contractNew.setContractNumber(contractNumberNew);
        contractNew.setContractType(contractOld.getContractType());
        contractNew.setContractSignType(contractOld.getContractSignType());
        contractNew.setArea(contractOld.getArea());
        contractNew.setCustomer(contractOld.getCustomer());
        contractNew.setAirTrafficContractNumber(contractOld.getAirTrafficContractNumber());
        contractNew.setContractName(contractOld.getContractName());
        contractNew.setPartA(contractOld.getPartA());
        contractNew.setPartB(contractOld.getPartB());
        contractNew.setContractConfigDate(contractOld.getContractConfigDate());
        contractNew.setContractStartDate(contractOld.getContractStartDate());
        contractNew.setContractEndDate(contractOld.getContractEndDate());
        contractNew.setIsContractAutoPostpone(contractOld.getIsContractAutoPostpone());
        contractNew.setGenerateBill(contractOld.getGenerateBill());
        contractNew.setChangeStatus(0);
        contractNew.setCreateBy(ShiroUtils.getLoginName());

        int flag = contractMapper.insertContract(contractNew);
        //获取新生成的合同的id
        ContractInfo contractNew1 = contractMapper.selectContractByContractNumber(contractNumberNew).get(0);


        //如果是技术服务合同或维护合同改变relation表。入网合同则不需要
        List<Association> associationList = associationMapper.selectAssociationByContractId(contractOld.getContractId());

        associationList.stream().forEach(association -> {
            //终止原有的关联表，并生成新的合同-电路关联表
            association.setEndTime(contract.getContractStopDate());
            association.setIsEnd(1);
            association.setUpdateBy(ShiroUtils.getLoginName());
            associationMapper.updateAssociation(association);

            Association associationNew = new Association();
            associationNew.setContract(contractNew1);
            associationNew.setCircuit(association.getCircuit());
            associationNew.setCustomer(association.getCustomer());
            associationNew.setStartTime(contract.getContractStopDate());
            associationNew.setIsEnd(0);
            associationNew.setFeePercent(association.getFeePercent());
            associationNew.setFeeCir(association.getFeeCir());
            associationNew.setFeeSum(association.getFeeSum());
            associationNew.setFeePort(association.getFeePort());
            association.setCreateBy(ShiroUtils.getLoginName());
            //保存新的合同-电路关联表
            associationMapper.insertAssociation(associationNew);
        });

        return flag;

    }

    @Override
    public void checkContractIsExpired() {
        ContractInfo newContract = new ContractInfo();
        //对所有未过期且有合同终止日期的合同进行判断
        newContract.setIsExpired(0);
        List<ContractInfo> contractInfoList = contractMapper.selectContractList(newContract);
        //筛选出所有有终止日期的合同
        contractInfoList.parallelStream()
                .filter(contractInfo -> contractInfo.getContractStopDate()!=null)
                .forEach(contractInfo1 -> {
                    Date contractStopDate = contractInfo1.getContractStopDate();
                    Date contractStopDateAfter2years = org.apache.commons.lang.time.DateUtils
                            .addYears(contractStopDate,2);
                    //获取今天的日期
                    Date todayDate = new Date();

                    //如果合同的终止日期超过2年，则合同过期
                    if (contractStopDateAfter2years.after(todayDate)) {
                        contractInfo1.setIsExpired(1);
                        contractMapper.updateContract(contractInfo1);
                    }

                });

    }

    @Override
    public List<ContractInfo> selectContractsByCustomerAndType(String customerId, String contractType) {
        return contractMapper.selectContractsByCustomerAndType(customerId,contractType);
    }

}
