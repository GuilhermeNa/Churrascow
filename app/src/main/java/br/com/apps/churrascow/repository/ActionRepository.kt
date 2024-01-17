package br.com.apps.churrascow.repository

import br.com.apps.churrascow.datasource.external.ExternalActionDataSource
import br.com.apps.churrascow.datasource.internal.InternalActionDataSource
import br.com.apps.churrascow.model.Action

class ActionRepository (

    private val internalDataSource: InternalActionDataSource,
    private val externalDataSource: ExternalActionDataSource

){

    suspend fun addAction(action: Action){
        internalDataSource.addAction(action)
    }

}