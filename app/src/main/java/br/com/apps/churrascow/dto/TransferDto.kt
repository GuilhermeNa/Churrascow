package br.com.apps.churrascow.dto

data class TransferDto(

    val id: String? = null,
    val eventId: String,
    val receiverId: String,
    val senderId: String,
    val value: String,

    )