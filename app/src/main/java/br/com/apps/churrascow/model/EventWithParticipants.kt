package br.com.apps.churrascow.model

import androidx.room.Embedded
import androidx.room.Relation

class EventWithParticipants(

    @Embedded val event: Event,

    @Relation(parentColumn = "id", entityColumn = "eventId")
    val guests: List<Guest>

)