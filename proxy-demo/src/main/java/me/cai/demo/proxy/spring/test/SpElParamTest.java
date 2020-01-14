package me.cai.demo.proxy.spring.test;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * me.cai.demo.proxy.spring.test
 *
 * @author caiguangzheng
 * date: 2020/1/14
 * mail: caiguangzheng@terminus.io
 * description:
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface SpElParamTest {

    String spel();
}
