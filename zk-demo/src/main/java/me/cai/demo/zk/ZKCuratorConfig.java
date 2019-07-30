package me.cai.demo.zk;

import lombok.extern.slf4j.Slf4j;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * me.cai.demo.zk
 *
 * @author caiguangzheng
 * date: 2019-07-30
 * mail: caiguangzheng@terminus.io
 * description:
 */
@Slf4j
@Configuration
@EnableConfigurationProperties(ZKProperties.class)
public class ZKCuratorConfig {

    private ZKProperties zkProperties;

    @Autowired
    public ZKCuratorConfig(ZKProperties zkProperties) {
        this.zkProperties = zkProperties;
    }

    @Bean(initMethod = "start", destroyMethod = "close")
    public CuratorFramework curatorFramework() {
        ZKProperties.RetryPolicy retryPolicyProperties = zkProperties.getRetryPolicy();
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(retryPolicyProperties.getBaseSleepTimeMs(), retryPolicyProperties.getMaxRetries());

        return CuratorFrameworkFactory.builder()
                .connectString(zkProperties.getAddress())
                .sessionTimeoutMs(zkProperties.getSessionTimeoutMs())
                .connectionTimeoutMs(zkProperties.getConnectionTimeoutMs())
                .retryPolicy(retryPolicy)
                .namespace("test")
                .build();
    }

}
