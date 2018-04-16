package br.com.feign.example.feign.integration.config

import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.cloud.netflix.feign.EnableFeignClients
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration

@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackages = ["br.com.feign.example.feign.integration"])
@EnableFeignClients
open class TestConfig
