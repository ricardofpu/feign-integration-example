package br.com.feign.example.global.exception.error

import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_NULL)
open class ResourceValue(val resource: String, val value: String) {

    constructor(`class`: Class<*>, value: String) : this(`class`.simpleName, value)
}