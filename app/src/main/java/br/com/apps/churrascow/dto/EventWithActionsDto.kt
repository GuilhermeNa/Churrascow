package br.com.apps.churrascow.dto

data class EventWithActionsDto(
    val event: EventDto,
    val actions: List<ActionDto>
)