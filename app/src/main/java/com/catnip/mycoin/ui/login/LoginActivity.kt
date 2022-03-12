package com.catnip.mycoin.ui.login

import android.content.Intent
import android.view.View
import android.widget.Toast
import com.catnip.mycoin.R
import com.catnip.mycoin.base.arch.BaseActivity
import com.catnip.mycoin.base.model.Resource
import com.catnip.mycoin.data.network.model.request.auth.AuthRequest
import com.catnip.mycoin.databinding.ActivityLoginBinding
import com.catnip.mycoin.ui.coinlist.CoinListActivity
import com.catnip.mycoin.ui.register.RegisterActivity
import com.catnip.mycoin.utils.StringUtils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : BaseActivity<ActivityLoginBinding, LoginViewModel>(
    ActivityLoginBinding::inflate
), LoginContract.View {


    override fun setToolbar() {
        supportActionBar?.title = getString(R.string.text_title_toolbar_login)
    }

    override fun setOnClick() {
        getViewBinding().btnLogin.setOnClickListener {
            if (checkFormValidation()) {
                getViewModel().loginUser(
                    AuthRequest(
                        email = getViewBinding().etEmail.text.toString(),
                        password = getViewBinding().etPassword.text.toString()
                    )
                )
            }
        }
        getViewBinding().btnNavigateRegister.setOnClickListener {
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
        getViewBinding().pbLoading.visibility = if (isLoadingVisible) View.VISIBLE else View.GONE
    }

    override fun checkFormValidation(): Boolean {
        val email = getViewBinding().etEmail.text.toString()
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
        return isFormValid
    }

    override fun initView() {
        observeData()
        setToolbar()
        setOnClick()
    }


    override fun observeData() {
        getViewModel().getLoginResultLiveData().observe(this) { response ->
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
        }
    }

    override fun saveSessionLogin(authToken: String?) {
        authToken?.let {
            getViewModel().saveSession(authToken)
        }
    }

}