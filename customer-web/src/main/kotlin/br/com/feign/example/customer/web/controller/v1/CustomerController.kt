package br.com.feign.example.customer.web.controller.v1

import br.com.feign.example.customer.api.v1.CustomerApi
import br.com.feign.example.customer.api.v1.representation.CustomerRepresentation
import br.com.feign.example.customer.api.v1.request.CreateCustomerRequest
import br.com.feign.example.customer.api.v1.request.UpdateCustomerRequest
import br.com.feign.example.customer.api.v1.request.UpdateStatusRequest
import org.springframework.web.bind.annotation.RestController

@RestController
class CustomerController: CustomerApi {

    override fun create(request: CreateCustomerRequest): CustomerRepresentation {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun update(customerId: String, request: UpdateCustomerRequest): CustomerRepresentation {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun delete(customerId: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun find(customerId: String): CustomerRepresentation {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun updateStatus(customerId: String, request: UpdateStatusRequest) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun validate(customerId: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}