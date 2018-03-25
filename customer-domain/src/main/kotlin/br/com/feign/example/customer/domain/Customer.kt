package br.com.feign.example.customer.domain

import br.com.feign.example.customer.domain.repository.CustomerRepository
import java.util.*
import javax.validation.constraints.NotNull

class Customer(
        @field:NotNull val id: Id?,
        val fullName: FullName? = null,
        val nickName: NickName? = null,
        val birthDate: BirthDate? = null,
        @field:NotNull val gender: Gender?,
        val civilState: CivilState? = null

) {

    var status: Customer.Status = Status.ACTIVATED

    fun create(repository: CustomerRepository) {
        repository.save(this)
    }

    fun update(repository: CustomerRepository) {
        repository.update(this)
    }

    fun updateStatus(status: Status, repository: CustomerRepository) {
        this.validateStatusChange(status)
        repository.updateStatus(this.id!!, status)
        this.status = status
    }

    fun delete(repository: CustomerRepository) {
        this.validateStatusToDelete()
        repository.delete(this.id!!)
    }

    //STATUS
    private fun validateStatusChange(status: Customer.Status) {
        if (Customer.Status.ACTIVATED == this.status) {
            if (Customer.Status.INACTIVATED != status) {
                throw Exception("GLOBAL_ERROR")
            }
        } else if (Customer.Status.INACTIVATED == this.status) {
            if (Customer.Status.ACTIVATED != status) {
                throw Exception("GLOBAL_ERROR")
            }
        } else {
            throw Exception("Status change not permitted")
        }
    }

    private fun validateStatusToDelete() {
        if(Customer.Status.ACTIVATED == this.status) {
            throw Exception("GLOBAL_ERROR")
        }
    }

    data class Id(val value: String = UUID.randomUUID().toString())

    enum class Status {

        ACTIVATED,
        INACTIVATED

    }
}