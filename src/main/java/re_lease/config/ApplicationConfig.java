package re_lease.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@Configuration
@ComponentScan(basePackages = "re_lease")
@PropertySource({"classpath:/application.properties"})
public class ApplicationConfig {

    @Bean
    public static PropertySourcesPlaceholderConfigurer appProperty() {
        return new PropertySourcesPlaceholderConfigurer();
    }

}
