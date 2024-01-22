package br.com.apps.churrascow.datasource.internal

import br.com.apps.churrascow.database.dao.GuestDao
import br.com.apps.churrascow.model.Guest

class InternalGuestDataSource(

  private val dao: GuestDao

) {

    suspend fun addGuest(guest: Guest): Long {
        return dao.addGuest(guest)
    }

}