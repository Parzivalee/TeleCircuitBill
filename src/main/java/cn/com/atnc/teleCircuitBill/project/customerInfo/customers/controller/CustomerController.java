package cn.com.atnc.teleCircuitBill.project.customerInfo.customers.controller;

import cn.com.atnc.teleCircuitBill.framework.aspectj.lang.annotation.Log;
import cn.com.atnc.teleCircuitBill.framework.aspectj.lang.constant.BusinessType;
import cn.com.atnc.teleCircuitBill.framework.web.controller.BaseController;
import cn.com.atnc.teleCircuitBill.framework.web.domain.AjaxResult;
import cn.com.atnc.teleCircuitBill.framework.web.page.TableDataInfo;
import cn.com.atnc.teleCircuitBill.project.customerInfo.customers.domain.Customer;
import cn.com.atnc.teleCircuitBill.project.customerInfo.customers.service.CustomerService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 客户信息Controller类
 * @author liwenjie
 * @Date 2018-08-20
 */
@Controller
@RequestMapping("customerInfo/customers")
public class CustomerController extends BaseController {
    private String prefix = "customerInfo/customers/";

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

}
