package br.com.feign.example.customer.web.config

import br.com.feign.example.customer.web.ApplicationConfig
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import

@Configuration
@Import(ApplicationConfig::class)
open class ApplicationConfigTest