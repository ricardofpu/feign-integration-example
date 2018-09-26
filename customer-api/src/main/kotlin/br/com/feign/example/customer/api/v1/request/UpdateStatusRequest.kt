package br.com.feign.example.customer.api.v1.request

import javax.validation.constraints.Pattern

data class UpdateStatusRequest(@field:Pattern(regexp = "ACTIVE|INACTIVE") val status: String)