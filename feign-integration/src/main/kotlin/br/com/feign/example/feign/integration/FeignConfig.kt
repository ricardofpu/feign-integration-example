package br.com.feign.example.feign.integration

import br.com.feign.example.customer.api.v1.CustomerApi
import br.com.feign.example.feign.integration.decoder.CustomerErrorDecoder
import feign.Feign
import feign.Logger
import feign.Request
import feign.Retryer
import feign.codec.Decoder
import feign.codec.Encoder
import feign.slf4j.Slf4jLogger
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.cloud.netflix.feign.FeignClientsConfiguration
import org.springframework.cloud.netflix.feign.support.SpringMvcContract
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import


@Import(FeignClientsConfiguration::class)
@Configuration
open class FeignConfig {

    private val oneSecond = 1000
    private val tenSeconds = oneSecond * 10
    private val oneMinute = 60000

    @Value("\${customer.api.url}")
    private lateinit var customerApiUrl: String

    @Autowired
    private lateinit var customerErrorDecoder: CustomerErrorDecoder

    @Autowired
    lateinit var decoder: Decoder

    @Autowired
    lateinit var encoder: Encoder

    @Bean
    @Qualifier(FeignQualifier.CUSTOMER_API)
    open fun customerClientFeign(): CustomerApi {
        return Feign.builder()
                .encoder(encoder)
                .decoder(decoder)
                .logger(Slf4jLogger())
                .contract(SpringMvcContract())
                .logLevel(Logger.Level.FULL)
                .options(Request.Options(tenSeconds, oneMinute))
                .retryer(Retryer.NEVER_RETRY)
                .errorDecoder(customerErrorDecoder)
                .target(CustomerApi::class.java, customerApiUrl)
    }

}