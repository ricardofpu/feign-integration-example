package br.com.feign.example.feign.integration.service

import br.com.feign.example.customer.api.v1.representation.CustomerRepresentation
import br.com.feign.example.customer.domain.*

fun CustomerRepresentation.toDomain(): Customer {
    val customer = Customer(
            id = Customer.Id(this.id!!),
            fullName = FullName(this.fullName),
            nickName = NickName(this.nickName),
            civilState = CivilState(this.civilState),
            gender = Gender(this.gender),
            birthDate = BirthDate(this.birthDate)
    )
    customer.status = Customer.Status.valueOf(this.status!!)
    return customer
}
