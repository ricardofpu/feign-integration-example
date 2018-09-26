package br.com.feign.example.customer.infrastructure.exception

import br.com.feign.example.global.exception.error.ErrorCode

private class CustomerError(code: String, key: String) : ErrorCode(code, key)

object CustomerErrorCode {

    val CUSTOMER_INTEGRATION_FAILED: ErrorCode =
            CustomerError("CUSTOMER_INTEGRATION_FAILED", "customer.integration.failed")

    val CUSTOMER_IS_NOT_ACTIVE: ErrorCode =
            CustomerError("CUSTOMER_IS_NOT_ACTIVE", "customer.is.not.active")

    val CHANGE_STATUS_NOT_ALLOWED: ErrorCode =
            CustomerError("CHANGE_STATUS_NOT_ALLOWED", "change.status.not.allowed")

    val STATUS_INVALID_TO_DELETE: ErrorCode =
            CustomerError("STATUS_INVALID_TO_DELETE", "status.invalid.to.delete")

}