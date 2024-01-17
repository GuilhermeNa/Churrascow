package br.com.apps.churrascow.database.converter

import androidx.room.TypeConverter
import br.com.apps.churrascow.model.ActionSummary
import br.com.apps.churrascow.util.toActionSummary
import br.com.apps.churrascow.util.toStringValue

class ConverterActionSummary {

    @TypeConverter
    fun toActionSummary(string: String?): ActionSummary? {
        return string?.toActionSummary()
    }

    @TypeConverter
    fun toString(actionSummary: ActionSummary?): String? {
        return actionSummary?.toStringValue()
    }


}