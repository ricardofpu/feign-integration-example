package br.com.feign.example.customer.repository.config

import br.com.feign.example.customer.infrastructure.tenant.TenantContext
import br.com.feign.example.customer.repository.liquibase.LiquibaseHandler
import org.junit.Before
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringRunner

@SpringBootTest
@RunWith(SpringRunner::class)
@ContextConfiguration(classes = [(RepositoryTestConfig::class)])
abstract class RepositoryBaseTest {

    @Autowired
    lateinit var liquibaseHandler: LiquibaseHandler

    @Value("\${customer.schema.name}")
    private lateinit var schema: String

    @Before
    fun loadSchema() {
        TenantContext.setCurrentTenant(schema)
        liquibaseHandler.createSchema(schema)
    }
}
