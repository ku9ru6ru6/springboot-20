package me.cai.tk.mybatis.model;

import lombok.Data;
import me.cai.tk.mybatis.enums.CountryType;
import me.cai.tk.mybatis.enums.MyCountryEnumTypeHandler;
import org.apache.ibatis.type.JdbcType;
import tk.mybatis.mapper.annotation.ColumnType;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author : caiguangzheng
 * @date : 2020-08-10
 * <p>.</p>
 */
@Data
@Table(name = "country")
public class Country {

    @Id
    @KeySql(useGeneratedKeys = true)
    private Integer id;

    @Column(name = "country_name")
    private String  countryName;

    @Column(name = "country_code")
    private String  countryCode;

    @Column(name = "country_type")
    @ColumnType(jdbcType = JdbcType.VARCHAR, typeHandler = MyCountryEnumTypeHandler.class)
    private CountryType countryType;
}
