package br.com.feign.example.customer.api.v1.request

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import javax.validation.Valid
import javax.validation.constraints.NotNull

@JsonIgnoreProperties(ignoreUnknown = true)
data class CreateCustomerRequest(
        @field:[NotNull Valid] val fullName: String?,
        @field:[Valid] val nickName: String?,
        @field:[Valid] val birthDate: String?,
        @field:[NotNull Valid] val gender: String?,
        @field:[Valid] val civilState: String?)