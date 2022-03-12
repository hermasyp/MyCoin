package com.catnip.mycoin.base.arch

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding
import javax.inject.Inject

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/
abstract class BaseActivity<B : ViewBinding, VM : ViewModel>(
    val bindingFactory: (LayoutInflater) -> B
) : AppCompatActivity(),BaseContract.BaseView {

    private lateinit var binding: B

    @Inject
    lateinit var viewModelInstance : VM

    fun getViewBinding(): B = binding
    fun getViewModel(): VM = viewModelInstance

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = bindingFactory(layoutInflater)
        setContentView(binding.root)
        initView()
        observeData()
    }

    abstract fun initView()
    override fun observeData(){
        //do nothing
    }
    override fun showContent(isVisible: Boolean) {
        //do nothing
    }
    override fun showLoading(isVisible: Boolean) {
        //do nothing
    }
    override fun showError(isErrorEnabled: Boolean, msg: String?) {
        if (isErrorEnabled) Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }
}