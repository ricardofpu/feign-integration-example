package br.com.feign.example.customer.domain.repository

import br.com.feign.example.customer.domain.Customer

interface CustomerRepository {

    fun find(customerId: Customer.Id): Customer?

    fun save(customer: Customer): Int

    fun update(customer: Customer): Int

    fun updateStatus(customerId: Customer.Id, status: Customer.Status): Int

    fun delete(customerId: Customer.Id): Int
}