package br.com.feign.example.customer.application.handler

import br.com.feign.example.customer.application.commands.CreateCustomer
import br.com.feign.example.customer.application.commands.DeleteCustomer
import br.com.feign.example.customer.application.commands.FindCustomer
import br.com.feign.example.customer.application.commands.UpdateCustomer
import br.com.feign.example.customer.application.commands.UpdateStatus
import br.com.feign.example.customer.application.commands.ValidateCustomer
import br.com.feign.example.customer.domain.Customer
import br.com.feign.example.customer.domain.repository.CustomerRepository
import br.com.feign.example.global.exception.NotFoundException
import br.com.feign.example.global.exception.error.ResourceValue
import org.apache.logging.log4j.LogManager
import org.springframework.stereotype.Component
import java.util.*

@Component
open class CustomerCommandHandler constructor(
    private val repository: CustomerRepository
) {

    private val log = LogManager.getLogger(this.javaClass)

    fun handler(command: CreateCustomer): Customer {
        log.debug("Received command to create a customer in data base with id: {}", command.customerId.value)

        val customer = Customer(
            id = command.customerId,
            fullName = command.fullName,
            nickName = command.nickName,
            birthDate = command.birthDate,
            gender = command.gender,
            civilState = command.civilState
        )

        customer.create(repository)

        log.debug("Customer created with id: [{}]", command.customerId.value)
        return customer
    }

    fun handler(command: UpdateCustomer): Customer {
        log.debug("Received command to update a customer in data base with id: {}", command.customerId.value)

        val customer = getCustomer(command.customerId)

        val newCustomer = Customer(
            id = command.customerId,
            fullName = command.fullName,
            nickName = command.nickName?.let { command.nickName } ?: customer.nickName,
            birthDate = command.birthDate?.let { command.birthDate } ?: customer.birthDate,
            gender = command.gender?.let { command.gender } ?: customer.gender,
            civilState = command.civilState?.let { command.civilState } ?: customer.civilState
        )

        newCustomer.update(repository, customer)

        log.debug("Customer updated with id: [{}]", command.customerId.value)
        return newCustomer
    }

    fun handler(command: DeleteCustomer): Customer? {
        log.debug("Received command to delete a customer in data base with id: {}", command.customerId.value)

        val customer = getCustomer(command.customerId)

        customer.delete(repository)

        log.debug("Customer deleted with id: [{}]", command.customerId.value)
        return customer
    }

    fun handler(command: FindCustomer): Customer {
        log.debug("Received command to find a customer in data base with id: {}", command.customerId.value)

        return getCustomer(command.customerId)
    }

    fun handler(command: UpdateStatus) {
        log.debug(
            "Received command to update status from a customer in data base with id: {}",
            command.customerId.value
        )

        val customer = getCustomer(command.customerId)

        customer.updateStatus(command.status, repository)

        log.debug("Customer updated with id: [{}]", command.customerId.value)
    }

    fun handler(command: ValidateCustomer) {
        log.debug("Received command to validate a customer in data base with id: {}", command.customerId.value)

        val customer = getCustomer(command.customerId)

        customer.validate()

        log.debug("Customer validated with id: [{}]", command.customerId.value)
    }

    private fun getCustomer(customerId: Customer.Id): Customer =
        Optional.ofNullable(repository.find(customerId))
            .orElseThrow {
                NotFoundException(ResourceValue(Customer::class.java, customerId.value))
            }

}