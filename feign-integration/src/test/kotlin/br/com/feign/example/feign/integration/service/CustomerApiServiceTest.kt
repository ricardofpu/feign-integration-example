package br.com.feign.example.feign.integration.service

import br.com.feign.example.customer.domain.Customer
import br.com.feign.example.customer.domain.service.CustomerService
import br.com.feign.example.feign.integration.config.BaseTest
import br.com.feign.example.global.exception.NotFoundException
import org.junit.Test
import org.springframework.beans.factory.annotation.Autowired
import kotlin.test.assertNotNull

class CustomerApiServiceTest : BaseTest() {

    @Autowired
    lateinit var customerApi: CustomerService

    @Test
    fun getCustomerSuccess() {
        val customer = customerApi.getCustomer(Customer.Id())
        assertNotNull(customer)
        assertNotNull(customer.id)
        assertNotNull(customer.fullName?.value)
        assertNotNull(customer.nickName?.value)
        assertNotNull(customer.civilState?.value)
        assertNotNull(customer.gender.value)
        assertNotNull(customer.status.name)
    }

    @Test(expected = NotFoundException::class)
    fun getCustomerNotFound() {
        customerApi.getCustomer(Customer.Id("notfound"))
    }

}