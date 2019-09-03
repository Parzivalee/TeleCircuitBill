package cn.com.atnc.teleCircuitBill.project.customerInfo.customers.controller;

import cn.com.atnc.teleCircuitBill.common.utils.poi.ExcelUtil;
import cn.com.atnc.teleCircuitBill.framework.aspectj.lang.annotation.Log;
import cn.com.atnc.teleCircuitBill.framework.aspectj.lang.constant.BusinessType;
import cn.com.atnc.teleCircuitBill.framework.web.controller.BaseController;
import cn.com.atnc.teleCircuitBill.framework.web.domain.AjaxResult;
import cn.com.atnc.teleCircuitBill.framework.web.page.TableDataInfo;
import cn.com.atnc.teleCircuitBill.project.customerInfo.customers.domain.Customer;
import cn.com.atnc.teleCircuitBill.project.customerInfo.customers.domain.CustomerExportModel;
import cn.com.atnc.teleCircuitBill.project.customerInfo.customers.service.CustomerService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 客户信息Controller类
 * @author liwenjie
 * @Date 2018-08-20
 */
@Controller
@RequestMapping("customerInfo/customers")
public class CustomerController extends BaseController {
    private String prefix = "customerInfo/customers";

    @Autowired
    private CustomerService customerService;

    /**
     * 获取客户信息-GET
     * @return
     */
    @RequiresPermissions("customerInfo:customers:view")
    @GetMapping()
    public String user()
    {
        return prefix + "/customer";
    }

    /**
     * 获取客户信息-POST
     * @param customer
     * @return
     */
    @RequiresPermissions("customerInfo:customers:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(Customer customer)
    {
        startPage();
        List<Customer> list = customerService.selectCustomerList(customer);
        return getDataTable(list);
    }


    /**
     * 客户新增-get
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 客户新增-post
     */
    @RequiresPermissions("customerInfo:customers:add")
    @Log(title = "客户管理", action = BusinessType.INSERT)
    @PostMapping("/add")
    @Transactional(rollbackFor = Exception.class)
    @ResponseBody
    public AjaxResult addSave(Customer customer)
    {
        return toAjax(customerService.insertCustomer(customer));
    }

    /**
     * 客户修改-GET
     */
    @GetMapping("/edit/{customerId}")
    public String edit(@PathVariable("customerId") String customerId, ModelMap model)
    {
        model.put("customer", customerService.findCustomerByCustomerId(customerId));
        return prefix + "/edit";
    }

    /**
     * 修改保存用户-POST
     */
    @RequiresPermissions("customerInfo:customers:edit")
    @Log(title = "客户管理", action = BusinessType.UPDATE)
    @PostMapping("/edit")
    @Transactional(rollbackFor = Exception.class)
    @ResponseBody
    public AjaxResult editSave(Customer customer) {
        return toAjax(customerService.updateCustomer(customer));
    }

    /**
     * 删除客户
     * @param ids
     * @return
     */
    @RequiresPermissions("customerInfo:customers:remove")
    @Log(title = "客户管理", action = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(customerService.deleteCountryByIds(ids));
    }

    @Log(title = "客户管理", action = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(Customer customer) throws Exception
    {
        try
        {
            List<Customer> list = customerService.selectCustomerList(customer);
            List<CustomerExportModel> modelList = new ArrayList<>();
            list.stream().forEach(customer1 -> {
                CustomerExportModel model = new CustomerExportModel();
                if (customer1.getArea() != null && !customer1.getArea().equals("")) {
                    switch (customer1.getArea()) {
                        case "0":
                            model.setArea("华北");
                            break;
                        case "1":
                            model.setArea("中南");
                            break;
                        case "2":
                            model.setArea("东北");
                            break;
                        case "3":
                            model.setArea("西南");
                            break;
                        case "4":
                            model.setArea("西北");
                            break;
                        case "5":
                            model.setArea("华东");
                            break;
                        case "6":
                            model.setArea("新疆");
                            break;
                        case "7":
                            model.setArea("民航局空管局");
                            break;
                    }
                    model.setCustomerName(customer1.getCustomerName());
                    model.setTranslateName(customer1.getTranslateName());
                    model.setShortName(customer1.getShortName());
                    model.setCode(customer1.getCode());
                    model.setLegalPerson(customer1.getLegalPerson());
                    model.setLegalPersonPhone(customer1.getLegalPersonPhone());
                    model.setDelegation(customer1.getDelegation());
                    model.setDelegationPhone(customer1.getDelegationPhone());
                    model.setBank(customer1.getBank());
                    model.setBankAccount(customer1.getBankAccount());
                    model.setAddress(customer1.getAddress());
                    model.setEmail(customer1.getEmail());
                    model.setPhoneNumber(customer1.getPhoneNumber());
                    model.setFax(customer1.getFax());
                    model.setUrl(customer1.getUrl());
                   if (customer1.getRegion() != null && !customer1.getRegion().equals("")) {
                       switch (customer1.getRegion()) {
                           case "0":
                               model.setRegion("境内");
                               break;
                           case "1":
                               model.setRegion("境外");
                               break;
                           case "2":
                               model.setRegion("港澳台");
                               break;
                       }
                   }
                   if (customer1.getLevel() != null && !customer1.getLevel().equals("")) {
                       switch (customer1.getLevel()) {
                           case "0":
                               model.setLevel("高");
                               break;
                           case "1":
                               model.setLevel("中");
                               break;
                           case "2":
                               model.setLevel("低");
                               break;
                       }
                   }

                   modelList.add(model);
                }

            });
            ExcelUtil<CustomerExportModel> util = new ExcelUtil<>(CustomerExportModel.class);
            return util.exportExcel(modelList, "客户表");
        }
        catch (Exception e)
        {
            return error("导出Excel失败，请联系网站管理员！");
        }
    }

}
