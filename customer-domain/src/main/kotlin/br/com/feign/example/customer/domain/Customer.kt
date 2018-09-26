package br.com.feign.example.customer.domain

import br.com.feign.example.customer.domain.repository.CustomerRepository
import br.com.feign.example.customer.infrastructure.exception.CustomerErrorCode
import br.com.feign.example.global.exception.BusinessException
import java.util.*

class Customer(
    val id: Id,
    val fullName: FullName,
    val nickName: NickName? = null,
    val birthDate: BirthDate? = null,
    val gender: Gender,
    val civilState: CivilState? = null
) {

    var status: Customer.Status = Status.ACTIVE

    fun create(repository: CustomerRepository) {
        repository.save(this)
    }

    fun update(repository: CustomerRepository, oldCustomer: Customer) {
        this.status = oldCustomer.status
        repository.update(this)
    }

    fun updateStatus(status: Status, repository: CustomerRepository) {
        this.validateStatusChange(status)
        repository.updateStatus(this.id, status)
        this.status = status
    }

    fun delete(repository: CustomerRepository) {
        this.validateStatusToDelete()
        repository.delete(this.id)
    }

    fun validate() {
        this.validateStatus()
    }

    //STATUS
    private fun validateStatusChange(status: Customer.Status) {
        if (Customer.Status.ACTIVE == this.status) {
            if (Customer.Status.INACTIVE != status) {
                throw BusinessException(CustomerErrorCode.CHANGE_STATUS_NOT_ALLOWED)
            }
        } else if (Customer.Status.INACTIVE == this.status) {
            if (Customer.Status.ACTIVE != status) {
                throw BusinessException(CustomerErrorCode.CHANGE_STATUS_NOT_ALLOWED)
            }
        } else {
            throw BusinessException(CustomerErrorCode.CHANGE_STATUS_NOT_ALLOWED)
        }
    }

    private fun validateStatus() {
        if (Customer.Status.ACTIVE != this.status) {
            throw BusinessException(CustomerErrorCode.CUSTOMER_IS_NOT_ACTIVE)
        }
    }

    private fun validateStatusToDelete() {
        if (Customer.Status.ACTIVE == this.status) {
            throw BusinessException(CustomerErrorCode.STATUS_INVALID_TO_DELETE)
        }
    }

    data class Id(val value: String = UUID.randomUUID().toString())

    enum class Status {
        ACTIVE,
        INACTIVE
    }
}