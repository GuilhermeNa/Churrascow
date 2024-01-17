package br.com.apps.churrascow.ui.fragments.formEvent

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.apps.churrascow.dto.EventDto
import br.com.apps.churrascow.useCase.EventUseCase
import br.com.apps.churrascow.util.toLocalDateTime
import java.time.LocalDateTime

class FormEventFragmentViewModel(

    val useCase: EventUseCase,

    ) : ViewModel() {

    /**
     * Is responsible for telling the UI the current selected date by the user.
     */
    private var _date = MutableLiveData(LocalDateTime.now())
    val date get() = _date

    /**
     * Is responsible for telling the UI the current selected url image.
     */
    private var _urlImage = MutableLiveData("")
    val urlImage get() = _urlImage

    //---------------------------------------------------------------------------------------------//
    // CLASS METHODS
    //---------------------------------------------------------------------------------------------//

    /**
     * Alter the mutableLiveData [_date] with a new date selected by the user.
     */
    fun newDateHasBeenSelected(selection: Long) {
        val newDate = selection.toLocalDateTime()
        val oldDate = _date.value

        if (newDate != oldDate) {
            _date.value = newDate
        }

    }

    /**
     * Alter the mutableLiveData [_urlImage] with url image selected by the user.
     */
    fun newImageHasBeenSelected(newUrl: String) {
        val oldUrl = _urlImage.value

        if (newUrl != oldUrl) {
            _urlImage.value = newUrl
        }

    }

    /**
     * When the user click on save button, starts the flow of creating a new Event.
     * @param eventDto with the data received by Ui.
     * @throws ObjectNotFoundException
     * @throws InvalidFormatException
     */
    suspend fun saveButtonClicked(eventDto: EventDto) {
        val event = useCase.createEvent(eventDto)
        useCase.addEvent(event)
    }

}