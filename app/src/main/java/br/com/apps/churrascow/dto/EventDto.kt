package br.com.apps.churrascow.dto

data class EventDto(

    val idUser: String,
    val title: String,
    val description: String? = null,
    val date: String? = null

)