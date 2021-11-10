package com.magnus.project.managee.configs.module.mybatis.configs;

import com.sun.tools.jconsole.JConsoleContext;
import org.apache.ibatis.cache.impl.PerpetualCache;
import org.apache.ibatis.logging.log4j2.Log4j2Impl;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.transaction.SpringManagedTransactionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.core.RedisTemplate;

import javax.sql.DataSource;

@Configuration
@MapperScan("com.magnus.project.managee.work.mapper")
public class MybatisConfiguration {

    @Autowired
    public DataSource dataSource;

    @Bean
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        // mybatis 配置类
        org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
        // Mybatis构建类
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        SpringManagedTransactionFactory springManagedTransactionFactory = new SpringManagedTransactionFactory();
        Environment environment = new Environment("1", springManagedTransactionFactory, dataSource);
        configuration.setEnvironment(environment);
        configuration.setLogImpl(Log4j2Impl.class);
        configuration.setCacheEnabled(true);
        sqlSessionFactoryBean.setConfiguration(configuration);
        sqlSessionFactoryBean.setPlugins();
        return sqlSessionFactoryBean.getObject();
    }

    @Bean
    @Primary
    public SqlSession sqlSession() throws Exception {
        SqlSessionTemplate sqlSessionTemplate = new SqlSessionTemplate(sqlSessionFactory(), ExecutorType.SIMPLE);
        return sqlSessionTemplate;
    }

    @Bean
    @Qualifier("batch")
    public SqlSession sqlSessionBatch() throws Exception {
        SqlSessionTemplate sqlSessionTemplate = new SqlSessionTemplate(sqlSessionFactory(), ExecutorType.BATCH);
        return sqlSessionTemplate;
    }
}
