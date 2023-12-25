package br.com.apps.churrascow.mapper

import br.com.apps.churrascow.dto.RegistrationDto
import br.com.apps.churrascow.model.User

class RegistrationMapper {

    companion object {
        fun toUser(registrationDto: RegistrationDto): User {
           return User(
               email = registrationDto.email,
               name = registrationDto.name,
               password = registrationDto.password
           )
        }
    }

}