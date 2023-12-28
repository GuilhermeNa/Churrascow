package br.com.apps.churrascow.ui.fragments.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.apps.churrascow.model.Event
import br.com.apps.churrascow.useCase.EventUseCase
import kotlinx.coroutines.launch

class HomeFragmentViewModel(

    private val useCase: EventUseCase

): ViewModel() {

    private var _eventDate = MutableLiveData<String>("teste de abril")
    val eventDate get() = _eventDate

    private var _eventsDataSet = MutableLiveData<List<Event>>(null)
    val eventsDataSet get() = _eventsDataSet

    //---------------------------------------------------------------------------------------------//
    // METHODS
    //---------------------------------------------------------------------------------------------//

    /**
     * Observe data from repository.
     *
     * @param userId user's email
     */
    fun loadEventsByUserId(userId: String){
        viewModelScope.launch {
            useCase.loadEventsByUserId(userId).collect {
                //todo fazer o mapeamento de todos os dados necessarios para a view
                _eventsDataSet.value = it
            }
        }
    }

}