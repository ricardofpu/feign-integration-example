package br.com.feign.example.customer.repository.config

import br.com.feign.example.customer.repository.liquibase.LiquibaseConfig
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.datasource.DataSourceTransactionManager
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.annotation.EnableTransactionManagement
import javax.sql.DataSource

@Configuration
@EnableTransactionManagement
@Import(LiquibaseConfig::class)
@ComponentScan(basePackages = ["br.com.feign.example.customer.repository"])
open class RepositoryConfig {

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    open fun dataSource() =
            DataSourceBuilder.create().build()!!

    @Bean
    open fun jdbcTemplate(dataSource: DataSource) =
            JdbcTemplate(dataSource)

    @Bean
    open fun txManager(): PlatformTransactionManager {
        return DataSourceTransactionManager(dataSource())
    }

}