package br.com.apps.churrascow.ui.fragments.formEvent

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import br.com.apps.churrascow.databinding.FragmentFormEventBinding
import br.com.apps.churrascow.ui.fragments.BaseFragment

/**
 * A simple [Fragment] subclass.
 * Use the [FormEventFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FormEventFragment : BaseFragment() {

    private var _binding: FragmentFormEventBinding? = null
    private val binding get() = _binding!!



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



    }


}