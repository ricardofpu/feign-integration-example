package br.com.feign.example.customer.repository.liquibase

import liquibase.integration.spring.MultiTenantSpringLiquibase
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import javax.sql.DataSource

@Configuration
@EnableConfigurationProperties(LiquibaseProperties::class)
open class LiquibaseConfig @Autowired constructor(private val datasource: DataSource, private val liquibaseProperties: LiquibaseProperties) {

    private val logger = LoggerFactory.getLogger(br.com.feign.example.customer.repository.liquibase.LiquibaseConfig::class.java)

    @Bean
    open fun liquibase(): MultiTenantSpringLiquibase {
        val liquibase = MultiTenantSpringLiquibase()

        liquibase.dataSource = datasource
        liquibase.changeLog = liquibaseProperties.changeLog
        liquibase.contexts = liquibaseProperties.contexts
        liquibase.labels = liquibaseProperties.labels
        liquibase.defaultSchema = liquibaseProperties.defaultSchema
        liquibase.isDropFirst = liquibaseProperties.isDropFirst
        liquibase.isShouldRun = liquibaseProperties.isEnabled
        liquibase.schemas = mutableListOf(liquibaseProperties.defaultSchema)

        logger.debug("Configuring Liquibase")

        return liquibase
    }
}
