package me.cai.demo.redis.controller;

import lombok.extern.slf4j.Slf4j;
import me.cai.demo.redis.model.Person;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

/**
 * me.cai.demo.redis.controller
 *
 * @author caiguangzheng
 * date: 2020/4/23
 * mail: caiguangzheng@terminus.io
 * description:
 */
@Slf4j
@RestController
@RequestMapping("/api/person")
@CacheConfig(cacheNames = "person")
public class CacheDemoController {

    @GetMapping("/{id}")
    @Cacheable(key = "#id")
    public Person getPerson(@PathVariable Integer id) {
        Person person = new Person();
        person.setId(id);
        person.setName("cai");
        person.setAge(25);
        person.setPhoneNumber("18368493646");
        return person;
    }

    @DeleteMapping("/{id}")
    @CacheEvict(key = "#id")
    public void delPerson(@PathVariable Integer id) { }

}
