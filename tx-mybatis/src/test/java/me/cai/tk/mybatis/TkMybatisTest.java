package me.cai.tk.mybatis;

import lombok.extern.slf4j.Slf4j;
import me.cai.tk.mybatis.enums.CountryType;
import me.cai.tk.mybatis.mapper.CountryMapper;
import me.cai.tk.mybatis.model.Country;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author : caiguangzheng
 * @date : 2020-08-10
 * <p>.</p>
 */
@Slf4j
@Rollback
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TkMybatisStarter.class)
public class TkMybatisTest {

    @Autowired
    private CountryMapper countryMapper;

    private Integer id;

    @Before
    public void init() {
        Country country = new Country();
        country.setCountryName("name");
        country.setCountryCode("code");
        country.setCountryType(CountryType.SOVEREIGN);
        countryMapper.insert(country);
        this.id = country.getId();
    }

    @After
    public void destroy() {
        if (null != id) {
            countryMapper.deleteByPrimaryKey(this.id);
        }
    }

    @Test
    public void testSelectByPrimaryKey() {
        Country country = countryMapper.selectByPrimaryKey(this.id);
        Assert.assertNotNull(country);
        Assert.assertEquals(country.getCountryCode(), "code");
        Assert.assertEquals(country.getCountryName(), "name");
        log.info("country:{}", country);
    }

    @Test
    public void testSelectByCountryName() {
        Country country = countryMapper.selectByCountryName("name");
        Assert.assertNotNull(country);
        Assert.assertEquals(country.getCountryCode(), "code");
        Assert.assertEquals(country.getCountryName(), "name");
        log.info("country:{}", country);
    }
}
