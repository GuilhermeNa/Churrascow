package br.com.apps.churrascow.database.converter

import androidx.room.TypeConverter
import java.math.BigDecimal

class ConvertersBigDecimal {

    @TypeConverter
    fun doubleToBd(value: Double?): BigDecimal {
        return value?.let { BigDecimal(value.toString()) } ?: BigDecimal.ZERO
    }

    @TypeConverter
    fun bdToDouble(value: BigDecimal?): Double? {
        return value?.let { value.toDouble() }
    }

}
