package com.catnip.mycoin.ui.register

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.catnip.mycoin.R

class RegisterActivity : AppCompatActivity(),RegisterContract.View {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
    }

    override fun initView() {
        TODO("Not yet implemented")
    }

    override fun initViewModel() {
        TODO("Not yet implemented")
    }
}