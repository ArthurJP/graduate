package cn.strongme.main;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.view.tiles3.TilesConfigurer;

/**
 * @author Walter
 */
@SpringBootApplication(scanBasePackages = "cn.strongme")
@MapperScan(basePackages = "cn.strongme")
@ServletComponentScan(basePackages = "cn.strongme")
@EnableTransactionManagement
public class BasicApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(BasicApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(BasicApplication.class);
    }

    @Bean
    public TilesConfigurer tilesConfigurer() {
        TilesConfigurer tilesConfigurer = new TilesConfigurer();
        tilesConfigurer.setDefinitions(new String[]{"/WEB-INF/tiles/tiles.xml"});
        tilesConfigurer.setCheckRefresh(true);
        return tilesConfigurer;
    }

    @Bean(name = "characterEncodingFilter")
    public FilterRegistrationBean characterEncodingFilter() {
        FilterRegistrationBean bean = new FilterRegistrationBean();
        bean.addInitParameter("encoding", "UTF-8");
        bean.addInitParameter("forceEncoding", "true");
        bean.setFilter(new CharacterEncodingFilter());
        bean.addUrlPatterns("/*");
        return bean;
    }
}
