package br.com.feign.example.customer.domain

import br.com.feign.example.customer.infrastructure.Validator
import javax.validation.constraints.NotNull

data class FullName(@field:[NotNull] val value: String?) {

    init {
        Validator.validate(this)
    }
}