package bitc.fullstack.sleepon.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@PropertySource("classpath:/application.properties")
public class DBConfig {

    @Autowired
    private ApplicationContext app;

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.hikari")
    public HikariConfig hikariConfig() {
        return new HikariConfig();
    }

    @Bean
    public DataSource dataSource() throws Exception {
        DataSource ds = new HikariDataSource(hikariConfig());
        System.out.println(ds.toString());
        return ds;
    }

//    // 세션 설정
//    @Bean
//    public ServletContextInitializer initializer() {
//        return new ServletContextInitializer() {
//            @Override
//            public void onStartup(ServletContext servletContext) throws ServletException {
//                // 세션 타임아웃을 60분으로 설정
//                servletContext.getSessionCookieConfig().setMaxAge(60 * 60);
//            }
//        };
//    }


    @Bean
    @ConfigurationProperties(prefix = "spring.jpa")
    public Properties hibernateConfig() {
        return new Properties();
    }
}