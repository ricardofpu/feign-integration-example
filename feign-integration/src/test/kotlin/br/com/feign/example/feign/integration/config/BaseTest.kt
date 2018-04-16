package br.com.feign.example.feign.integration.config

import org.junit.runner.RunWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringRunner

@SpringBootTest
@RunWith(SpringRunner::class)
@ContextConfiguration(classes = [(TestConfig::class)])
abstract class BaseTest
