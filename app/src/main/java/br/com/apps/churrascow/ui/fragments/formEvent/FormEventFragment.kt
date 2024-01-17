package br.com.apps.churrascow.ui.fragments.formEvent

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import br.com.apps.churrascow.databinding.FragmentFormEventBinding
import br.com.apps.churrascow.dto.EventDto
import br.com.apps.churrascow.exception.BlankStringException
import br.com.apps.churrascow.exception.ObjectNotFoundException
import br.com.apps.churrascow.exception.StringTooBigException
import br.com.apps.churrascow.mapper.EventMapper
import br.com.apps.churrascow.ui.dialogs.EventFragmentPickImageDialog
import br.com.apps.churrascow.ui.fragments.baseFragment.BaseFragmentV1
import br.com.apps.churrascow.util.EMPTY_FIELD
import br.com.apps.churrascow.util.INTERNAL_ERROR
import br.com.apps.churrascow.util.STRING_TOO_BIG
import br.com.apps.churrascow.util.SUCCESSFULLY_CREATED
import br.com.apps.churrascow.util.TAG_TITLE
import br.com.apps.churrascow.util.USER_ERROR
import br.com.apps.churrascow.util.controllerPopBackStack
import br.com.apps.churrascow.util.formatToStringBr
import br.com.apps.churrascow.util.hideKeyboard
import br.com.apps.churrascow.util.loadImageThroughUrl
import br.com.apps.churrascow.util.snackBarGreen
import br.com.apps.churrascow.util.snackBarRed
import com.google.android.material.datepicker.MaterialDatePicker
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

private const val DATE_PICKER_TITLE = "Select a date for event"
private const val DATE_PICKER_TAG = "picker"
class FormEventFragment : BaseFragmentV1() {

    private var _binding: FragmentFormEventBinding? = null
    private val binding get() = _binding!!

    private val viewModel: FormEventFragmentViewModel by viewModel()

    //---------------------------------------------------------------------------------------------//
    // ON CREATE
    //---------------------------------------------------------------------------------------------//

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch {
            user.filterNotNull().collect {
                userId = it.email
            }
        }
    }

    //---------------------------------------------------------------------------------------------//
    // ON CREATE VIEW
    //---------------------------------------------------------------------------------------------//

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFormEventBinding.inflate(inflater, container, false)
        return binding.root
    }

    //---------------------------------------------------------------------------------------------//
    // ON VIEW CREATED
    //---------------------------------------------------------------------------------------------//

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initDateView()
        initImageView()
        initStateManager()
    }

    /**
     * Responsible for date text field behaviors, such as selecting a new date for an event through
     * a date picker.
     */
    private fun initDateView() {
        binding.fragmentFormEventDate.setOnClickListener {

            setClickRangeTimer(it, 1000)
            hideKeyboard()

            val datePicker =
                MaterialDatePicker.Builder.datePicker()
                    .setTitleText(DATE_PICKER_TITLE)
                    .build()

            datePicker.show(childFragmentManager, DATE_PICKER_TAG)

            datePicker.addOnPositiveButtonClickListener { selection ->
                viewModel.newDateHasBeenSelected(selection)
            }

        }
    }

    /**
     * Responsible for opening the dialog and manipulate the image.
     */
    private fun initImageView() {
        val dialog = EventFragmentPickImageDialog(requireContext())
        binding.fragmentFormEventImage.setOnClickListener {

            setClickRangeTimer(it, 1000L)

            dialog.show() { urlImage ->
                viewModel.newImageHasBeenSelected(urlImage)
            }

        }
    }

    /**
     * This method is responsible for manipulate the fragment's view.
     *
     * Observe any change in date.
     *
     */
    private fun initStateManager() {
        viewModel.date.observe(viewLifecycleOwner) {
            binding.fragmentFormEventDate.text = it.formatToStringBr()
        }

        viewModel.urlImage.observe(viewLifecycleOwner) {
            if (it.isNotBlank()) {
                binding.fragmentFormEventImage
                    .loadImageThroughUrl(it, requireContext())
            }
        }

    }

    //---------------------------------------------------------------------------------------------//
    // ON DESTROY VIEW
    //---------------------------------------------------------------------------------------------//

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    //---------------------------------------------------------------------------------------------//
    // INTERFACES
    //---------------------------------------------------------------------------------------------//

    override fun idConsumer(id: String) {

    }

    //---------------------------------------------------------------------------------------------//
    // PUBLIC METHODS
    //---------------------------------------------------------------------------------------------//

    /**
     * Must be called by the activity, since the toolbar is being controlled by it.
     *
     * Responsible for create a new object when toolbar receive interaction for save.
     */
    fun toolbarSaveClick() {
        binding.fragmentFormEventEditText.error = null
        binding.fragmentFormEventDescriptionEditText.error = null

        val eventDto = getDto()

        lifecycleScope.launch {
            try {
                viewModel.saveButtonClicked(eventDto)
                requireView().snackBarGreen(SUCCESSFULLY_CREATED)
                requireView().controllerPopBackStack()
            } catch (e: Exception) {
                e.printStackTrace()
                threatException(e)
                requireView().snackBarRed(USER_ERROR)
            }
        }

    }

    private fun getDto(): EventDto {
        return EventMapper.toDto(
            idUser = userId,
            title = binding.fragmentFormEventEditText.text.toString(),
            description = binding.fragmentFormEventDescriptionEditText.text.toString(),
            date = viewModel.date.value,
            urlImage = viewModel.urlImage.value
        )
    }

    private fun threatException(e: Exception) {
        when (e) {
            is ObjectNotFoundException -> {
                requireView().snackBarRed(INTERNAL_ERROR)
            }

            is BlankStringException -> {
                binding.fragmentFormEventEditText.error = EMPTY_FIELD
            }

            is StringTooBigException -> {
                if(e.message == TAG_TITLE) {
                    binding.fragmentFormEventEditText.error = STRING_TOO_BIG
                } else {
                    binding.fragmentFormEventEditText.error = STRING_TOO_BIG
                }
            }

        }
    }

    /**
     * Must be called by the activity, since the toolbar is being controlled by it.
     *
     * Responsible for popBackStack when user interact with home button.
     */
    fun toolbarBackClick() {
        requireView().controllerPopBackStack()
    }


}