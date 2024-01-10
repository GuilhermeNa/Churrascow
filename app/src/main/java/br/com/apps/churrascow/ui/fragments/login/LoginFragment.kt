package br.com.apps.churrascow.ui.fragments.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.datastore.dataStore
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.Navigation
import br.com.apps.churrascow.databinding.FragmentLoginBinding
import br.com.apps.churrascow.preferences.dataStore
import br.com.apps.churrascow.preferences.rememberPassword
import br.com.apps.churrascow.preferences.userLogged
import br.com.apps.churrascow.ui.activities.MainActivity
import br.com.apps.churrascow.ui.fragments.baseFragment.BaseFragment
import br.com.apps.churrascow.util.hideKeyboard
import br.com.apps.churrascow.util.navigateTo
import br.com.apps.churrascow.util.snackBarRed
import br.com.apps.churrascow.util.toast
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

private const val AUTHENTICATION_ERROR = "Usuário e/ou senha inválidos"

class LoginFragment : BaseFragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private lateinit var navController: NavController
    private val viewModel: LoginFragmentViewModel by viewModel()

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
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    //---------------------------------------------------------------------------------------------//
    // ON VIEW CREATED
    //---------------------------------------------------------------------------------------------//

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(requireView())
        initAccessBtn()
        initLoginBtn()
        initLoginPanelBackBtn()
        initRegisterBtn()
        initSupportBtn()
        initForgottenPasswordBtn()
        initCheckBoxRememberPassword()
        initViewController()
    }

    /**
     * Responsible for Access Button behavior, it notifies the ViewModel to change view state.
     */
    private fun initAccessBtn() {
        binding.apply {
            loginFragmentBtnAccess.setOnClickListener {
                setClickRangeTimer(it, 1000)
                viewModel.accessBtnCLicked()
            }
        }
    }

    /**
     * Responsible for Login Button behavior. Get the credentials in edit text views and send them
     * to a check. If they have a good format, send to authentication.
     */
    private fun initLoginBtn() {
        binding.loginFragmentLoginPanel.loginPanelLoginBtn
            .setOnClickListener {
                setClickRangeTimer(it, 1000)

                val credentials = getCredentials()

                val credentialsAreGood = checkCredentials(credentials)

                if (credentialsAreGood) {
                    authenticate(credentials)
                }
            }
    }

    private fun getCredentials(): Pair<String, String> {
        binding.loginFragmentLoginPanel.apply {
            return Pair(
                loginPanelEditTextEmail.text.toString().trim(),
                loginPanelEditTextPassword.text.toString().trim()
            )
        }
    }

    private fun checkCredentials(credentials: Pair<String, String>): Boolean {
        var fieldsAreCorrectlyFilled = true

        viewModel.checkCredentials(credentials)?.let { errors ->
            credentialCheckError(errors)
            fieldsAreCorrectlyFilled = false
        }

        return fieldsAreCorrectlyFilled
    }

    private fun credentialCheckError(errors: Pair<String, String>) {
        binding.loginFragmentLoginPanel.apply {
            if (errors.first.isNotBlank()) {
                loginPanelEditTextEmail.error = errors.first
            }
            if (errors.second.isNotBlank()) {
                loginPanelEditTextPassword.error = errors.second
            }
        }
    }

    private fun authenticate(credentials: Pair<String, String>) {
        lifecycleScope.launch {
            viewModel.authenticate(credentials)?.let { user ->

                requireContext().dataStore.edit { preferences ->
                    preferences[userLogged] = user.email
                    preferences[rememberPassword] = viewModel.rememberPassword
                }

                hideKeyboard()
                requireContext().navigateTo(MainActivity::class.java)
                requireActivity().finish()

            } ?: authenticationError()
        }
    }

    private fun authenticationError() {
        hideKeyboard()
        requireView().snackBarRed(AUTHENTICATION_ERROR)
    }

    /**
     * Responsible for Back Button behavior, it notifies the ViewModel to change view state.
     */
    private fun initLoginPanelBackBtn() {
        binding.loginFragmentLoginPanel.loginPanelBackImageBtn
            .setOnClickListener {
                setClickRangeTimer(it, 1000)
                hideKeyboard()
                lifecycleScope.launch {
                    delay(500)
                    viewModel.backBtnCLicked()
                }
            }
    }

    /**
     * Responsible for Register Button behavior, navigate to another fragment responsible for
     * register new users.
     */
    private fun initRegisterBtn() {
        binding.loginFragmentBtnRegister.setOnClickListener {
            setClickRangeTimer(it, 1000)

            val direction = LoginFragmentDirections.actionNavLoginToNavRegister()
            navController.navigate(direction)
        }
    }

    /**
     * Responsible for Support Button behavior, navigate to another fragment responsible for
     * support.
     */
    private fun initSupportBtn() {
        binding.loginFragmentSupport.setOnClickListener {
            setClickRangeTimer(it, 1000)
            requireContext().toast("Support Btn clicked")
        }
    }

    /**
     * Responsible for Checkbox behavior, notify viewModel to change checkbox saved state.
     */
    private fun initCheckBoxRememberPassword() {
        lifecycleScope.launch {
            requireContext().dataStore.data.collect { preferences ->
                preferences[rememberPassword].let {
                    if (!it!!) {
                        binding.loginFragmentLoginPanel.loginPanelCheckbox.isChecked = false
                    }
                }
            }
            TODO("o click ainda nao está funcionando")
            binding.loginFragmentLoginPanel.loginPanelCheckbox
                .setOnCheckedChangeListener { _, isChecked ->
                    viewModel.checkBoxClicked(isChecked)
                }
        }
    }

    /**
     * Responsible for Forgotten Password Button behavior, navigate to another fragment responsible
     * for recover user's password.
     */
    private fun initForgottenPasswordBtn() {
        binding.loginFragmentLoginPanel.loginPanelForgotPassword
            .setOnClickListener {
                setClickRangeTimer(it, 1000)
                requireContext().toast("Support Btn clicked")
            }
    }

    /**
     * This method is responsible for manipulate the fragment's view by observing a MutableLiveData.
     *
     * If the MutableLiveData return's true, controller should prepare the view for login.
     *
     * If the MutableLiveData return's false, controller should hide login panel and back its initial
     * stage.
     */
    private fun initViewController() {
        viewModel.loginPanel.observe(viewLifecycleOwner) {
            it?.let {
                if (it) {
                    prepareViewForLogin()
                } else {
                    hideLoginPanel()
                }
            }
        }
    }

    private fun prepareViewForLogin() {
        binding.apply {
            lifecycleScope.launch {
                loginFragmentLoginPanel.loginPanelLayout.visibility = VISIBLE
                loginFragmentBanner.logo.visibility = GONE
                loginFragmentBtnAccess.visibility = GONE
                loginFragmentBtnRegister.visibility = GONE
            }
        }
    }

    private fun hideLoginPanel() {
        binding.apply {
            lifecycleScope.launch {
                loginFragmentLoginPanel.loginPanelLayout.visibility = GONE
                loginFragmentBanner.logo.visibility = VISIBLE
                loginFragmentBtnRegister.visibility = VISIBLE
                loginFragmentBtnAccess.visibility = VISIBLE
            }
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
