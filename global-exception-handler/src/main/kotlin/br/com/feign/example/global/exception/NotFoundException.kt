package br.com.feign.example.global.exception

import br.com.feign.example.global.exception.error.ResourceValue
import com.fasterxml.jackson.databind.ObjectMapper

class NotFoundException : RuntimeException {

    companion object {
        fun tryBuildLogMessageAsJson(`object`: Any) =
                try {
                    ObjectMapper().writeValueAsString(`object`)
                } catch (var2: Exception) {
                    null
                }
    }

    var resourceValue: ResourceValue? = null

    constructor() {}

    @Throws(RuntimeException::class)
    constructor(resourceValue: ResourceValue, cause: Throwable) : super(tryBuildLogMessageAsJson(resourceValue), cause) {
        this.resourceValue = resourceValue
    }

    @Throws(RuntimeException::class)
    constructor(resourceValue: ResourceValue) : super(tryBuildLogMessageAsJson(resourceValue)) {
        this.resourceValue = resourceValue
    }

    @Throws(RuntimeException::class)
    constructor(cause: Throwable) : super(cause)

}