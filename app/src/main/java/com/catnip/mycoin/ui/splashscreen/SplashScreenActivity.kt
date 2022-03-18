package com.catnip.mycoin.ui.splashscreen

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.catnip.mycoin.base.arch.BaseActivity
import com.catnip.mycoin.base.model.Resource
import com.catnip.mycoin.databinding.ActivitySplashScreenBinding
import com.catnip.mycoin.ui.coinlist.CoinListActivity
import com.catnip.mycoin.ui.login.LoginActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SplashScreenActivity :
    BaseActivity<ActivitySplashScreenBinding, SplashScreenViewModel>(ActivitySplashScreenBinding::inflate),
    SplashScreenContract.View {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkLoginStatus()
    }

    override fun initView() {
        supportActionBar?.hide()
    }

    override fun observeData() {
        super.observeData()
        getViewModel().getSyncUserLiveData().observe(this) {
            when (it) {
                is Resource.Loading -> {

                }
                is Resource.Success -> {
                    navigateToHome()
                }
                is Resource.Error -> {
                    deleteSession().also {
                        navigateToLogin()
                    }
                }
            }
        }
    }

    override fun checkLoginStatus() {
        if(getViewModel().isUserLoggedIn()){
            getViewModel().getSyncUser()
        }else{
            lifecycleScope.launch(Dispatchers.IO) {
                delay(1000)
                lifecycleScope.launch(Dispatchers.Main){
                    navigateToLogin()
                }
            }
        }
    }

    override fun deleteSession() {
        getViewModel().clearSession()
    }

    override fun navigateToLogin() {
        startActivity(Intent(this, LoginActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        })
    }

    override fun navigateToHome() {
        startActivity(Intent(this, CoinListActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        })
    }


}