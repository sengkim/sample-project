package com.sengkim.study.sample.config;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceView;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

/**
 * @author sengkim
 * @Date Sep 8, 2015
 * @Email sengkim.it@gmail.com
 */
@EnableWebMvc
@Configuration
@ImportResource(value = { "classpath:/security-context.xml" })
@Import(PersistenceContext.class)
@PropertySource(name = "application", value = { "classpath:/application.properties" })
@ComponentScan(basePackages = { "com.sengkim.study.sample" })
public class MvcConfig extends WebMvcConfigurerAdapter {

    @Override
    public void configureMessageConverters(
            List<HttpMessageConverter<?>> converters) {
        MappingJackson2HttpMessageConverter converter =
                new MappingJackson2HttpMessageConverter();
        converters.add(converter);
    }

    @Override
    public void configureHandlerExceptionResolvers(
            List<HandlerExceptionResolver> exceptionResolvers) {
        // TODO Auto-generated method stub
        super.configureHandlerExceptionResolvers(exceptionResolvers);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // TODO Auto-generated method stub
        super.addInterceptors(registry);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**")
                .addResourceLocations("/resources/")
                .addResourceLocations("classpath:/resources/");
    }

    @Bean
    public InternalResourceViewResolver htmlViewResolver() {
        final InternalResourceViewResolver bean = new InternalResourceViewResolver();
        bean.setViewClass(InternalResourceView.class);
        bean.setPrefix("/internal/");
        bean.setSuffix(".html");
        bean.setOrder(999);
        return bean;
    }

    // uncomment the following block for embedded database
    /*
     * @Bean
     * public DataSource dataSource() {
     * return new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2).addScript("classpath:/data/schema.sql")
     * .addScript("classpath:/data/data.sql").build();
     * }
     */

    @Bean
    public Map<String, String> sql() {
        Map<String, String> sqlString = new HashMap<String, String>();
        sqlString.put("query", "select * from books");
        sqlString.put("queryByPage", "select * from books limit ? offset ?");
        sqlString.put("find", "select * from books b where id = ?");

        return sqlString;
    }

}
