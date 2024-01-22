package br.com.apps.churrascow.dto

data class GuestDto(

    val id: String? = null,
    val eventId: String,

    val name: String,
    val icon: String? = null

)