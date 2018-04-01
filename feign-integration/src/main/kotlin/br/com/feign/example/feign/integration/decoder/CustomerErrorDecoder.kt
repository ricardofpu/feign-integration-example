package br.com.feign.example.feign.integration.decoder

import feign.Response
import feign.codec.ErrorDecoder
import org.springframework.stereotype.Component
import java.lang.Exception

@Component
class CustomerErrorDecoder : ErrorDecoder {

    override fun decode(methodKey: String, response: Response): Exception {
//        if (response.status() == 404) {
//            throw NotFoundException()
//        }
//
//        throw BusinessException.of("CUSTOMER_SEARCH", PolicyErrorCode.CUSTOMER_SEARCH_INTEGRATION_ERROR)
    }
}