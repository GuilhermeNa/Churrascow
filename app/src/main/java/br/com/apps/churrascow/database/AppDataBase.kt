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
import br.com.apps.churrascow.database.dao.ExpenseDao
import br.com.apps.churrascow.database.dao.GuestDao
import br.com.apps.churrascow.database.dao.TransferDao
import br.com.apps.churrascow.database.dao.UserDao
import br.com.apps.churrascow.model.Action
import br.com.apps.churrascow.model.Event
import br.com.apps.churrascow.model.Expense
import br.com.apps.churrascow.model.Guest
import br.com.apps.churrascow.model.Transfer
import br.com.apps.churrascow.model.User

const val DATABASE_NAME = "prod.db"

@Database(
    entities =
    [
        User::class,
        Event::class,
        Guest::class,
        Expense::class,
        Action::class,
        Transfer::class
    ],
    version = 8,
    exportSchema = false
)
@TypeConverters(
    ConvertersBigDecimal::class,
    ConverterLocalDateTime::class,
    ConverterActionSummary::class
)
abstract class AppDataBase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun guestDao(): GuestDao
    abstract fun eventDao(): EventDao
    abstract fun actionDao(): ActionDao
    abstract fun expenseDao(): ExpenseDao
    abstract fun transferDao(): TransferDao

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