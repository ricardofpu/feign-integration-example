package br.com.feign.example.customer.application.commands

import br.com.feign.example.customer.domain.*

data class CreateCustomer(val customerId: Customer.Id, val fullName: FullName, val nickName: NickName?,
                          val birthDate: BirthDate?, val gender: Gender, val civilState: CivilState?)

data class UpdateCustomer(val customerId: Customer.Id, val fullName: FullName, val nickName: NickName?,
                          val birthDate: BirthDate?, val gender: Gender, val civilState: CivilState?)

data class UpdateStatus(val customerId: Customer.Id, val status: Customer.Status)

data class FindCustomer(val customerId: Customer.Id)

data class ValidateCustomer(val customerId: Customer.Id)

data class DeleteCustomer(val customerId: Customer.Id)