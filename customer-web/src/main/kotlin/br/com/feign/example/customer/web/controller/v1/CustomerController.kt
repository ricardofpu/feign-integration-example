package br.com.feign.example.customer.web.controller.v1

import br.com.feign.example.customer.api.v1.CustomerApi
import br.com.feign.example.customer.api.v1.representation.CustomerRepresentation
import br.com.feign.example.customer.api.v1.request.CreateCustomerRequest
import br.com.feign.example.customer.api.v1.request.UpdateCustomerRequest
import br.com.feign.example.customer.api.v1.request.UpdateStatusRequest
import br.com.feign.example.customer.application.commands.DeleteCustomer
import br.com.feign.example.customer.application.commands.FindCustomer
import br.com.feign.example.customer.application.commands.UpdateStatus
import br.com.feign.example.customer.application.commands.ValidateCustomer
import br.com.feign.example.customer.application.handler.CustomerCommandHandler
import br.com.feign.example.customer.domain.Customer
import br.com.feign.example.customer.web.utils.toCommand
import br.com.feign.example.customer.web.utils.toRepresentation
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
class CustomerController constructor(
    private val commandHandler: CustomerCommandHandler
) : CustomerApi {

    override fun create(@RequestBody @Valid request: CreateCustomerRequest): CustomerRepresentation {
        val command = request.toCommand()
        val customer = commandHandler.handler(command)
        return customer.toRepresentation()
    }

    override fun update(
        @PathVariable("customerId") customerId: String,
        @RequestBody @Valid request: UpdateCustomerRequest
    ): CustomerRepresentation {
        val command = request.toCommand(customerId)
        val customer = commandHandler.handler(command)
        return customer.toRepresentation()
    }

    override fun delete(@PathVariable("customerId") customerId: String) {
        val command = DeleteCustomer(Customer.Id(customerId))
        commandHandler.handler(command)
    }

    override fun find(@PathVariable("customerId") customerId: String): CustomerRepresentation {
        val command = FindCustomer(Customer.Id(customerId))
        val customer = commandHandler.handler(command)
        return customer.toRepresentation()
    }

    override fun updateStatus(
        @PathVariable("customerId") customerId: String,
        @RequestBody @Valid request: UpdateStatusRequest
    ) {
        val command = UpdateStatus(Customer.Id(customerId), Customer.Status.valueOf(request.status))
        commandHandler.handler(command)
    }

    override fun validate(@PathVariable("customerId") customerId: String) {
        val command = ValidateCustomer(Customer.Id(customerId))
        commandHandler.handler(command)
    }
}