package br.com.apps.churrascow.useCase

import br.com.apps.churrascow.dto.ActionDto
import br.com.apps.churrascow.exception.ObjectNotFoundException
import br.com.apps.churrascow.factory.ActionFactory
import br.com.apps.churrascow.model.Action
import br.com.apps.churrascow.model.ActionEnum
import br.com.apps.churrascow.repository.ActionRepository

class ActionUseCase(

    private val repository: ActionRepository

) {

    /**
     * Responsible for the flow of creating and adding a new action. It will receive a dto,
     * validate its fields and then add the new action.
     *
     * @param [actionDto] is the data of whoever requested the creation of an action.
     * @param [actionEnum] is the class from which the request came.
     *
     * @throws ObjectNotFoundException
     * @throws BlankStringException
     * @throws InvalidEnumReferenceException
     */
    suspend fun registerAnAction(actionDto: ActionDto?, actionEnum: ActionEnum) {
        val action = createAction(actionDto, actionEnum)
        addAction(action)
    }

    private fun createAction(actionDto: ActionDto?, actionEnum: ActionEnum): Action {
        return ActionFactory.createObject(actionDto, actionEnum)
    }

    private suspend fun addAction(action: Action) {
        repository.addAction(action)
        ActionEnum.EVENT
    }

}