package br.com.feign.example.customer.api.v1.request

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import javax.validation.Valid
import javax.validation.constraints.NotNull

@JsonIgnoreProperties(ignoreUnknown = true)
data class UpdateCustomerRequest(
    @field:[NotNull Valid] val fullName: String?,
    @field:[Valid] val nickName: String? = null,
    @field:[Valid] val birthDate: String? = null,
    @field:[Valid] val gender: String? = null,
    @field:[Valid] val civilState: String? = null
)