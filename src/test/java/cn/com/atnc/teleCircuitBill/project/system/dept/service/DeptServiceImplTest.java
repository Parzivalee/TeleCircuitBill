package cn.com.atnc.teleCircuitBill.project.system.dept.service;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import java.util.List;
import java.util.Map;

/**
 * DeptServiceImpl Tester.
 *
 * @author Leonhardt
 * @version 1.0
 * @since 07/22/2018
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class DeptServiceImplTest
{
    @Autowired
    private IDeptService deptService;

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    /**
     * Method: selectDeptList(Dept dept)
     */
    @Test
    public void testSelectDeptList() throws Exception
    {
    }

    /**
     * Method: selectDeptAll()
     */
    @Test
    public void testSelectDeptAll() throws Exception
    {
        Assert.assertEquals(deptService.selectDeptAll().size(), 10);
    }

    /**
     * Method: selectDeptTree()
     */
    @Test
    public void testSelectDeptTree() throws Exception
    {
        List<Map<String, Object>> trees = deptService.selectDeptTree();
        trees.stream().forEach(tree -> System.out.println(tree));
    }

    /**
     * Method: selectDeptCount(Long parentId)
     */
    @Test
    public void testSelectDeptCount() throws Exception
    {
        Assert.assertEquals(10, deptService.selectDeptCount(0L));
    }

    /**
     * Method: checkDeptExistUser(Long deptId)
     */
    @Test
    public void testCheckDeptExistUser() throws Exception
    {
    }

    /**
     * Method: deleteDeptById(Long deptId)
     */
    @Test
    public void testDeleteDeptById() throws Exception
    {
    }

    /**
     * Method: saveDept(Dept dept)
     */
    @Test
    public void testSaveDept() throws Exception
    {
    }

    /**
     * Method: selectDeptById(Long deptId)
     */
    @Test
    public void testSelectDeptById() throws Exception
    {
        Assert.assertNotNull("不存在", deptService.selectDeptById(100L));
    }

    /**
     * Method: checkDeptNameUnique(Dept dept)
     */
    @Test
    public void testCheckDeptNameUnique() throws Exception {
    }

}
