package me.cai.tk.mybatis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @author : caiguangzheng
 * @date : 2020-08-10
 * <p>.</p>
 */
@SpringBootApplication
@MapperScan(basePackages = {"me.cai.tk.mybatis.mapper"})
public class TkMybatisStarter {
    public static void main(String[] args) {
        SpringApplication.run(TkMybatisStarter.class, args);
    }

}
