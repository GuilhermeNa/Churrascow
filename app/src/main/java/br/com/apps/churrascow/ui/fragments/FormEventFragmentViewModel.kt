package br.com.apps.churrascow.ui.fragments

import androidx.lifecycle.ViewModel
import br.com.apps.churrascow.dto.EventDto
import br.com.apps.churrascow.useCase.EventUseCase

class FormEventFragmentViewModel(

    val useCase: EventUseCase,


) : ViewModel() {

    suspend fun adicionar(eventDto: EventDto){

        val newEvent = useCase.mapToEvent(eventDto)

        useCase.newEvent(newEvent)

    }


}