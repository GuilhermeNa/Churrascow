package br.com.apps.churrascow.datasource.internal

import br.com.apps.churrascow.database.dao.ActionDao
import br.com.apps.churrascow.model.Action

class InternalActionDataSource (

    private val dao: ActionDao

) {

    suspend fun addAction(action: Action){
        dao.add(action)
    }

}