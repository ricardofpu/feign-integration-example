package br.com.feign.example.feign.integration.service

import br.com.feign.example.customer.api.v1.CustomerApi
import br.com.feign.example.customer.domain.Customer
import br.com.feign.example.customer.domain.service.CustomerService
import br.com.feign.example.feign.integration.FeignQualifier
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service

@Service
class CustomerApiService : CustomerService {

    @Autowired
    @Qualifier(FeignQualifier.CUSTOMER_API)
    private lateinit var customerApi: CustomerApi

    override fun getCustomer(customerId: Customer.Id): Customer {
        return customerApi.find(customerId.value).toDomain()
    }
}