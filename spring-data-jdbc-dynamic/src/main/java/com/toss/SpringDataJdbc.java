package com.toss;

import com.toss.interceptor.CustomRepositoryFactoryBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jdbc.repository.QueryMappingConfiguration;
import org.springframework.data.jdbc.repository.config.DefaultQueryMappingConfiguration;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;

@SpringBootApplication
@EnableJdbcRepositories(repositoryFactoryBeanClass = CustomRepositoryFactoryBean.class)
public class SpringDataJdbc {
    public static void main(String[] args) {
        SpringApplication.run(SpringDataJdbc.class, args);
    }

    @Bean
    public DataSource dataSource() {
        EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
        return builder.setType(EmbeddedDatabaseType.HSQL).build();
    }

    @Bean
    public QueryMappingConfiguration queryMappingConfiguration() {
        return new DefaultQueryMappingConfiguration();
    }
}
