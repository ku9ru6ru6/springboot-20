package me.cai.demo.dubbo;

import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.rpc.service.GenericService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * me.cai.demo.dubbo
 *
 * @author caiguangzheng
 * date: 2019-07-25
 * mail: caiguangzheng@terminus.io
 * description:
 */
@Slf4j
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DubboSpringBootStarter.class)
public class DubboGenericServiceTest {

    private GenericService genericService;

    @Autowired
    private ApplicationConfig applicationConfig;

    @Before
    public void init() {
        // 引用远程服务
        // 该实例很重量，里面封装了所有与注册中心及服务提供方连接，请缓存
        ReferenceConfig<GenericService> reference = new ReferenceConfig<>();

        reference.setApplication(applicationConfig);

        // 弱类型接口名
        reference.setInterface("me.cai.demo.dubbo.provider.ProviderDemo");
        reference.setVersion("1.0");
        // 声明为泛化接口
        reference.setGeneric(true);

        reference.setProtocol("dubbo");

        // 用org.apache.dubbo.rpc.service.GenericService可以替代所有接口引用
        genericService = reference.get();
    }

    @Test
    public void test() {
        Object result = genericService.$invoke("sayHello", null, null);
        System.out.println(result);
    }
}
