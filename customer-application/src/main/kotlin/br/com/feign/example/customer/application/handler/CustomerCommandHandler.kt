package br.com.feign.example.customer.application.handler

import br.com.feign.example.customer.application.commands.*
import br.com.feign.example.customer.domain.Customer
import br.com.feign.example.customer.domain.repository.CustomerRepository
import org.apache.logging.log4j.LogManager
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.util.*

@Component
open class CustomerCommandHandler @Autowired constructor(private val repository: CustomerRepository) {

    private val LOG = LogManager.getLogger(this.javaClass)

    fun handler(command: CreateCustomer): Customer {
        LOG.debug("Received command to create a customer in data base with id: {}", command.customerId.value)

        val customer = Customer(
                id = command.customerId,
                fullName = command.fullName,
                nickName = command.nickName,
                birthDate = command.birthDate,
                gender = command.gender,
                civilState = command.civilState
        )

        customer.create(repository)

        LOG.debug("Customer created with id: [{}]", command.customerId.value)
        return customer
    }

    fun handler(command: UpdateCustomer): Customer {
        LOG.debug("Received command to update a customer in data base with id: {}", command.customerId.value)

        val customer = getCustomer(command.customerId)

        val newCustomer = Customer(
                id = command.customerId,
                fullName = command.fullName,
                nickName = command.nickName,
                birthDate = command.birthDate,
                gender = command.gender,
                civilState = command.civilState
        )

        newCustomer.update(repository, customer)

        LOG.debug("Customer updated with id: [{}]", command.customerId.value)
        return newCustomer
    }

    fun handler(command: DeleteCustomer): Customer? {
        LOG.debug("Received command to delete a customer in data base with id: {}", command.customerId.value)

        val customer = getCustomer(command.customerId)

        customer.delete(repository)

        LOG.debug("Customer deleted with id: [{}]", command.customerId.value)
        return customer
    }

    fun handler(command: FindCustomer): Customer {
        LOG.debug("Received command to find a customer in data base with id: {}", command.customerId.value)

        return getCustomer(command.customerId)
    }

    fun handler(command: UpdateStatus) {
        LOG.debug("Received command to update status from a customer in data base with id: {}", command.customerId.value)

        val coupon = getCustomer(command.customerId)

        coupon.updateStatus(command.status, repository)

        LOG.debug("Customer updated with id: [{}]", command.customerId.value)
    }

    fun handler(command: ValidateCustomer) {
        LOG.debug("Received command to validate a customer in data base with id: {}", command.customerId.value)

        val coupon = getCustomer(command.customerId)

        coupon.validate()

        LOG.debug("Customer validated with id: [{}]", command.customerId.value)
    }

    private fun getCustomer(customerId: Customer.Id) =
            Optional.ofNullable(repository.find(customerId))
                    .orElseThrow {
                        Exception("NotFound")
                    }

}