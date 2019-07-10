package me.cai.demo.mybatis.mapper.first;

import me.cai.demo.mybatis.model.City;
import org.apache.ibatis.annotations.Select;

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

    @Select("SELECT name FROM city WHERE id = #{id}")
    City selectNameByPrimaryKey(Long id);
}
