package com.catnip.mycoin.ui.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.catnip.mycoin.R

class LoginActivity : AppCompatActivity(),LoginContract.View{
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }

    override fun initView() {
        TODO("Not yet implemented")
    }

    override fun initViewModel() {
        TODO("Not yet implemented")
    }
}