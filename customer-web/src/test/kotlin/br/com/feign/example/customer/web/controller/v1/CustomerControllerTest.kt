package br.com.feign.example.customer.web.controller.v1

import br.com.feign.example.customer.api.v1.representation.CustomerRepresentation
import br.com.feign.example.customer.api.v1.request.UpdateCustomerRequest
import br.com.feign.example.customer.api.v1.request.UpdateStatusRequest
import br.com.feign.example.customer.domain.Customer
import br.com.feign.example.customer.domain.randomUUID
import br.com.feign.example.customer.infrastructure.jsonToObject
import br.com.feign.example.customer.infrastructure.objectToJson
import br.com.feign.example.customer.web.ControllerBaseTest
import org.hamcrest.CoreMatchers.notNullValue
import org.junit.Test
import org.springframework.http.MediaType
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.patch
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.put
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class CustomerControllerTest : ControllerBaseTest() {

    @Test
    fun `should create customer`() {
        val request = buildCreateCustomerRequest()

        this.mockMvc.perform(
            post(
                "/v1/customers"
            )
                .content(request.objectToJson())
                .contentType(MediaType.APPLICATION_JSON_UTF8)
        )
            .andExpect(status().isCreated)
            .andExpect(jsonPath("$.id", notNullValue()))
            .andDo {
                val response = it.response.contentAsString.jsonToObject(CustomerRepresentation::class.java)
                assertNotNull(response)
                assertEquals(request.fullName, response.fullName)
                assertEquals(request.nickName, response.nickName)
                assertEquals(request.gender, response.gender)
                assertEquals(request.birthDate, response.birthDate)
                assertEquals(request.civilState, response.civilState)
                assertEquals(Customer.Status.ACTIVE.name, response.status)
            }
    }

    @Test
    fun `should find customer`() {
        val customer = requestToCreateCustomer()

        this.mockMvc.perform(
            get(
                "/v1/customers/{id}", customer.id
            )
        )
            .andExpect(status().isOk)
            .andDo {
                val response = it.response.contentAsString.jsonToObject(CustomerRepresentation::class.java)
                assertNotNull(response)
                assertEquals(customer.fullName, response.fullName)
                assertEquals(customer.nickName, response.nickName)
                assertEquals(customer.gender, response.gender)
                assertEquals(customer.birthDate, response.birthDate)
                assertEquals(customer.civilState, response.civilState)
                assertEquals(Customer.Status.ACTIVE.name, response.status)
            }
    }

    @Test
    fun `shouldn't find customer when he not exists`() {
        val id = randomUUID()

        this.mockMvc.perform(
            get(
                "/v1/customers/{id}", id
            )
        )
            .andExpect(status().isNotFound)
    }

    @Test
    fun `should update customer`() {
        val customer = requestToCreateCustomer()

        val request = UpdateCustomerRequest(
            fullName = "Ricardo M Borges"
        )

        this.mockMvc.perform(
            put(
                "/v1/customers/{id}", customer.id
            )
                .content(request.objectToJson())
                .contentType(MediaType.APPLICATION_JSON_UTF8)
        )
            .andExpect(status().isOk)
            .andDo {
                val response = it.response.contentAsString.jsonToObject(CustomerRepresentation::class.java)
                assertNotNull(response)
                assertEquals(request.fullName, response.fullName)
                assertEquals(customer.nickName, response.nickName)
                assertEquals(customer.gender, response.gender)
                assertEquals(customer.birthDate, response.birthDate)
                assertEquals(customer.civilState, response.civilState)
                assertEquals(Customer.Status.ACTIVE.name, response.status)
            }
    }

    @Test
    fun `should delete customer`() {
        val customer = requestToCreateCustomer()

        requestToUpdateCustomerStatus(
            customerId = customer.id!!, request = UpdateStatusRequest(status = "INACTIVE")
        )

        this.mockMvc.perform(
            delete(
                "/v1/customers/{id}", customer.id
            )
        )
            .andExpect(status().isNoContent)
    }

    @Test
    fun `should update customer status`() {
        val customer = requestToCreateCustomer()

        val request = UpdateStatusRequest(
            status = "INACTIVE"
        )

        this.mockMvc.perform(
            patch(
                "/v1/customers/{id}/status", customer.id
            )
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(request.objectToJson())
        )
            .andExpect(status().isNoContent)
    }

}