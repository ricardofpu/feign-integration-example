package br.com.feign.example.customer.web

import br.com.feign.example.customer.api.v1.representation.CustomerRepresentation
import br.com.feign.example.customer.api.v1.request.CreateCustomerRequest
import br.com.feign.example.customer.infrastructure.jsonToObject
import br.com.feign.example.customer.infrastructure.objectToJson
import br.com.feign.example.customer.web.config.ApplicationConfigTest
import org.hamcrest.CoreMatchers
import org.junit.Before
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.MessageSource
import org.springframework.context.support.MessageSourceAccessor
import org.springframework.http.MediaType
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.jdbc.Sql
import org.springframework.test.context.jdbc.SqlConfig
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.context.web.WebAppConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext
import java.util.*
import javax.annotation.PostConstruct

@WebAppConfiguration
@SpringBootTest
@RunWith(SpringRunner::class)
@ContextConfiguration(classes = [(ApplicationConfigTest::class)])
@Sql(
    scripts = ["classpath:sqlScripts/clear_tables.sql"],
    config = SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED),
    executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD
)
abstract class ControllerBaseTest {

    @Autowired
    private lateinit var context: WebApplicationContext

    protected lateinit var mockMvc: MockMvc

    @Autowired
    private val messageSource: MessageSource? = null

    private var accessor: MessageSourceAccessor? = null

    @PostConstruct
    private fun init() {
        accessor = MessageSourceAccessor(messageSource, Locale.US)
    }

    fun get(code: String): List<String> = listOf(accessor!!.getMessage(code))

    @Before
    fun setUp() {
        this.mockMvc = MockMvcBuilders
            .webAppContextSetup(this.context)
            .build()
    }

    protected fun requestToCreateCustomer(
        request: CreateCustomerRequest = buildCreateCustomerRequest()
    ): CustomerRepresentation {
        val response = this.mockMvc.perform(
            post(
                "/v1/customers"
            )
                .content(request.objectToJson())
                .contentType(MediaType.APPLICATION_JSON_UTF8)
        )
            .andExpect(MockMvcResultMatchers.status().isCreated)
            .andExpect(MockMvcResultMatchers.jsonPath("$.id", CoreMatchers.notNullValue()))
            .andReturn()

        return response.response.contentAsString.jsonToObject(CustomerRepresentation::class.java)
    }

    protected fun buildCreateCustomerRequest(): CreateCustomerRequest =
        CreateCustomerRequest(
            nickName = "Ricardo",
            fullName = "Ricardo Borges",
            birthDate = "1992-01-01",
            gender = "MALE",
            civilState = "MARRIED"
        )

}