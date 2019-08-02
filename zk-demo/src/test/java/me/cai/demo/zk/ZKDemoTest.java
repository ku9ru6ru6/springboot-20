package me.cai.demo.zk;

import com.google.common.base.Throwables;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.*;
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
import java.util.concurrent.TimeUnit;

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

    private final String PATH = "/testPath";

    @Before
    public void beforeTest() throws Exception {
        if (Objects.nonNull(client.checkExists().forPath(PATH))) {
            client.delete().deletingChildrenIfNeeded().forPath(PATH);
        }
    }

    @Test
    public void curdTest() throws Exception {

        // 创建一个永久节点
        client.create().forPath(PATH, "init".getBytes());

        // 检查节点是否存在
        Assert.assertNotNull(client.checkExists().forPath(PATH));

        // 读取数据节点数据
        byte[] data = client.getData().forPath(PATH);
        Assert.assertArrayEquals(data, "init".getBytes());

        // 读取一个节点的数据内容，同时获取到该节点的stat
        Stat stat = new Stat();
        data = client.getData().storingStatIn(stat).forPath(PATH);
        Assert.assertArrayEquals(data, "init".getBytes());

        // 更新数据
        client.setData().forPath(PATH, "data".getBytes());
        data = client.getData().forPath(PATH);
        Assert.assertArrayEquals(data, "data".getBytes());

        // 删除节点
        client.delete().forPath(PATH);
        Assert.assertNull(client.checkExists().forPath(PATH));

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
    }

    /**
     * CuratorFramework的实例包含inTransaction( )接口方法，
     * 调用此方法开启一个ZooKeeper事务.
     * 可以复合create, setData, check, and/or delete 等操作然后调用commit()作为一个原子操作提交。
     */
    @Test
    public void transactionTest() throws Exception {
        client.inTransaction()
                .create().withMode(CreateMode.EPHEMERAL).forPath(PATH, "data".getBytes())
                .and().setData().forPath(PATH, "data2".getBytes())
                .and().commit();

        Stat stat = client.checkExists().forPath(PATH);
        Assert.assertNotNull(stat);

        byte[] data = client.getData().forPath(PATH);
        Assert.assertArrayEquals(data, "data2".getBytes());
    }

    /**
     * Path Cache用来监控一个ZNode的子节点. 当一个子节点增加， 更新，删除时，
     * Path Cache会改变它的状态， 会包含最新的子节点， 子节点的数据和状态，
     * 而状态的更变将通过PathChildrenCacheListener通知。
     * 想使用cache，必须调用它的start方法，使用完后调用close方法。
     * <p>
     * 示例中的 `TimeUnit.SECONDS.sleep(1);` 不能注释
     * 注释后事件监听的触发次数会不全，这可能与PathCache的实现原理有关，不能太过频繁的触发事件！
     */
    @Test
    public void testPathCacheListener() {
        try (PathChildrenCache cache = new PathChildrenCache(client, PATH, true)) {
            cache.start();

            PathChildrenCacheListener cacheListener = ((client, event) -> {
                log.debug("事件类型:{}", event.getType());
                if (Objects.nonNull(event.getData())) {
                    log.debug("节点数据: {} = {}", event.getData().getPath(), new String(event.getData().getData()));
                }
            });
            cache.getListenable().addListener(cacheListener);

            client.create().creatingParentContainersIfNeeded().forPath(PATH + "/test01", "01".getBytes());
            TimeUnit.SECONDS.sleep(1);
            client.create().creatingParentContainersIfNeeded().forPath(PATH + "/test02", "02".getBytes());
            TimeUnit.SECONDS.sleep(1);
            client.setData().forPath(PATH + "/test01", "01_v2".getBytes());
            TimeUnit.SECONDS.sleep(1);
            for (ChildData data : cache.getCurrentData()) {
                log.debug("getCurrentData: {} = {}", data.getPath(), new String(data.getData()));
            }
            TimeUnit.SECONDS.sleep(1);
        } catch (Exception e) {
            log.error("testPathCacheListener get Exception:{}", Throwables.getStackTraceAsString(e));
            Assert.fail(e.getMessage());
        }
    }

    /**
     * Node Cache与Path Cache类似，Node Cache只是监听某一个特定的节点。
     */
    @Test
    public void testNodeCacheListener() {
        try (NodeCache cache = new NodeCache(client, PATH, false)) {

            NodeCacheListener cacheListener = (() -> {
                ChildData data = cache.getCurrentData();
                if (Objects.nonNull(data)) {
                    log.debug("节点数据: {} = {}", data.getPath(), new String(data.getData()));
                } else {
                    log.debug("节点被删除！");
                }
            });

            cache.getListenable().addListener(cacheListener);
            cache.start();

            client.create().forPath(PATH, "01".getBytes());
            TimeUnit.SECONDS.sleep(1);

            client.setData().forPath(PATH, "01_v2".getBytes());
            TimeUnit.SECONDS.sleep(1);

            client.delete().forPath(PATH);
            TimeUnit.SECONDS.sleep(1);

        } catch (Exception e) {
            log.error("testNodeCacheListener get Exception:{}", Throwables.getStackTraceAsString(e));
            Assert.fail(e.getMessage());
        }
    }

    /**
     * Tree Cache可以监控整个树上的所有节点，类似于PathCache和NodeCache的组合
     */
    @Test
    public void testTreeCacheListener() {
        try (TreeCache cache = new TreeCache(client, PATH)) {
            TreeCacheListener cacheListener = (client, event) -> {
                if (Objects.nonNull(event) && Objects.nonNull(event.getData())) {
                    log.debug("事件类型: {} | 路径: {}", event.getType(), event.getData().getPath());
                }
            };

            cache.getListenable().addListener(cacheListener);
            cache.start();

            client.create().forPath(PATH);
            TimeUnit.SECONDS.sleep(1);

            client.setData().forPath(PATH, "01".getBytes());
            TimeUnit.SECONDS.sleep(1);

            client.setData().forPath(PATH, "02".getBytes());
            TimeUnit.SECONDS.sleep(1);

            client.create().creatingParentsIfNeeded().forPath(PATH + "/child", "child".getBytes());
            TimeUnit.SECONDS.sleep(1);

            client.delete().deletingChildrenIfNeeded().forPath(PATH);
            TimeUnit.SECONDS.sleep(1);

        } catch (Exception e) {
            log.error("testTreeCacheListener get Exception:{}", Throwables.getStackTraceAsString(e));
            Assert.fail(e.getMessage());
        }
    }
}
