package br.com.feign.example.customer.repository.mapper

import br.com.feign.example.customer.domain.*
import br.com.feign.example.customer.repository.JdbcCustomerRepository
import org.springframework.jdbc.core.RowMapper
import java.sql.ResultSet

class CustomerMapper : RowMapper<Customer> {

    lateinit var customer: Customer

    override fun mapRow(rs: ResultSet, rowNum: Int): Customer {

        val id = rs.getString(JdbcCustomerRepository.ID_COLUMN)
        val fullName = rs.getString(JdbcCustomerRepository.FULL_NAME_COLUMN)
        val nickName = rs.getString(JdbcCustomerRepository.NICK_NAME_COLUMN)
        val birthDate = rs.getString(JdbcCustomerRepository.BIRTH_DATE_COLUMN)
        val gender = rs.getString(JdbcCustomerRepository.GENDER_COLUMN)
        val civilState = rs.getString(JdbcCustomerRepository.CIVIL_STATE_COLUMN)
        val status = rs.getString(JdbcCustomerRepository.STATUS_COLUMN)

        customer = Customer(
                id = Customer.Id(id),
                fullName = FullName(fullName),
                nickName = NickName(nickName),
                birthDate = BirthDate(birthDate),
                civilState = CivilState(civilState),
                gender = Gender(gender)
        )

        customer.status = Customer.Status.valueOf(status)

        return customer

    }
}