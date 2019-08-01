package me.cai.demo.zk;

import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;
import org.junit.Assert;
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
public class ZKDemoTest {

    @Autowired
    private CuratorFramework client;

    @Before
    public void beforeTest() throws Exception {
        if (Objects.nonNull(client.checkExists().forPath("/persistent"))) {
            client.delete().deletingChildrenIfNeeded().forPath("/persistent");
        }
        if (Objects.nonNull(client.checkExists().forPath("/parent-1"))) {
            client.delete().deletingChildrenIfNeeded().forPath("/parent-1");
        }
    }

    @Test
    public void curdTest() throws Exception {

        // 创建一个永久节点
        client.create().forPath("/persistent", "init".getBytes());

        // 检查节点是否存在
        Assert.assertNotNull(client.checkExists().forPath("/persistent"));

        // 读取数据节点数据
        byte[] data = client.getData().forPath("/persistent");
        Assert.assertArrayEquals(data, "init".getBytes());

        // 读取一个节点的数据内容，同时获取到该节点的stat
        Stat stat = new Stat();
        data = client.getData().storingStatIn(stat).forPath("/persistent");
        Assert.assertArrayEquals(data, "init".getBytes());

        // 更新数据
        client.setData().forPath("/persistent","data".getBytes());
        data = client.getData().forPath("/persistent");
        Assert.assertArrayEquals(data, "data".getBytes());

        // 删除节点
        client.delete().forPath("/persistent");
        Assert.assertNull(client.checkExists().forPath("/persistent"));

        // 递归创建父节点，有一点需要注意，就是下边设置的临时节点只是针对son节点
        // parent-1 和 parent-2 节点都是永久的
        client.create()
                .creatingParentContainersIfNeeded()
                .withMode(CreateMode.EPHEMERAL)
                .forPath("/parent-1/parent-2/son");
        Assert.assertNotNull(client.checkExists().forPath("/parent-1"));
        Assert.assertNotNull(client.checkExists().forPath("/parent-1/parent-2"));
        Assert.assertNotNull(client.checkExists().forPath("/parent-1/parent-2/son"));

        // 获取某个节点的所有子节点路径
        List<String> children = client.getChildren().forPath("/parent-1");
        log.debug("get /parent-1 children:{}", children);

        // 创建一个临时节点，内容为空
        client.create().withMode(CreateMode.EPHEMERAL).forPath("/ephemeral");

        // 检查节点是否存在
        Assert.assertNotNull(client.checkExists().forPath("/ephemeral"));
    }

    /**
     * CuratorFramework的实例包含inTransaction( )接口方法，
     * 调用此方法开启一个ZooKeeper事务.
     * 可以复合create, setData, check, and/or delete 等操作然后调用commit()作为一个原子操作提交。
     */
    @Test
    public void transactionTest() throws Exception {
        client.inTransaction()
                .create().withMode(CreateMode.EPHEMERAL).forPath("/path", "data".getBytes())
                .and().setData().forPath("/path", "data2".getBytes())
                .and().commit();

        Stat stat = client.checkExists().forPath("/path");
        Assert.assertNotNull(stat);

        byte[] data = client.getData().forPath("/path");
        Assert.assertArrayEquals(data, "data2".getBytes());
    }
}
