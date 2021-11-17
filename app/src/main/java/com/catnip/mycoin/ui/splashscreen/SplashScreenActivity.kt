package com.catnip.mycoin.ui.splashscreen

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.catnip.mycoin.base.Resource
import com.catnip.mycoin.databinding.ActivitySplashScreenBinding
import com.catnip.mycoin.ui.coinlist.CoinListActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashScreenActivity : AppCompatActivity(), SplashScreenContract.View {

    private lateinit var binding: ActivitySplashScreenBinding
    private val viewModel: SplashScreenViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
        initViewModel()
        checkLoginStatus()
    }

    override fun initView() {
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
    }

    override fun initViewModel() {
        viewModel.getSyncUserLiveData().observe(this) {
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
        if (viewModel.isUserLoggedIn()) {
            viewModel.getSyncUser()
        } else {
            navigateToLogin()
        }
    }

    override fun deleteSession() {
        viewModel.clearSession()
    }

    override fun navigateToLogin() {
/*        val intent = Intent(this, CoinListActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
        finish()*/
        Toast.makeText(this, "Navigate to login", Toast.LENGTH_SHORT).show()
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
}