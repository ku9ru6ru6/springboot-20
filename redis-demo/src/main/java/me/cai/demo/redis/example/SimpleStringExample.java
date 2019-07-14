package me.cai.demo.redis.example;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * me.cai.demo.redis.example
 *
 * @author caiguangzheng
 * date: 2019-07-13
 * mail: caiguangzheng@terminus.io
 * description:
 */

@Slf4j
@Component
public class SimpleStringExample implements Example<String> {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public void setString(String key, String value) {
        stringRedisTemplate.opsForValue().set(key, value);
    }

    @Override
    public String getString(String key) {
        return stringRedisTemplate.opsForValue().get(key);
    }

    @Override
    public void setHash(String key, String hashKey, String hashValue) {
        stringRedisTemplate.opsForHash().put(key, hashKey, hashValue);
    }

    @Override
    public String getHash(String key, String hashKey) {
        Object value = stringRedisTemplate.opsForHash().get(key, hashKey);
        if (Objects.isNull(value)) {
            return null;
        }
        return value.toString();
    }

    @Override
    public void setList(String key, String[] values) {
        stringRedisTemplate.opsForList().leftPushAll(key, values);
    }

    @Override
    public String getListFirst(String key) {
        return stringRedisTemplate.opsForList().rightPop(key);
    }

    @Override
    public String[] getListAll(String key) {
        Long len = stringRedisTemplate.opsForList().size(key);
        if (Objects.isNull(len)) {
            return null;
        }
        List<String> result = stringRedisTemplate.opsForList().range(key, 0, len);
        if (CollectionUtils.isEmpty(result)) {
            return new String[0];
        }
        return result.toArray(new String[0]);
    }

    @Override
    public void setSet(String key, String[] values) {
        stringRedisTemplate.opsForSet().add(key, values);
    }

    @Override
    public boolean ifContains(String key, String targetValue) {
        Boolean result = stringRedisTemplate.opsForSet().isMember(key, targetValue);
        return Objects.nonNull(result) ? result : false;
    }


}
