package br.com.feign.example.global.exception.error

import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_NULL)
open class ResourceValueResponse(private val resource: String, private val value: String) {

    constructor(resourceValue: ResourceValue): this(resourceValue.resource, resourceValue.value)

}