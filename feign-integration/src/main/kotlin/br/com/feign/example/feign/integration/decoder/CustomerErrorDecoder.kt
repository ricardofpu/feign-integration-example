package br.com.feign.example.feign.integration.decoder

import br.com.feign.example.customer.infrastructure.exception.CustomerErrorCode
import br.com.feign.example.global.exception.BusinessException
import br.com.feign.example.global.exception.NotFoundException
import br.com.feign.example.global.exception.error.ErrorCode
import feign.Response
import feign.codec.ErrorDecoder
import org.springframework.stereotype.Component
import java.lang.Exception

@Component
class CustomerErrorDecoder : ErrorDecoder {

    override fun decode(methodKey: String, response: Response): Exception {
        if (response.status() == 404) {
            throw NotFoundException()
        }

        throw BusinessException(CustomerErrorCode.CUSTOMER_INTEGRATION_FAILED)
    }
}