package br.com.feign.example.customer.repository.config

import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration

@Configuration
@ComponentScan(basePackages = ["br.com.feign.example.customer.repository"])
open class RepositoryConfig