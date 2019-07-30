package me.cai.demo.zk;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * me.cai.demo.zk
 *
 * @author caiguangzheng
 * date: 2019-07-30
 * mail: caiguangzheng@terminus.io
 * description:
 */
@ConfigurationProperties(prefix = "me.zk")
public class ZKProperties {

    private String address;

    private int sessionTimeoutMs;

    private int connectionTimeoutMs;

    private RetryPolicy retryPolicy;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getSessionTimeoutMs() {
        return sessionTimeoutMs;
    }

    public void setSessionTimeoutMs(int sessionTimeoutMs) {
        this.sessionTimeoutMs = sessionTimeoutMs;
    }

    public int getConnectionTimeoutMs() {
        return connectionTimeoutMs;
    }

    public void setConnectionTimeoutMs(int connectionTimeoutMs) {
        this.connectionTimeoutMs = connectionTimeoutMs;
    }

    public RetryPolicy getRetryPolicy() {
        return retryPolicy;
    }

    public void setRetryPolicy(RetryPolicy retryPolicy) {
        this.retryPolicy = retryPolicy;
    }

    public static class RetryPolicy {
        private int baseSleepTimeMs;

        private int maxRetries;

        public int getBaseSleepTimeMs() {
            return baseSleepTimeMs;
        }

        public void setBaseSleepTimeMs(int baseSleepTimeMs) {
            this.baseSleepTimeMs = baseSleepTimeMs;
        }

        public int getMaxRetries() {
            return maxRetries;
        }

        public void setMaxRetries(int maxRetries) {
            this.maxRetries = maxRetries;
        }
    }
}
