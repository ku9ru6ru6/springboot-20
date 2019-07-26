package me.cai.demo.dubbo.consumer;

import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.utils.ReferenceConfigCache;
import org.apache.dubbo.rpc.service.GenericService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * me.cai.demo.dubbo.consumer
 *
 * @author caiguangzheng
 * date: 2019-07-25
 * mail: caiguangzheng@terminus.io
 * description:
 */
@Slf4j
@RestController
@RequestMapping("/me/test")
public class GenericServiceController {

    @Autowired
    private ApplicationConfig applicationConfig;

    private GenericService getGenericService(String interfaceName, String address, String version) {
        ReferenceConfig<GenericService> referenceConfig = new ReferenceConfig<>();
        referenceConfig.setApplication(applicationConfig);
        referenceConfig.setRegistry(getRegistryConfig(address));
        referenceConfig.setInterface(interfaceName);
        referenceConfig.setVersion(version);
        referenceConfig.setGeneric(true);
        referenceConfig.setRetries(1);
        ReferenceConfigCache cache = ReferenceConfigCache.getCache();
        return cache.get(referenceConfig);
    }

    private RegistryConfig getRegistryConfig(String address) {
        RegistryConfig registryConfig = new RegistryConfig();
        registryConfig.setAddress(address);
        return registryConfig;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Object test() {
        GenericService genericService = this.getGenericService("me.cai.provider.ServiceProvider", "zookeeper://127.0.0.1:2181", "1.0.0");
        Map<String, Object> map = new HashMap<>(10);
        map.put("name", "cai");
        map.put("bigDecimal", BigDecimal.valueOf(111));
        map.put("createdTime", new Date());
        return genericService.$invoke("createOrder", new String[]{"me.cai.provider.dto.OrderDTO"}, new Object[]{map});
    }
}
