package br.com.feign.example.customer.repository.liquibase

import liquibase.exception.LiquibaseException
import liquibase.integration.spring.SpringLiquibase
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties
import org.springframework.core.io.ResourceLoader
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Component
import javax.sql.DataSource

@Component
class LiquibaseHandler @Autowired constructor(private val jdbcTemplate: JdbcTemplate,
                                              private val dataSource: DataSource,
                                              private val resourceLoader: ResourceLoader,
                                              var liquibaseProperties: LiquibaseProperties) {

    fun createSchema(schema: String) = create(schema)

    private fun create(schema: String) {
        val sql = "SELECT COUNT(schema_name) FROM information_schema.schemata WHERE schema_name = '$schema'"

        if (jdbcTemplate.queryForObject(sql, Int::class.java) <= 0) {
            jdbcTemplate.execute("CREATE SCHEMA \"$schema\"")
            executeLiquibase(schema)
        }
    }

    @Throws(LiquibaseException::class)
    private fun executeLiquibase(schema: String) {
        val springLiquibase = SpringLiquibase()

        springLiquibase.dataSource = dataSource
        springLiquibase.changeLog = liquibaseProperties.changeLog
        springLiquibase.contexts = liquibaseProperties.contexts
        springLiquibase.labels = liquibaseProperties.labels
        springLiquibase.defaultSchema = schema
        springLiquibase.resourceLoader = resourceLoader
        springLiquibase.setShouldRun(liquibaseProperties.isEnabled)
        springLiquibase.setRollbackFile(liquibaseProperties.rollbackFile)
        springLiquibase.setChangeLogParameters(liquibaseProperties.parameters)

        springLiquibase.afterPropertiesSet()
    }

}