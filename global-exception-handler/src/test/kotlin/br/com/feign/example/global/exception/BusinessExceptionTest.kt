package br.com.feign.example.global.exception

import br.com.feign.example.global.exception.error.ErrorCode
import org.junit.Assert
import org.junit.Test

class BusinessExceptionTest {

    @Test
    fun throwBusinessExceptionWithoutErrorCode() {
        try {
            throw BusinessException()
        } catch (e: BusinessException) {
            Assert.assertNotNull(e)
            Assert.assertNull(e.errorCode)
        }
    }

    @Test
    fun throwBusinessExceptionWithErrorCode() {
        try {
            throw BusinessException(ErrorCode("exists.id", getBusinessMessage()))
        } catch (e: BusinessException) {
            Assert.assertNotNull(e)
            Assert.assertNotNull(e.errorCode)
            Assert.assertEquals(e.errorCode?.message, getBusinessMessage())
        }

    }

    private fun getBusinessMessage() = "This id already exists."

}