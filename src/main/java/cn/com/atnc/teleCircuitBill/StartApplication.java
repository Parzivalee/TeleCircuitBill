package cn.com.atnc.teleCircuitBill;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 启动程序
 * 
 */
@SpringBootApplication
@EnableTransactionManagement
@MapperScan({"cn.com.atnc.teleCircuitBill.project.*.mapper",
                "cn.com.atnc.teleCircuitBill.project.*.*.mapper",
                "cn.com.atnc.teleCircuitBill.project.*.*.*.mapper"})
public class StartApplication {
    public static void main(String[] args) {
        // System.setProperty("spring.devtools.restart.enabled", "false");
        SpringApplication.run(StartApplication.class, args);
    }
}