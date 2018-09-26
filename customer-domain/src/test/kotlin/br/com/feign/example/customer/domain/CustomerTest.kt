package br.com.feign.example.customer.domain

import br.com.feign.example.customer.domain.repository.CustomerRepository
import br.com.feign.example.customer.infrastructure.exception.CustomerErrorCode
import br.com.feign.example.global.exception.BusinessException
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import org.junit.Test
import org.mockito.Mockito
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class CustomerTest {

    private val customerRepository: CustomerRepository = mock()

    @Test
    fun createCustomer() {
        whenever(customerRepository.save(any())).thenReturn(1)

        val customer = createBasicCustomer()

        customer.create(customerRepository)

        assertEquals(Customer.Status.ACTIVE, customer.status)
        Mockito.verify(customerRepository).save(customer)
    }

    @Test
    fun updateCustomer() {
        whenever(customerRepository.update(any())).thenReturn(1)

        val customer = createBasicCustomer()

        val customerUpdated = br.com.feign.example.customer.domain.updateCustomer(customer.id)

        customerUpdated.update(customerRepository, customer)

        assertEquals(Customer.Status.ACTIVE, customer.status)
        Mockito.verify(customerRepository).update(customerUpdated)
    }

    @Test
    fun updateStatusToInactivated() {
        whenever(customerRepository.updateStatus(any(), any())).thenReturn(1)

        val customer = createBasicCustomer()

        assertEquals(Customer.Status.ACTIVE, customer.status)

        customer.updateStatus(Customer.Status.INACTIVE, customerRepository)

        assertEquals(Customer.Status.INACTIVE, customer.status)
        Mockito.verify(customerRepository).updateStatus(customer.id, Customer.Status.INACTIVE)
    }

    @Test
    fun deleteCustomer() {
        whenever(customerRepository.delete(any())).thenReturn(1)

        val customer = createBasicCustomer()

        whenever(customerRepository.updateStatus(any(), any())).thenReturn(1)

        customer.updateStatus(Customer.Status.INACTIVE, customerRepository)
        assertEquals(Customer.Status.INACTIVE, customer.status)

        customer.delete(customerRepository)

        Mockito.verify(customerRepository).delete(customer.id)
    }

    @Test
    fun failDeleteCustomerWhenStatusIsActive() {
        val customer = createBasicCustomer()

        try {
            customer.delete(customerRepository)
            throw Exception(TEST_BUSINESS_BROKE_MESSAGE)
        } catch (e: BusinessException) {
            assertTrue(e.message?.contains(CustomerErrorCode.STATUS_INVALID_TO_DELETE.code)!!)
        }
    }
}