package com.catnip.mycoin.ui.splashscreen

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.catnip.mycoin.base.BaseActivity
import com.catnip.mycoin.base.Resource
import com.catnip.mycoin.databinding.ActivitySplashScreenBinding
import com.catnip.mycoin.ui.coinlist.CoinListActivity
import com.catnip.mycoin.ui.login.LoginActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SplashScreenActivity : BaseActivity<ActivitySplashScreenBinding,SplashScreenViewModel>(
    ActivitySplashScreenBinding::inflate
), SplashScreenContract.View {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkLoginStatus()
    }

    override fun initView() {
        supportActionBar?.hide()
    }

    override fun initViewModel() {
        getViewModel().getSyncUserLiveData().observe(this) {
            when (it) {
                is Resource.Loading -> {
                    Log.d(TAG, "initViewModel: Loading")
                }
                is Resource.Success -> {
                    navigateToHome()
                }
                is Resource.Error -> {
                    deleteSession()
                    navigateToLogin()
                }
            }
        }
    }

    override fun checkLoginStatus() {
        if (getViewModel().isUserLoggedIn()) {
            getViewModel().getSyncUser()
        } else {
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
        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
        finish()
    }

    override fun navigateToHome() {
        val intent = Intent(this, CoinListActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
        finish()
    }

    companion object {
        private val TAG = SplashScreenActivity::class.simpleName
    }

    override val viewModelInstance: SplashScreenViewModel by viewModels()
}