package br.com.apps.churrascow.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import br.com.apps.churrascow.database.converter.ConverterLocalDateTime
import br.com.apps.churrascow.database.converter.ConvertersBigDecimal
import br.com.apps.churrascow.database.dao.UserDao
import br.com.apps.churrascow.model.Event
import br.com.apps.churrascow.model.Participant
import br.com.apps.churrascow.model.User
import br.com.apps.churrascow.model.Expense
import br.com.apps.churrascow.model.ExpenseGenerator

const val DATABASE_NAME = "prod.db"
const val DATABASE_NAME_TEST = "test.db"

@Database(
    entities =
    [
        User::class,
        Event::class,
        Participant::class,
        Expense::class,
        ExpenseGenerator::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(
    ConvertersBigDecimal::class,
    ConverterLocalDateTime::class
)
abstract class AppDataBase : RoomDatabase() {

    abstract fun userDao(): UserDao

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