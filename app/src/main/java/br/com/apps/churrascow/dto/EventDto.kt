package br.com.apps.churrascow.dto

data class EventDto(

    val idUser: String,
    val id: String? = null,

    val title: String,
    val description: String? = null,
    var date: String? = null,
    var guests: String? = null,
    var urlImage: String? = null

)