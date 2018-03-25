package br.com.feign.example.customer.domain

import br.com.feign.example.customer.domain.repository.CustomerRepository
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import org.junit.Test
import org.mockito.Mockito
import kotlin.test.assertEquals

class CustomerTest {

    private val customerRepository: CustomerRepository = mock()

    @Test
    fun createCustomer() {
        whenever(customerRepository.save(any())).thenReturn(1)

        val customer = createBasicCustomer()

        customer.create(customerRepository)

        assertEquals(Customer.Status.ACTIVATED, customer.status)
        Mockito.verify(customerRepository).save(customer)
    }

    @Test
    fun updateCustomer() {
        whenever(customerRepository.update(any())).thenReturn(1)

        val customer = createBasicCustomer()

        customer.update(customerRepository)

        assertEquals(Customer.Status.ACTIVATED, customer.status)
        Mockito.verify(customerRepository).update(customer)
    }

    @Test
    fun updateStatusToInactivated() {
        whenever(customerRepository.updateStatus(any(), any())).thenReturn(1)

        val customer = createBasicCustomer()

        assertEquals(Customer.Status.ACTIVATED, customer.status)

        customer.updateStatus(Customer.Status.INACTIVATED, customerRepository)

        assertEquals(Customer.Status.INACTIVATED, customer.status)
        Mockito.verify(customerRepository).updateStatus(customer.id!!, Customer.Status.INACTIVATED)
    }
}