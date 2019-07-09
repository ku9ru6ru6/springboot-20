package me.cai.demo;

import lombok.extern.slf4j.Slf4j;
import me.cai.demo.springboot20.mybatis.SpringBootStarter;
import me.cai.demo.springboot20.mybatis.mapper.first.FirstCityMapper;
import me.cai.demo.springboot20.mybatis.mapper.second.SecondCityMapper;
import me.cai.demo.springboot20.mybatis.model.City;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * me.cai.demo
 *
 * @author caiguangzheng
 * date: 2019-07-09
 * mail: caiguangzheng@terminus.io
 * description:
 */
@Slf4j
@Rollback(value = false)
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringBootStarter.class)
public class TestApplication {

    @Autowired
    private FirstCityMapper firstCityMapper;

    @Autowired
    private SecondCityMapper secondCityMapper;

    @Test
    @Transactional(value = "firstDataSourceTransactionManager", rollbackFor = Exception.class)
    public void testFirstCityMapper() {
        City city = new City("first", "first", "first");
        int result = firstCityMapper.insert(city);
        log.info("testFirstCityMapper inster result:{}", result);

        City city_second = firstCityMapper.selectByPrimaryKey(city.getId());
        log.info("testFirstCityMapper selectByPrimaryKey result:{}", city_second);
    }

    @Test
    @Transactional(value = "secondDataSourceTransactionManager", rollbackFor = Exception.class)
    public void testSecondCityMapper() {
        City city = new City("second", "second", "second");
        int result = secondCityMapper.create(city);
        log.info("testSecondCityMapper inster result:{}", result);

        City city_second = secondCityMapper.selectByPrimaryKey(city.getId());
        log.info("testSecondCityMapper selectByPrimaryKey result:{}", city_second);
    }

}
