package me.cai.tk.mybatis.enums;

import lombok.NoArgsConstructor;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author : caiguangzheng
 * @date : 2020-08-10
 * <p>.</p>
 */
@NoArgsConstructor
public class MyCountryEnumTypeHandler extends BaseTypeHandler<CountryType> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, CountryType parameter, JdbcType jdbcType) throws SQLException {
        if (jdbcType == null) {
            ps.setString(i, parameter.name());
        } else {
            ps.setObject(i, parameter.name(), jdbcType.TYPE_CODE);
        }
    }

    @Override
    public CountryType getNullableResult(ResultSet rs, String columnName) throws SQLException {
        String s = rs.getString(columnName);
        return s == null ? null : Enum.valueOf(CountryType.class, s);
    }

    @Override
    public CountryType getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        String s = rs.getString(columnIndex);
        return s == null ? null : Enum.valueOf(CountryType.class, s);
    }

    @Override
    public CountryType getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        String s = cs.getString(columnIndex);
        return s == null ? null : Enum.valueOf(CountryType.class, s);
    }
}
