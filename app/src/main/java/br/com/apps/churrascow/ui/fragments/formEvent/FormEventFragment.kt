package br.com.apps.churrascow.ui.fragments.formEvent

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import br.com.apps.churrascow.databinding.FragmentFormEventBinding
import br.com.apps.churrascow.dto.EventDto
import br.com.apps.churrascow.mapper.EventMapper
import br.com.apps.churrascow.ui.dialogs.EventFragmentPickImageDialog
import br.com.apps.churrascow.ui.fragments.baseFragment.BaseFragment
import br.com.apps.churrascow.ui.fragments.baseFragment.BaseFragmentV1
import br.com.apps.churrascow.util.controllerPopBackStack
import br.com.apps.churrascow.util.formatToString
import br.com.apps.churrascow.util.loadImageThroughUrl
import com.google.android.material.datepicker.MaterialDatePicker
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

private const val DATE_PICKER_TITLE = "Select a date for event"

private const val DATE_PICKER_TAG = "picker"

/**
 * A simple [Fragment] subclass.
 * Use the [FormEventFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FormEventFragment : BaseFragmentV1() {

    private var _binding: FragmentFormEventBinding? = null
    private val binding get() = _binding!!

    private val viewModel: FormEventFragmentViewModel by viewModel()

    //---------------------------------------------------------------------------------------------//
    // ON CREATE
    //---------------------------------------------------------------------------------------------//

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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
        initImageButton()
        initViewControler()
    }

    /**
     * Responsible for date text field.
     */
    private fun initDateView() {
        binding.fragmentFormEventDate.setOnClickListener {

            setClickRangeTimer(it, 1000)

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
    private fun initImageButton() {
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
    private fun initViewControler() {
        viewModel.date.observe(viewLifecycleOwner) {
            binding.fragmentFormEventDate.text = it.formatToString()
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
    // PUBLIC METHODS
    //---------------------------------------------------------------------------------------------//

    /**
     * Must be called by the activity, since the toolbar is being controlled by it.
     *
     * Responsible for create a new object when toolbar receive interaction for save.
     */
    fun toolbarSaveClick() {
        binding.fragmentFormEventEditText.error = null
        val eventDto = getDto()

        lifecycleScope.launch {
            try {
                viewModel.saveButtonClicked(eventDto)
                requireView().controllerPopBackStack()
            } catch (e: Exception) {
                e.message?.let { error -> notifyError(error) }
            }
        }

    }

    private fun notifyError(error: String) {
        binding.fragmentFormEventEditText.error = error
    }

    private fun getDto(): EventDto {
        val idUser = ""
        val title = binding.fragmentFormEventEditText.text.toString()
        val date = viewModel.date.value
        val urlImage = viewModel.urlImage.value

        return EventMapper.toDto(
            idUser = idUser,
            title = title,
            date = date,
            urlImage = urlImage
        )
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