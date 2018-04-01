package br.com.feign.example.customer.web.utils

import br.com.feign.example.customer.api.v1.request.CreateCustomerRequest
import br.com.feign.example.customer.api.v1.request.UpdateCustomerRequest
import br.com.feign.example.customer.application.commands.CreateCustomer
import br.com.feign.example.customer.application.commands.UpdateCustomer
import br.com.feign.example.customer.domain.*


fun CreateCustomerRequest.toCommand() =
        CreateCustomer(
                customerId = Customer.Id(),
                fullName = FullName(this.fullName),
                nickName = if (nickName != null) NickName(this.nickName) else null,
                birthDate = if (birthDate != null) BirthDate(this.birthDate) else null,
                civilState = if (civilState != null) CivilState(this.civilState) else null,
                gender = Gender(this.gender)
        )

fun UpdateCustomerRequest.toCommand(customerId: String) =
        UpdateCustomer(
                customerId = Customer.Id(customerId),
                fullName = FullName(this.fullName),
                nickName = if (nickName != null) NickName(this.nickName) else null,
                birthDate = if (birthDate != null) BirthDate(this.birthDate) else null,
                civilState = if (civilState != null) CivilState(this.civilState) else null,
                gender = Gender(this.gender)
        )