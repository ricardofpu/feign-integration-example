package br.com.feign.example.customer.application.commands

import br.com.feign.example.customer.domain.BirthDate
import br.com.feign.example.customer.domain.CivilState
import br.com.feign.example.customer.domain.Customer
import br.com.feign.example.customer.domain.FullName
import br.com.feign.example.customer.domain.Gender
import br.com.feign.example.customer.domain.NickName

data class CreateCustomer(
    val customerId: Customer.Id,
    val fullName: FullName,
    val nickName: NickName? = null,
    val birthDate: BirthDate? = null,
    val gender: Gender,
    val civilState: CivilState? = null
)

data class DeleteCustomer(val customerId: Customer.Id)

data class FindCustomer(val customerId: Customer.Id)

data class UpdateCustomer(
    val customerId: Customer.Id,
    val fullName: FullName,
    val nickName: NickName? = null,
    val birthDate: BirthDate? = null,
    val gender: Gender,
    val civilState: CivilState? = null
)

data class UpdateStatus(val customerId: Customer.Id, val status: Customer.Status)

data class ValidateCustomer(val customerId: Customer.Id)

