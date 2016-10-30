package re_lease.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.hibernate5.HibernateExceptionTranslator;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableJpaRepositories(basePackages = "re_lease.service_layer")
@EnableTransactionManagement
public class PersistenceConfig {

    @Bean
    public DataSource dataSource() {
        EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
        return builder.setType(EmbeddedDatabaseType.H2).build();
    }

    @Bean
    public HibernateExceptionTranslator hibernateExceptionTranslator() {
        return new HibernateExceptionTranslator();
    }

    @Bean
    public EntityManagerFactory entityManagerFactory() {

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setDatabase(Database.H2);
        vendorAdapter.setGenerateDdl(true);
        vendorAdapter.setShowSql(true);

        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setJpaVendorAdapter(vendorAdapter);
        factory.setPackagesToScan("re_lease.service_layer");
        factory.setDataSource(dataSource());

        Properties jpaProperties = new Properties();

        //prettifying sql
        jpaProperties.setProperty("hibernate.format_sql",
                "true");
        jpaProperties.setProperty("hibernate.use_sql_comments",
                "true");


        //HikariCP (connection-pool lib) config - https://github.com/brettwooldridge/HikariCP

        //hikari db connection
        jpaProperties.setProperty("hibernate.connection.provider_class",
                "org.hibernate.hikaricp.internal.HikariCPConnectionProvider");
        jpaProperties.setProperty("hibernate.hikari.dataSourceClassName",
                "org.h2.jdbcx.JdbcDataSource");
        jpaProperties.setProperty("hibernate.hikari.dataSource.url",
                "jdbc:h2:mem:testdb");
        jpaProperties.setProperty("hibernate.hikari.dataSource.user",
                "sa");
        jpaProperties.setProperty("hibernate.hikari.dataSource.password",
                "");

        //hikari settings
        jpaProperties.setProperty("hibernate.hikari.minimumIdle",
                "10");
        jpaProperties.setProperty("hibernate.hikari.maximumPoolSize",
                "25");
        jpaProperties.setProperty("hibernate.hikari.idleTimeout",
                "30000");


        //TODO Second-level caching - i.e. EHCACHE

        factory.setJpaProperties(jpaProperties);

        factory.afterPropertiesSet();

        return factory.getObject();

    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        return new JpaTransactionManager(entityManagerFactory());
    }

}
