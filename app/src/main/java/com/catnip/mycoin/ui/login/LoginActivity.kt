package com.catnip.mycoin.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.catnip.mycoin.R
import com.catnip.mycoin.base.Resource
import com.catnip.mycoin.data.network.model.request.auth.AuthRequest
import com.catnip.mycoin.databinding.ActivityLoginBinding
import com.catnip.mycoin.ui.coinlist.CoinListActivity
import com.catnip.mycoin.ui.register.RegisterActivity
import com.catnip.mycoin.utils.StringUtils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity(), LoginContract.View {
    private lateinit var binding: ActivityLoginBinding
    private val viewModel: LoginViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
    }

    override fun setToolbar() {
        supportActionBar?.title = getString(R.string.text_title_toolbar_login)
    }

    override fun setOnClick() {
        binding.btnLogin.setOnClickListener {
            if (checkFormValidation()) {
                viewModel.loginUser(
                    AuthRequest(
                        email = binding.etEmail.text.toString(),
                        password = binding.etPassword.text.toString()
                    )
                )
            }
        }
        binding.btnNavigateRegister.setOnClickListener {
            navigateToRegister()
        }
    }

    override fun navigateToHome() {
        val intent = Intent(this, CoinListActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
        finish()
    }

    override fun navigateToRegister() {
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
    }

    override fun setLoadingState(isLoadingVisible: Boolean) {
        binding.pbLoading.visibility = if (isLoadingVisible) View.VISIBLE else View.GONE
    }

    override fun checkFormValidation(): Boolean {
        val email = binding.etEmail.text.toString()
        val password = binding.etPassword.text.toString()
        var isFormValid = true
        //for checking is email empty
        when {
            email.isEmpty() -> {
                isFormValid = false
                binding.tilEmail.isErrorEnabled = true
                binding.tilEmail.error = getString(R.string.error_email_empty)
            }
            StringUtils.isEmailValid(email).not() -> {
                isFormValid = false
                binding.tilEmail.isErrorEnabled = true
                binding.tilEmail.error = getString(R.string.error_email_invalid)
            }
            else -> {
                binding.tilEmail.isErrorEnabled = false
            }
        }
        //for checking is Password empty
        if (password.isEmpty()) {
            isFormValid = false
            binding.tilPassword.isErrorEnabled = true
            binding.tilPassword.error = getString(R.string.error_password_empty)
        } else {
            binding.tilPassword.isErrorEnabled = false
        }
        return isFormValid
    }

    override fun initView() {
        initViewModel()
        setToolbar()
        setOnClick()
    }

    override fun initViewModel() {
        viewModel.getLoginResultLiveData().observe(this, { response ->
            when (response) {
                is Resource.Loading -> {
                    setLoadingState(true)
                }
                is Resource.Success -> {
                    setLoadingState(false)
                    Toast.makeText(this, R.string.text_login_success, Toast.LENGTH_SHORT).show()
                    saveSessionLogin(response.data?.token)
                    navigateToHome()

                }
                is Resource.Error -> {
                    setLoadingState(false)
                    Toast.makeText(
                        this,
                        "Login Failed, Please check email and password correctly",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        })
    }

    override fun saveSessionLogin(authToken: String?) {
        authToken?.let {
            saveSessionLogin(it)
        }
    }
}