package com.catnip.mycoin.ui.register

import android.content.Intent
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.catnip.mycoin.R
import com.catnip.mycoin.base.arch.BaseActivity
import com.catnip.mycoin.base.model.Resource
import com.catnip.mycoin.data.network.model.request.auth.AuthRequest
import com.catnip.mycoin.databinding.ActivityRegisterBinding
import com.catnip.mycoin.ui.login.LoginActivity
import com.catnip.mycoin.utils.StringUtils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterActivity : BaseActivity<ActivityRegisterBinding, RegisterViewModel>(
    ActivityRegisterBinding::inflate
), RegisterContract.View {


    override fun setToolbar() {
        supportActionBar?.title = getString(R.string.text_title_toolbar_register)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun setLoadingState(isLoadingVisible: Boolean) {
        getViewBinding().pbLoading.visibility = if (isLoadingVisible) View.VISIBLE else View.GONE
    }

    override fun checkFormValidation(): Boolean {
        val email = getViewBinding().etEmail.text.toString()
        val username = getViewBinding().etUsername.text.toString()
        val password = getViewBinding().etPassword.text.toString()
        var isFormValid = true

        //for checking is email empty
        when {
            email.isEmpty() -> {
                isFormValid = false
                getViewBinding().tilEmail.isErrorEnabled = true
                getViewBinding().tilEmail.error = getString(R.string.error_email_empty)
            }
            StringUtils.isEmailValid(email).not() -> {
                isFormValid = false
                getViewBinding().tilEmail.isErrorEnabled = true
                getViewBinding().tilEmail.error = getString(R.string.error_email_invalid)
            }
            else -> {
                getViewBinding().tilEmail.isErrorEnabled = false
            }
        }
        //for checking is Password empty
        if (password.isEmpty()) {
            isFormValid = false
            getViewBinding().tilPassword.isErrorEnabled = true
            getViewBinding().tilPassword.error = getString(R.string.error_password_empty)
        } else {
            getViewBinding().tilPassword.isErrorEnabled = false
        }
        //for checking is Password empty
        if (username.isEmpty()) {
            isFormValid = false
            getViewBinding().tilUsername.isErrorEnabled = true
            getViewBinding().tilUsername.error = getString(R.string.error_username_empty)
        } else {
            getViewBinding().tilUsername.isErrorEnabled = false
        }
        return isFormValid
    }

    override fun navigateToLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
        finish()
    }

    override fun initView() {
        observeData()
        setToolbar()
        setOnClick()
    }

    override fun observeData() {
        getViewModel().getRegisterResponseLiveData().observe(this) { response ->
            when (response) {
                is Resource.Loading -> {
                    setLoadingState(true)
                }
                is Resource.Success -> {
                    setLoadingState(false)
                    Toast.makeText(this, R.string.text_register_success, Toast.LENGTH_SHORT).show()
                    navigateToLogin()
                }
                is Resource.Error -> {
                    setLoadingState(false)
                    Toast.makeText(this, response.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun setOnClick() {
        getViewBinding().btnRegister.setOnClickListener {
            if(checkFormValidation()){
                getViewModel().registerUser(
                    AuthRequest(
                        email = getViewBinding().etEmail.text.toString(),
                        password = getViewBinding().etPassword.text.toString(),
                        username = getViewBinding().etUsername.text.toString()
                    )
                )
            }
        }
    }

}