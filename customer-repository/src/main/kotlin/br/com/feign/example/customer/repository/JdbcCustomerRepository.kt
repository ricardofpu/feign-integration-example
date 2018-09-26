package br.com.feign.example.customer.repository

import br.com.feign.example.customer.domain.Customer
import br.com.feign.example.customer.domain.repository.CustomerRepository
import br.com.feign.example.customer.repository.mapper.CustomerMapper
import org.apache.logging.log4j.LogManager
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.DuplicateKeyException
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository

@Repository
open class JdbcCustomerRepository @Autowired constructor(private val jdbcTemplate: JdbcTemplate) : CustomerRepository {

    private val logger = LogManager.getLogger(this.javaClass)

    companion object {
        const val TABLE_NAME = "customer"
        const val ID_COLUMN = "id"
        const val FULL_NAME_COLUMN = "full_name"
        const val NICK_NAME_COLUMN = "nick_name"
        const val BIRTH_DATE_COLUMN = "birth_date"
        const val GENDER_COLUMN = "gender"
        const val CIVIL_STATE_COLUMN = "civil_state"
        const val STATUS_COLUMN = "status"
        const val CREATED_AT_COLUMN = "created_at"
        const val UPDATED_AT_COLUMN = "updated_at"
    }

    override fun find(customerId: Customer.Id): Customer? {
        var customer: Customer? = null

        val sql = """select * from $TABLE_NAME where $ID_COLUMN = ?"""

        try {
            customer = jdbcTemplate.queryForObject(sql, CustomerMapper(), customerId.value)
        } catch (e: EmptyResultDataAccessException) {
        }

        return customer
    }

    override fun save(customer: Customer): Int {
        val sql = """
              insert into $TABLE_NAME ($ID_COLUMN, $FULL_NAME_COLUMN, $NICK_NAME_COLUMN, $BIRTH_DATE_COLUMN,
                $GENDER_COLUMN, $CIVIL_STATE_COLUMN, $STATUS_COLUMN, $CREATED_AT_COLUMN)
              values (?, ?, ?, ?, ?, ?, ?, now())
                  """
        return try {
            jdbcTemplate.update(
                sql, customer.id.value,
                customer.fullName.value,
                customer.nickName?.value,
                customer.birthDate?.value,
                customer.gender.value,
                customer.civilState?.value,
                customer.status.name
            )
        } catch (e: DuplicateKeyException) {
            logger.warn("Fail to save Customer, key duplicate for id: {}", customer.id.value, e)
            0
        }
    }

    override fun update(customer: Customer): Int {
        val sql = """
                    update $TABLE_NAME
                    set $FULL_NAME_COLUMN = ?,
                        $NICK_NAME_COLUMN = ?,
                        $BIRTH_DATE_COLUMN = ?,
                        $GENDER_COLUMN = ?,
                        $CIVIL_STATE_COLUMN = ?,
                        $UPDATED_AT_COLUMN = now()
                    where $ID_COLUMN = ?
                """
        return jdbcTemplate.update(
            sql,
            customer.fullName.value,
            customer.nickName?.value,
            customer.birthDate?.value,
            customer.gender.value,
            customer.civilState?.value,
            customer.id.value
        )
    }

    override fun updateStatus(customerId: Customer.Id, status: Customer.Status): Int {
        val sql = """
                    update $TABLE_NAME
                      set $STATUS_COLUMN = ?,
                          $UPDATED_AT_COLUMN = now()
                      where $ID_COLUMN = ?
                """
        return jdbcTemplate.update(sql, status.name, customerId.value)
    }

    override fun delete(customerId: Customer.Id): Int {
        val sql = """
                    delete from $TABLE_NAME where $ID_COLUMN = ?
                """
        return jdbcTemplate.update(sql, customerId.value)
    }
}