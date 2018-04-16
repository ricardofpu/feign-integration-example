package br.com.feign.example.customer.repository

import br.com.feign.example.customer.domain.Customer
import br.com.feign.example.customer.domain.FullName
import br.com.feign.example.customer.domain.NickName
import br.com.feign.example.customer.domain.createBasicCustomer
import br.com.feign.example.customer.domain.repository.CustomerRepository
import br.com.feign.example.customer.repository.config.RepositoryBaseTest
import org.junit.Test
import org.springframework.beans.factory.annotation.Autowired
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull

class JdbcCustomerRepositoryTest : RepositoryBaseTest() {

    @Autowired
    private lateinit var repository: CustomerRepository

    @Test
    fun saveCustomerSuccess() {
        val customer = createBasicCustomer()

        val saved = repository.save(customer)
        assertEquals(1, saved)
    }

    @Test
    fun findCustomerSuccess() {
        val customer = createBasicCustomer()

        val saved = repository.save(customer)
        assertEquals(1, saved)


        val find = repository.find(customer.id)
        assertNotNull(find)

        assertEquals(customer.id, find?.id)
        assertEquals(customer.fullName, find?.fullName)
        assertEquals(customer.nickName, find?.nickName)
        assertEquals(customer.birthDate, find?.birthDate)
        assertEquals(customer.civilState, find?.civilState)
        assertEquals(customer.gender, find?.gender)
        assertEquals(customer.status, find?.status)

    }


    @Test
    fun findCustomerEmpty() {
        val find = repository.find(Customer.Id())
        assertNull(find)
    }

    @Test
    fun updateCustomerSuccess() {
        val customer = createBasicCustomer()

        val saved = repository.save(customer)
        assertEquals(1, saved)

        val customerUpdated = Customer(
                id = customer.id,
                fullName = FullName("Full Name Updated"),
                nickName = NickName("Nick Updated"),
                gender = customer.gender,
                civilState = customer.civilState,
                birthDate = customer.birthDate
        )
        val updated = repository.update(customerUpdated)
        assertEquals(1, updated)

        val find = repository.find(customerUpdated.id)
        assertNotNull(find)

        assertEquals(customer.id, find?.id)
        assertEquals(customerUpdated.fullName, find?.fullName)
        assertEquals(customerUpdated.nickName, find?.nickName)
        assertEquals(customerUpdated.birthDate, find?.birthDate)
        assertEquals(customerUpdated.civilState, find?.civilState)
        assertEquals(customerUpdated.gender, find?.gender)
        assertEquals(customerUpdated.status, find?.status)

    }

    @Test
    fun updateStatusSuccess() {
        val customer = createBasicCustomer()

        val saved = repository.save(customer)
        assertEquals(1, saved)
        assertEquals(Customer.Status.ACTIVE, customer.status)

        val updated = repository.updateStatus(customer.id, Customer.Status.INACTIVE)
        assertEquals(1, updated)

        val find = repository.find(customer.id)
        assertNotNull(find)
        assertEquals(customer.id, find?.id)
        assertEquals(Customer.Status.INACTIVE, find?.status)
    }

    @Test
    fun deleteSuccess() {
        val customer = createBasicCustomer()

        val saved = repository.save(customer)
        assertEquals(1, saved)

        val deleted = repository.delete(customer.id)
        assertEquals(1, deleted)

        val find = repository.find(customer.id)
        assertNull(find)
    }
}