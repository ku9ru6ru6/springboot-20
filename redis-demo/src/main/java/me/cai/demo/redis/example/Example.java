package me.cai.demo.redis.example;

/**
 * me.cai.demo.redis.example
 *
 * @author caiguangzheng
 * date: 2019-07-13
 * mail: caiguangzheng@terminus.io
 * description:
 */
public interface Example<T> {

    void setString(String key, T value);

    T getString(String key);

    void setHash(String key, String hashKey, T hashValue);

    T getHash(String key, String hashKey);

    void setList(String key, T[] values);

    T getListFirst(String key);

    T[] getListAll(String key);

    void setSet(String key, T[] values);

    boolean ifContains(String key, T targetValue);

}
