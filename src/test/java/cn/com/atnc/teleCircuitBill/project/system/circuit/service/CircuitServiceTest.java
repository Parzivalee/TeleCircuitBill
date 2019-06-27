package cn.com.atnc.teleCircuitBill.project.system.circuit.service;

import cn.com.atnc.teleCircuitBill.project.circuitmng.circuitInfo.service.CircuitService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @Description TODO
 * @Author lwj
 * @Date 2019-06-21
 * @Version 1.0
 **/
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class CircuitServiceTest {
    @Autowired
    private CircuitService circuitService;

    @Test
    public void testCheckCircuitIsExpired() {
        circuitService.checkCircuitIsExpired();
    }
}
