package me.cai.demo.dubbo.consumer;

import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.utils.ReferenceConfigCache;
import org.apache.dubbo.rpc.service.GenericService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * me.cai.demo.dubbo.consumer
 *
 * @author caiguangzheng
 * date: 2020/4/21
 * mail: caiguangzheng@terminus.io
 * description:
 */
@Slf4j
@Component
public class GenericServiceDemo implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
        initService();
    }

    public void initService() {
        ReferenceConfig<GenericService> reference = new ReferenceConfig<>();
        reference.setInterface("me.cai.demo.dubbo.provider.ProviderDemo");
        reference.setVersion("1.0");
        reference.setGeneric(true);
        reference.setRetries(0);
        ReferenceConfigCache cache = ReferenceConfigCache.getCache();
        GenericService genericService = cache.get(reference);
        Object result = genericService.$invoke("sayHello", null, null);
        log.info("GenericService#$invoke get result:{}", result);
    }
}
