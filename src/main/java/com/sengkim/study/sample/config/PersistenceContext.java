package com.sengkim.study.sample.config;

import java.net.URI;
import java.net.URISyntaxException;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.sengkim.study.sample.dao.typehandler.CollectionStringTypeHandler;

/**
 * @author sengkim
 * @Date Sep 8, 2015
 * @Email sengkim.it@gmail.com
 */
@Configuration
@EnableTransactionManagement
@MapperScan("com.sengkim.study.sample.dao")
public class PersistenceContext {
	
	private static final Logger LOG = LoggerFactory.getLogger(PersistenceContext.class);

    private static final String PROPERTY_NAME_DB_DRIVER = "jdbc.driver";
    private static final String PROPERTY_NAME_DB_HOST_AND_PORT = "jdbc.hostAndPort";
    private static final String PROPERTY_NAME_DB_USERNAME = "jdbc.username";
    private static final String PROPERTY_NAME_DB_PASSWORD = "jdbc.password";
    private static final String PROPERTY_NAME_DB_DATABASENAME = "jdbc.databasename";

    @Resource
    private Environment env;

//    @Bean
//    public DataSource dataSource() {
//        BasicDataSource dataSource = new BasicDataSource();
//        dataSource.setDriverClassName(env.getProperty(PROPERTY_NAME_DB_DRIVER));
//        dataSource.setUrl("jdbc:mariadb://" + env.getProperty(PROPERTY_NAME_DB_HOST_AND_PORT) + "/"
//                + env.getProperty(PROPERTY_NAME_DB_DATABASENAME) + "?zeroDateTimeBehavior=convertToNull");
//        dataSource.setUsername(env.getProperty(PROPERTY_NAME_DB_USERNAME));
//        dataSource.setPassword(env.getProperty(PROPERTY_NAME_DB_PASSWORD));
//
//        return dataSource;
//    }
    @Bean
    public DataSource dataSource() throws URISyntaxException {
    	LOG.info(">>>>>>> DATABASE_URL: {}", System.getenv("DATABASE_URL"));
    	URI dbUri = new URI(System.getenv("DATABASE_URL"));
    	
    	String username = dbUri.getUserInfo().split(":")[0];
    	String password = dbUri.getUserInfo().split(":")[1];
    	String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + ":" + dbUri.getPort() + dbUri.getPath() 
    			+ "?ssl=true&sslfactory=org.postgresql.ssl.NonValidatingFactory";
    	
        BasicDataSource dataSource = new BasicDataSource();
        //dataSource.setDriverClassName(env.getProperty(PROPERTY_NAME_DB_DRIVER));
        dataSource.setUrl(dbUrl);
        dataSource.setUsername(username);
        dataSource.setPassword(password);

        return dataSource;
    }

    @Bean
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        SqlSessionFactoryBean sqlSessionFactory = new SqlSessionFactoryBean();
        sqlSessionFactory.setDataSource(dataSource());
        sqlSessionFactory.setTypeAliasesPackage("com.sengkim.study.sample.domain");
        // sqlSessionFactory.setMapperLocations(new org.springframework.core.io.Resource[] { new ClassPathResource(
        // "/mapper/UserDao.xml") });
        sqlSessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper/*.xml"));
        sqlSessionFactory.setTypeHandlers(new CollectionStringTypeHandler[] { });
        return sqlSessionFactory.getObject();
    }

    @Bean
    public SqlSession sqlSession() throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory());
    }

    @Bean
    public DataSourceTransactionManager transactionManager() throws URISyntaxException {
        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager();
        transactionManager.setDataSource(dataSource());
        return transactionManager;
    }
    private String getProperty(String key) {
        return env.getProperty(key);
    }
}
