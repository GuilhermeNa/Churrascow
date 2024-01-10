package br.com.apps.churrascow.ui.fragments.register

import android.database.sqlite.SQLiteConstraintException
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.Navigation
import br.com.apps.churrascow.R
import br.com.apps.churrascow.databinding.FragmentRegisterBinding
import br.com.apps.churrascow.dto.RegistrationDto
import br.com.apps.churrascow.ui.fragments.baseFragment.BaseFragment
import br.com.apps.churrascow.useCase.BAD_PASSWORD
import br.com.apps.churrascow.useCase.EMPTY_PASSWORD
import br.com.apps.churrascow.useCase.UNCONFIRMED_PASSWORD
import br.com.apps.churrascow.util.snackBarGreen
import br.com.apps.churrascow.util.snackBarRed
import br.com.apps.churrascow.util.toast
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

private const val CREATION_SUCCEED = "Usuário criado com sucesso"
private const val CREATION_FAILED = "Verifique os campos e tente novamente"

private const val EMAIL_ALREADY_EXISTS = "Email já cadastrado"

class RegisterFragment : BaseFragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    private lateinit var navController: NavController
    private val viewModel: RegisterFragmentViewModel by viewModel()

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
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    //---------------------------------------------------------------------------------------------//
    // ON VIEW CREATED
    //---------------------------------------------------------------------------------------------//

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        initRegisterBtn()
        initSupportBtn()
    }

    /**
     * Responsible for Register Button behavior and set its text.
     *
     * Create a data transfer object with view's data, check if is what we expect and redirect the flow
     * in 2 ways:
     *
     * 1 - if the data isn't what we expected for an user, show errors;
     *
     * 2 - if data is what we expected for an user, create an user;
     */
    private fun initRegisterBtn() {
        binding.registerFragmentBtn.button.apply {

            text = resources.getString(R.string.register)

            setOnClickListener {
                cleanErrors()
                val registrationDto = getRegistrationDto()
                lifecycleScope.launch {
                    registrationDto?.let { dto ->

                        try {

                            viewModel.registerBtnClicked(dto)?.let { errors ->
                                creationFailed(errors)
                            } ?: creationSucceed()

                        } catch (e: SQLiteConstraintException) {

                            binding
                                .registerFragmentRegisterPanel
                                .registerPanelEditTextEmail.error =
                                EMAIL_ALREADY_EXISTS

                        }
                    }
                }
            }
        }
    }

    private fun cleanErrors() {
        binding.registerFragmentRegisterPanel.apply {
            registerPanelEditTextName.error = null
            registerPanelEditTextEmail.error = null
            registerPanelEditTextPassword.error = null
            registerPanelEditTextPasswordConfirmation.error = null
        }
    }

    private fun getRegistrationDto(): RegistrationDto? {
        val registrationDto: RegistrationDto?
        binding.registerFragmentRegisterPanel.apply {
            registrationDto = RegistrationDto(
                registerPanelEditTextName.text.toString(),
                registerPanelEditTextEmail.text.toString(),
                registerPanelEditTextPassword.text.toString(),
                registerPanelEditTextPasswordConfirmation.text.toString()
            )
        }
        return registrationDto
    }

    private fun creationFailed(errors: Triple<String, String, String>) {
        requireView().snackBarRed(CREATION_FAILED)
        binding.registerFragmentRegisterPanel.apply {

            if (errors.first.isNotBlank()) {
                registerPanelEditTextName.error = errors.first
            }
            if (errors.second.isNotBlank()) {
                registerPanelEditTextEmail.error = errors.second
            }
            if (errors.third.isNotBlank()) {
                when (errors.third.trim()) {
                    EMPTY_PASSWORD -> {
                        registerPanelEditTextPassword.error = errors.third
                    }

                    BAD_PASSWORD -> {
                        registerPanelEditTextPassword.error = errors.third
                    }

                    UNCONFIRMED_PASSWORD -> {
                        registerPanelEditTextPassword.error = errors.third
                        registerPanelEditTextPasswordConfirmation.error = errors.third
                    }
                }
            }
        }
    }

    private fun creationSucceed() {
        requireView().snackBarGreen(CREATION_SUCCEED)
        navController.popBackStack()
    }

    /**
     * Responsible for Support Button behavior, navigate to another fragment responsible for
     * support.
     */
    private fun initSupportBtn() {
        binding.registerSupport.setOnClickListener {
            requireContext().toast("Not implemented yet!")
        }
    }

    //---------------------------------------------------------------------------------------------//
    // ON DESTROY VIEW
    //---------------------------------------------------------------------------------------------//

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}