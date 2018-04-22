package br.com.feign.example.customer.domain.service

import br.com.feign.example.customer.domain.Customer

interface CustomerService {

    fun findById(customerId: Customer.Id): Customer

    fun validate(customerId: Customer.Id)
}