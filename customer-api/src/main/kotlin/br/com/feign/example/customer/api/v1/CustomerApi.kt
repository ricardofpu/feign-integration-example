package br.com.feign.example.customer.api.v1

import br.com.feign.example.customer.api.v1.representation.CustomerRepresentation
import br.com.feign.example.customer.api.v1.request.CreateCustomerRequest
import br.com.feign.example.customer.api.v1.request.UpdateCustomerRequest
import br.com.feign.example.customer.api.v1.request.UpdateStatusRequest
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus
import javax.validation.Valid

@RequestMapping(value = ["/v1/customers"])
interface CustomerApi {

    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    @PostMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
    fun create(@RequestBody @Valid request: CreateCustomerRequest): CustomerRepresentation

    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @PutMapping("/{customerId}", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun update(
        @PathVariable("customerId") customerId: String,
        @RequestBody @Valid request: UpdateCustomerRequest
    ): CustomerRepresentation

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ResponseBody
    @DeleteMapping("/{customerId}", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun delete(@PathVariable("customerId") customerId: String)

    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @RequestMapping(
        value = ["/{customerId}"],
        method = [RequestMethod.GET],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun find(@PathVariable("customerId") customerId: String): CustomerRepresentation

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ResponseBody
    @PatchMapping("/{customerId}/status", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun updateStatus(
        @PathVariable("customerId") customerId: String,
        @RequestBody @Valid request: UpdateStatusRequest
    )

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ResponseBody
    @GetMapping("/{customerId}/validate", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun validate(@PathVariable("customerId") customerId: String)
}