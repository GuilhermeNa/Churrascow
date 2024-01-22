package br.com.apps.churrascow.repository

import br.com.apps.churrascow.datasource.external.ExternalGuestDataSource
import br.com.apps.churrascow.datasource.internal.InternalGuestDataSource
import br.com.apps.churrascow.model.Guest

class GuestRepository(

    private val internalDataSource: InternalGuestDataSource,
    private val externalDataSource: ExternalGuestDataSource

) {

    suspend fun addGuest(guest: Guest): Long {
        return internalDataSource.addGuest(guest)
    }

}