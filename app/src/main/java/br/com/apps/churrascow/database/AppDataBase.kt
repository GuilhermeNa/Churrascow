package br.com.apps.churrascow.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import br.com.apps.churrascow.database.converter.ConverterActionSummary
import br.com.apps.churrascow.database.converter.ConverterLocalDateTime
import br.com.apps.churrascow.database.converter.ConvertersBigDecimal
import br.com.apps.churrascow.database.dao.ActionDao
import br.com.apps.churrascow.database.dao.EventDao
import br.com.apps.churrascow.database.dao.UserDao
import br.com.apps.churrascow.model.Action
import br.com.apps.churrascow.model.Event
import br.com.apps.churrascow.model.Guest
import br.com.apps.churrascow.model.User
import br.com.apps.churrascow.model.Expense
import br.com.apps.churrascow.model.ExpenseGenerator

const val DATABASE_NAME = "prod.db"

@Database(
    entities =
    [
        User::class,
        Event::class,
        Guest::class,
        Expense::class,
        ExpenseGenerator::class,
        Action::class
    ],
    version = 6,
    exportSchema = false
)
@TypeConverters(
    ConvertersBigDecimal::class,
    ConverterLocalDateTime::class,
    ConverterActionSummary::class
)
abstract class AppDataBase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun eventDao(): EventDao
    abstract fun actionDao(): ActionDao

    companion object {

        @Volatile
        private lateinit var db: AppDataBase

        fun getDb(context: Context): AppDataBase {

            if (::db.isInitialized) return db

            return Room.databaseBuilder(context, AppDataBase::class.java, DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .build().also { db = it }
        }
    }

}