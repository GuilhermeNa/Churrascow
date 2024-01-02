package br.com.apps.churrascow.ui.fragments.formEvent

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import br.com.apps.churrascow.databinding.FragmentFormEventBinding
import br.com.apps.churrascow.ui.fragments.BaseFragment
import br.com.apps.churrascow.util.toLocalDateTime
import com.google.android.material.datepicker.MaterialDatePicker
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.time.LocalDateTime
import java.time.format.TextStyle
import java.util.Locale


/**
 * A simple [Fragment] subclass.
 * Use the [FormEventFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FormEventFragment : BaseFragment() {

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


    }

    /**
     * Responsible for date text field.
     */
    private fun initDateView() {
        binding.fragmentFormEventDate.apply {
            val dateString = getDate()
            text = dateString

            setOnClickListener {

                setClickRangeTimer(it, 1000)
                val datePicker =
                    MaterialDatePicker.Builder.datePicker()
                        .setTitleText("Select a date for event")
                        .build()
                datePicker.show(childFragmentManager, "picker")
                datePicker.addOnPositiveButtonClickListener { selection ->
                    val newDate = selection.toLocalDateTime()

                }
            }
        }
    }

    private fun getDate(): String {
        val dateTimeNow = LocalDateTime.now()
        val dayOfWeek = dateTimeNow.dayOfMonth
        val monthOfYear = dateTimeNow.month
        val year = dateTimeNow.year

        val locale = Locale("pt", "BR")
        val monthInPtBr = monthOfYear.getDisplayName(TextStyle.FULL, locale)
        val month =
            monthInPtBr.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() }

        return "$dayOfWeek, de $month de $year"
    }

    //---------------------------------------------------------------------------------------------//
    // ON DESTROY VIEW
    //---------------------------------------------------------------------------------------------//

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}