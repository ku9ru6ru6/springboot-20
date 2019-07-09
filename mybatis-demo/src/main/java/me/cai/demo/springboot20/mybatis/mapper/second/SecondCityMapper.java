package me.cai.demo.springboot20.mybatis.mapper.second;

import me.cai.demo.springboot20.mybatis.model.City;

/**
 * me.cai.demo.springboot20.mybatis.mapper.second
 *
 * @author caiguangzheng
 * date: 2019-07-09
 * mail: caiguangzheng@terminus.io
 * description:
 */
public interface SecondCityMapper {

    int create(City city);

    City selectByPrimaryKey(Long id);
}
