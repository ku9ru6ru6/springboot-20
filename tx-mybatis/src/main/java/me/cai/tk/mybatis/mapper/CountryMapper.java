package me.cai.tk.mybatis.mapper;

import me.cai.tk.mybatis.model.Country;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

/**
 * @author : caiguangzheng
 * @date : 2020-08-10
 * <p>.</p>
 */
public interface CountryMapper extends Mapper<Country> {

    @Select("select `id`, `country_name` as countryName, " +
            "`country_code` as countryCode, `country_type` as countryType from country where `country_name` = #{countryName}")
    Country selectByCountryName(String countryName);
}
