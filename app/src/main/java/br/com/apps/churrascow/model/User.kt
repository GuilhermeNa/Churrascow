package br.com.apps.churrascow.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    indices = [
        Index(value = ["email"], unique = true)
    ]
)
data class User(

    @PrimaryKey
    val email: String,

    val name: String,
    val password: String

)