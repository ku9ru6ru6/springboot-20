package me.cai.demo.dubbo.provider;

/**
 * me.cai.demo.dubbo.provider
 *
 * @author caiguangzheng
 * date: 2019-07-29
 * mail: caiguangzheng@terminus.io
 * description:
 */
public class ProviderDemoMock implements ProviderDemo {

    @Override
    public String sayHello() {
        return null;
    }

    @Override
    public String timeOut() {
        return null;
    }
}
