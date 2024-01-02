package br.com.apps.churrascow.ui.fragments.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import br.com.apps.churrascow.databinding.FragmentProfileBinding
import br.com.apps.churrascow.ui.fragments.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * A simple [Fragment] subclass.
 * Use the [ProfileFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProfileFragment : BaseFragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ProfileFragmentViewModel by viewModel()

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
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    //---------------------------------------------------------------------------------------------//
    // ON VIEW CREATED
    //---------------------------------------------------------------------------------------------//

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    //---------------------------------------------------------------------------------------------//
    // ON DESTROY VIEW
    //---------------------------------------------------------------------------------------------//

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}