package com.system.weatherapp.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.system.weatherapp.R
import com.system.weatherapp.databinding.FragmentLoginBinding
import com.system.weatherapp.ui.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private val viewModel: LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            loginButton.setOnClickListener {
                viewModel.login(
                    emailEditText.text.toString(),
                    passwordEditText.text.toString()
                )
            }

            registertv.setOnClickListener {
                findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
            }

            viewModel.loginState.observe(viewLifecycleOwner, Observer { state ->
                when (state) {
                    is LoginViewModel.LoginState.Loading -> {
                        progressBar.visibility = View.VISIBLE
                        loginButton.isEnabled = false
                    }
                    is LoginViewModel.LoginState.Success -> {
                        progressBar.visibility = View.GONE
                        loginButton.isEnabled = true
                        findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
                    }
                    is LoginViewModel.LoginState.Error -> {
                        progressBar.visibility = View.GONE
                        loginButton.isEnabled = true
                        emailEditText.error = resources.getString(state.message)
                        passwordEditText.error =  resources.getString(state.message)
                    }
                    LoginViewModel.LoginState.InvalidEmail -> {
                        emailEditText.error = getString(R.string.invalid_email)
                    }
                    LoginViewModel.LoginState.InvalidPassword -> {
                        passwordEditText.error = getString(R.string.inncorrect_password)
                    }
                }
            })
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
