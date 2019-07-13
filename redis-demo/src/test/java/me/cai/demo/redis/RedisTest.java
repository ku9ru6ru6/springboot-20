package me.cai.demo.redis;

import lombok.extern.slf4j.Slf4j;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

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
@SpringBootTest(classes = SpringBootStarter.class)
public class RedisTest {


}
