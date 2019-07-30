package me.cai.demo.mybatis;

import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.annotation.MapperScans;
import org.mybatis.spring.boot.autoconfigure.SpringBootVFS;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

/**
 * me.cai.demo.springboot20.mybatis
 *
 * @author caiguangzheng
 * date: 2019-07-09
 * mail: caiguangzheng@terminus.io
 * description:
 */
@Configuration
@MapperScans({
        @MapperScan(basePackages = {"me.cai.demo.mybatis.mapper.first"}, sqlSessionFactoryRef = "firstSqlSessionFactory"),
        @MapperScan(basePackages = {"me.cai.demo.mybatis.mapper.second"}, sqlSessionFactoryRef = "secondSqlSessionFactory")
})
@EnableTransactionManagement
public class MybatisConfig {

    @Bean
    @Primary
    @ConfigurationProperties("hikari.first")
    public DataSource firstDataSource() {
        return new HikariDataSource();
    }

    @Bean
    @Primary
    public DataSourceTransactionManager firstDataSourceTransactionManager(@Qualifier("firstDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean
    @Primary
    public SqlSessionFactory firstSqlSessionFactory(@Qualifier("firstDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean factory = new SqlSessionFactoryBean();
        factory.setDataSource(dataSource);
        factory.setVfs(SpringBootVFS.class);
        factory.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper/first/*Mapper.xml"));
        return factory.getObject();
    }


    @Bean
    @ConfigurationProperties("hikari.second")
    public DataSource secondDataSource() {
        return new HikariDataSource();
    }

    @Bean
    public DataSourceTransactionManager secondDataSourceTransactionManager(@Qualifier("secondDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean
    public SqlSessionFactory secondSqlSessionFactory(@Qualifier("secondDataSource") DataSource dataSource,
                                                     @Qualifier("secondDataSourceTransactionManager") DataSourceTransactionManager transactionManager) throws Exception {
        SqlSessionFactoryBean factory = new SqlSessionFactoryBean();
        factory.setDataSource(dataSource);
        factory.setVfs(SpringBootVFS.class);
        factory.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper/second/*Mapper.xml"));
        return factory.getObject();
    }

}
