package br.com.feign.example.customer.domain.service

import br.com.feign.example.customer.domain.Customer

interface CustomerService {

    fun getCustomer(customerId: Customer.Id): Customer
}