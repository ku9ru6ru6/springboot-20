package me.cai.demo.proxy.cglib;

import lombok.extern.slf4j.Slf4j;

/**
 * me.cai.demo.proxy
 *
 * @author caiguangzheng
 * date: 2019-08-19
 * mail: caiguangzheng@terminus.io
 * description:
 */
@Slf4j
public class UserDao {
    public void save() {
        log.debug("保存数据");
    }
}
