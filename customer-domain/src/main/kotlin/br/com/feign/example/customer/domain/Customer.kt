package br.com.feign.example.customer.domain

import br.com.feign.example.customer.domain.repository.CustomerRepository
import java.util.*
import javax.validation.constraints.NotNull

class Customer(
        @field:NotNull val id: Id,
        val fullName: FullName? = null,
        val nickName: NickName? = null,
        val birthDate: BirthDate? = null,
        @field:NotNull val gender: Gender,
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
                throw Exception("GLOBAL_ERROR")
            }
        } else if (Customer.Status.INACTIVE == this.status) {
            if (Customer.Status.ACTIVE != status) {
                throw Exception("GLOBAL_ERROR")
            }
        } else {
            throw Exception("Status change not permitted")
        }
    }

    private fun validateStatus() {
        if (Customer.Status.ACTIVE != this.status) {
            throw Exception("GLOBAL_ERROR")
        }
    }

    private fun validateStatusToDelete() {
        if (Customer.Status.ACTIVE == this.status) {
            throw Exception("GLOBAL_ERROR")
        }
    }

    data class Id(val value: String = UUID.randomUUID().toString())

    enum class Status {

        ACTIVE,
        INACTIVE

    }
}