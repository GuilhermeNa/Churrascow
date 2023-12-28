package br.com.apps.churrascow.model

import androidx.room.PrimaryKey
import java.math.BigDecimal

data class Action(

    @PrimaryKey(autoGenerate = true)
    val id: Long? = null,
    val eventId: Long,
    val participantId: Long? = null,

    val description: String,
    val value: BigDecimal? = null

)