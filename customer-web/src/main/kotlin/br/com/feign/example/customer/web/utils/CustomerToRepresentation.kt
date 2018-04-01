package br.com.feign.example.customer.web.utils

import br.com.feign.example.customer.api.v1.representation.CustomerRepresentation
import br.com.feign.example.customer.domain.Customer

fun Customer.toRepresentation() =
        CustomerRepresentation(
                id = this.id.value,
                fullName = this.fullName?.value,
                nickName = this.nickName?.value,
                birthDate = this.birthDate?.value,
                status = this.status.name,
                gender = this.gender.value,
                civilState = this.civilState?.value
        )