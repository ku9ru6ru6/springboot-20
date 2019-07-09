package me.cai.demo.springboot20.mybatis.mapper.first;

import me.cai.demo.springboot20.mybatis.model.City;

/**
 * me.cai.demo.springboot20.mybatis.mapper.first
 *
 * @author caiguangzheng
 * date: 2019-07-09
 * mail: caiguangzheng@terminus.io
 * description:
 */
public interface FirstCityMapper {

    int insert(City city);

    City selectByPrimaryKey(Long id);
}
