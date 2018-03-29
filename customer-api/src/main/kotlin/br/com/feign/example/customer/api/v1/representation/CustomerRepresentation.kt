package br.com.feign.example.customer.api.v1.representation

import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_NULL)
data class CustomerRepresentation(
        val id: String?,
        val fullName: String?,
        val nickName: String?,
        val status: String?,
        val birthDate: String?,
        val gender: String?,
        val civilState: String?)