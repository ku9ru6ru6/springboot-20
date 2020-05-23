package me.cai.demo.zk;

import com.google.common.base.Throwables;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Objects;

/**
 * io.terminus.flow.activiti.base
 *
 * @author caiguangzheng
 * date: 2020/5/22
 * mail: caiguangzheng@terminus.io
 * description: 雪花算法id生成器
 * 共51位，32bit秒级时间戳+3bit机器标+16bit自增
 */
@Slf4j
@Component
public class SnowflakeIdGenerator {

    @Autowired
    private CuratorFramework zkClient;

    private final String PARENT_PATH = "/terminus/flow/snowflake";

    private final long OFFSET = LocalDate.of(2000, 1, 1).atStartOfDay(ZoneId.of("Z")).toEpochSecond();

    /**
     * 16位自增id最大值，用于判断自增是否超过界限
     * 如果超过界限，向下1秒借位
     */
    private final long MAX_NEXT = 0b1111_1111_1111_1111L;

    /**
     * 机器id，通过注册zk顺序节点自动获取
     * 最多支持8台机器 (0~7) 机器位只有3位
     */
    private volatile int workId = 0;

    private long offset = 0;

    private long lastEpoch = 0;

    /**
     * bean初始化时检测父节点是否存在，如果存在则递归删除。
     * 创建循序节点，获取workId，注册父节点事件监听器。
     */
    @PostConstruct
    public void init() throws Exception {
        if (Objects.nonNull(zkClient.checkExists().forPath(PARENT_PATH))) {
            zkClient.delete().deletingChildrenIfNeeded().forPath(PARENT_PATH);
        }
        String zNodePath = zkClient.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT_SEQUENTIAL).forPath(PARENT_PATH + "/son");
        setWorkId(zNodePath);
        zkClient.checkExists().usingWatcher(new SnowflakeIdGeneratorWatcher()).forPath(zNodePath);
    }

    public String getNextId() {
        return nextId(System.currentTimeMillis() / 1000) + "";
    }

    /**
     * zk节点的监听器，监听父节点删除事件。当父节点被删除时，说明有机器重启，
     * 所有实例重新创建顺序节点，更新workId。以保证workId不重复。
     */
     private class SnowflakeIdGeneratorWatcher implements Watcher {
        @Override
        public void process(WatchedEvent watchedEvent) {
            if (watchedEvent.getType().equals(Event.EventType.NodeDeleted)) {
                try {
                    String zNodePath = zkClient.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT_SEQUENTIAL).forPath(PARENT_PATH + "/son");
                    zkClient.getData().usingWatcher(new SnowflakeIdGeneratorWatcher()).forPath(PARENT_PATH);
                    setWorkId(zNodePath);
                } catch (Exception e) {
                    log.error("{}", Throwables.getStackTraceAsString(e));
                }
            }
        }
    }

    private synchronized long nextId(long epochSecond) {
        if (epochSecond < lastEpoch) {
            if (log.isWarnEnabled()) {
                log.warn("clock is back: " + epochSecond + " from previous:" + lastEpoch);
            }
            epochSecond = lastEpoch;
        }
        if (lastEpoch != epochSecond) {
            lastEpoch = epochSecond;
            offset = 0;
        }
        offset++;
        long next = offset & MAX_NEXT;
        if (next == 0) {
            if (log.isWarnEnabled()) {
                log.warn("maximum id reached in 1 second in epoch: " + epochSecond);
            }
            return nextId(epochSecond + 1);
        }
        return generateId(epochSecond, next, workId);
    }

    private long generateId(long epochSecond, long next, long workId) {
        return ((epochSecond - OFFSET) << 19) | (workId << 16) | next;
    }

    public void setWorkId(String zNodePath) {
        String[] split = zNodePath.split("/");
        String lastNode = split[split.length - 1];
        String num = lastNode.substring(lastNode.length() - 10);
        this.workId = Integer.parseInt(num);
        if (log.isInfoEnabled()) {
            log.info("set workId: {}", this.workId);
        }
    }
}
