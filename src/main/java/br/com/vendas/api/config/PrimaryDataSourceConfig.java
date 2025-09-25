package br.com.vendas.api.config;

import jakarta.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableJpaRepositories(
    basePackages = {
        "br.com.vendas.api.domain.repository"
    },
    entityManagerFactoryRef = "primaryEntityManagerFactory",
    transactionManagerRef = "primaryTransactionManager")
public class PrimaryDataSourceConfig {

  @Primary
  @Bean
  @ConfigurationProperties("spring.datasource.primary")
  public DataSource primaryDataSource() {
    return DataSourceBuilder.create().build();
  }

  @Primary
  @Bean
  public LocalContainerEntityManagerFactoryBean primaryEntityManagerFactory(
      @Qualifier("primaryDataSource") DataSource primaryDataSource,
      EntityManagerFactoryBuilder builder) {
    return builder
        .dataSource(primaryDataSource)
        .packages("br.com.vendas.api.domain")
        .persistenceUnit("primary")
        .build();
  }

  @Primary
  @Bean
  public PlatformTransactionManager primaryTransactionManager(
      @Qualifier("primaryEntityManagerFactory") EntityManagerFactory primaryEntityManagerFactory) {
    return new JpaTransactionManager(primaryEntityManagerFactory);
  }
}
