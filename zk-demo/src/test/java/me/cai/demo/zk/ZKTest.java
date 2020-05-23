package me.cai.demo.zk;

import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.CreateMode;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Objects;

/**
 * me.cai.demo.zk
 *
 * @author caiguangzheng
 * date: 2019-07-31
 * mail: caiguangzheng@terminus.io
 * description:
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ZkSpringBootStarter.class)
public class ZKTest {

    @Autowired
    private CuratorFramework client;

    private final String PARENT_PATH = "/terminus/flow/snowflake";

    @Before
    public void beforeTest() throws Exception {
        if (Objects.nonNull(client.checkExists().forPath(PARENT_PATH))) {
            client.delete().deletingChildrenIfNeeded().forPath(PARENT_PATH);
        }
    }

    @Test
    public void create() {
        String target = null;
        try {
            for (int i = 0; i < 5; i++) {
                String s = client.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT_SEQUENTIAL).forPath(PARENT_PATH + "/son");
                log.info(s);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.info("end");

        try {
                List<String> strings = client.getChildren().forPath("/terminus/flow");
            for (int i = 0; i < strings.size(); i++) {
                if (Objects.equals(target, strings.get(i))) {
                    log.info("find i:{}", i);
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
