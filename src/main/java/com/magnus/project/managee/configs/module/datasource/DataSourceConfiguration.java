package com.magnus.project.managee.configs.module.datasource;

import com.alibaba.druid.pool.DruidDataSource;
import com.magnus.project.managee.support.dicts.ConfigDict;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionManager;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@PropertySource("classpath:properties/datasource.properties")
public class DataSourceConfiguration {

    @Value("${datasource.url}")
    public String url;
    @Value("${datasource.username}")
    public String user;
    @Value("${datasource.driver}")
    public String driver;
    @Value("${datasource.password}")
    public String password;
    @Value("${druid.max-idle}")
    public String maxIdle;
    @Value("${druid.max-active}")
    public String maxActive;
    @Value("${druid.initial-size}")
    public String initialSize;

    @Bean
    public JdbcTemplate jdbcTemplate() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        jdbcTemplate.setDataSource(dataSource());
        return jdbcTemplate;
    }

    /**
     * 配置德鲁伊数据源
     * @return
     */
    @Bean
    public DataSource dataSource() {
        DruidDataSource druidDataSource = new DruidDataSource();
        Properties properties = new Properties();
        properties.put(ConfigDict.DRUID_URL.getValue(), url);
        properties.put(ConfigDict.DRUID_USERNAME.getValue(), user);
        properties.put(ConfigDict.DRUID_PASSWORD.getValue(), password);
        properties.put(ConfigDict.DRUID_MAX_IDLE.getValue(), maxIdle);
        properties.put(ConfigDict.DRUID_MAX_ACTIVE.getValue(), maxActive);
        properties.put(ConfigDict.DRUID_INITIAL_SIZE.getValue(), initialSize);
        druidDataSource.configFromPropety(properties);
        return druidDataSource;
    }

    @Bean
    public TransactionManager transactionManager() {
        return new DataSourceTransactionManager(dataSource());
    }
}
