package br.com.apps.churrascow.dto

data class ExpenseDto(

    val id: String? = null,
    val eventId: String,
    val guestId: String,
    val name: String,
    val value: String,
    val ticket: String

)