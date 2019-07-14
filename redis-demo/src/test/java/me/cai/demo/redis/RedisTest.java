package me.cai.demo.redis;

import lombok.extern.slf4j.Slf4j;
import me.cai.demo.redis.example.SimpleStringExample;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Collections;

/**
 * me.cai.demo.redis
 *
 * @author caiguangzheng
 * date: 2019-07-12
 * mail: caiguangzheng@terminus.io
 * description:
 */
@Slf4j
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RedisSpringBootStarter.class)
public class RedisTest {

    @Autowired
    private SimpleStringExample simpleStringExample;

    @Test
    public void testSimpleStringExample() {
        String key = "stringTest";
        String value = "stringValue";
        simpleStringExample.setString(key, value);
        Assert.assertEquals(simpleStringExample.getString(key), value);

        key = "hashTest";
        String hashKey = "name";
        value = "Bob";
        simpleStringExample.setHash(key, hashKey, value);
        Assert.assertEquals(simpleStringExample.getHash(key, hashKey), value);

        key = "listTest";
        String[] listValue = new String[]{"1", "2", "3", "4"};
        simpleStringExample.setList(key, listValue);
        Collections.reverse(Arrays.asList(listValue));
        Assert.assertArrayEquals(simpleStringExample.getListAll(key), listValue);

        key = "setTest";
        listValue = new String[]{"A", "B", "C", "D"};
        String targetValue = "Z";
        simpleStringExample.setSet(key, listValue);
        Assert.assertFalse(simpleStringExample.ifContains(key, targetValue));
        targetValue = "a";
        Assert.assertFalse(simpleStringExample.ifContains(key, targetValue));
    }
}
