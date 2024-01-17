package br.com.apps.churrascow.ui.fragments.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.apps.churrascow.dto.EventWithActionsDto
import br.com.apps.churrascow.mapper.EventWithActionsMapper
import br.com.apps.churrascow.useCase.EventUseCase
import kotlinx.coroutines.launch

class HomeFragmentViewModel(

    private val useCase: EventUseCase

) : ViewModel() {

    private var _eventWithActionsDataSet = MutableLiveData<List<EventWithActionsDto>>(null)
    val eventWithActionsDataSet get() = _eventWithActionsDataSet

    //---------------------------------------------------------------------------------------------//
    // METHODS
    //---------------------------------------------------------------------------------------------//

    /**
     * Observe data from repository.
     *
     * @param userId user's email
     */
    fun loadEventsAndActions(userId: String) {
        viewModelScope.launch {
            useCase.loadEventsWithActions(userId).collect {
                val eventWithActionsDto = EventWithActionsMapper.toDto(it)
                _eventWithActionsDataSet.value = eventWithActionsDto
            }
        }
    }

}