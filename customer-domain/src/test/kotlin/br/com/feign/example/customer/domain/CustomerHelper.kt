package br.com.feign.example.customer.domain

fun createBasicCustomer(id: Customer.Id = Customer.Id(),
                   fullName: FullName = FullName("Full Name Customer"),
                   nickName: NickName? = NickName("Customer"),
                   birthDate: BirthDate? = BirthDate("01/01/1990"),
                   civilState: CivilState? = CivilState("Married"),
                   gender: Gender? = Gender("Male")) =
        Customer(
                id = id,
                fullName = fullName,
                nickName = nickName,
                birthDate = birthDate,
                civilState = civilState,
                gender = gender
        )