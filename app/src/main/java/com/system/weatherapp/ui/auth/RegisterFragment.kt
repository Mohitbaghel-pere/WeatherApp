package com.system.weatherapp.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.system.weatherapp.R
import com.system.weatherapp.databinding.FragmentRegisterBinding
import com.system.weatherapp.ui.apistates.Result
import com.system.weatherapp.ui.viewmodel.RegisterViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!
    private val viewModel: RegisterViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            registerButton.setOnClickListener {
                viewModel.registerUser(
                    nameEditText.text.toString(),
                    emailEditText.text.toString(),
                    passwordEditText.text.toString(),
                    confirmpasswordEditText.text.toString()
                )
            }

            viewModel.result.observe(viewLifecycleOwner, Observer { state ->
                when (state) {
                    is Result.LOADING -> {
                        progressBar.visibility = View.VISIBLE
                        registerButton.isEnabled = false
                    }
                    is Result.SUCCESS -> {
                        progressBar.visibility = View.GONE
                        findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
                    }
                    is Result.ERROR -> {
                        progressBar.visibility = View.GONE
                        registerButton.isEnabled = true
                        Toast.makeText(context, resources.getString(state.message), Toast.LENGTH_SHORT).show()
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
