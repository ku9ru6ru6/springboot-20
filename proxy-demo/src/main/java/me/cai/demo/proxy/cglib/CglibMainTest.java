package me.cai.demo.proxy.cglib;

import lombok.extern.slf4j.Slf4j;

/**
 * me.cai.demo.proxy.cglib
 *
 * @author caiguangzheng
 * date: 2019-08-19
 * mail: caiguangzheng@terminus.io
 * description:
 */
@Slf4j
public class CglibMainTest {

    public static void main(String[] args) {
        UserDao userDao = new UserDao();
        log.debug(userDao.getClass().toString());

        UserDao proxy = new ProxyFactory<>(userDao).getProxyInstance();
        log.debug(proxy.getClass().toString());

        proxy.save();
    }
}
