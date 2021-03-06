package com.catnip.mycoin.ui.profile

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import coil.load
import coil.transform.CircleCropTransformation
import com.catnip.mycoin.R
import com.catnip.mycoin.base.arch.BaseActivity
import com.catnip.mycoin.base.model.Resource
import com.catnip.mycoin.data.network.model.response.auth.User
import com.catnip.mycoin.databinding.ActivityProfileBinding
import com.catnip.mycoin.ui.login.LoginActivity
import com.catnip.mycoin.utils.StringUtils
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import java.io.File

@AndroidEntryPoint
class ProfileActivity :
    BaseActivity<ActivityProfileBinding, ProfileViewModel>(ActivityProfileBinding::inflate),
    ProfileContract.View {

    private var selectedPicture: File? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getData()
    }

    override fun getData() {
        getViewModel().getProfileData()
    }

    override fun initView() {
        setToolbar()
        setOnClickListeners()
    }

    private val startForProfileImageResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            val resultCode = result.resultCode
            val data = result.data

            when (resultCode) {
                Activity.RESULT_OK -> {
                    //Image Uri will not be null for RESULT_OK
                    val fileUri = data?.data!!
                    getViewBinding().ivProfilePict.load(fileUri) {
                        crossfade(true)
                        placeholder(R.drawable.ic_placeholder_profile)
                        transformations(CircleCropTransformation())
                    }
                    fileUri.path?.let {
                        val file = File(it)
                        if (file.exists()) {
                            selectedPicture = file
                        }
                    }
                }
                ImagePicker.RESULT_ERROR -> {
                    Toast.makeText(this, ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
                }
                else -> {
                    Toast.makeText(this, "Task Cancelled", Toast.LENGTH_SHORT).show()
                }
            }
        }

    override fun observeData() {
        super.observeData()
        getViewModel().getProfileLiveData().observe(this) { response ->
            when (response) {
                is Resource.Loading -> {
                    showLoading(true)
                    showContent(false)
                }
                is Resource.Success -> {
                    showLoading(false)
                    showContent(true)
                    response.data?.let { setDataToView(it) }

                }
                is Resource.Error -> {
                    showLoading(false)
                    showContent(true)
                    showError(true, response.message)
                }
            }
        }
        getViewModel().getChangeProfileResultLiveData().observe(this) { response ->
            when (response) {
                is Resource.Loading -> {
                    showLoading(true)
                    showContent(false)
                }
                is Resource.Success -> {
                    showLoading(false)
                    showContent(true)
                    Toast.makeText(
                        this,
                        getString(R.string.text_change_profile_success),
                        Toast.LENGTH_SHORT
                    ).show()
                    response.data?.let { setDataToView(it) }
                }
                is Resource.Error -> {
                    showLoading(false)
                    showContent(true)
                    showError(true, response.message)
                }
            }
        }
    }

    override fun showLoading(isVisible: Boolean) {
        super.showLoading(isVisible)
        getViewBinding().pbLoading.isVisible = isVisible
    }

    override fun showContent(isVisible: Boolean) {
        super.showContent(isVisible)
        getViewBinding().groupContent.isVisible = isVisible
    }

    override fun setToolbar() {
        supportActionBar?.title = getString(R.string.text_title_toolbar_profile)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun setOnClickListeners() {
        getViewBinding().btnChangeProfileData.setOnClickListener {
            if (checkFormValidation()) {
                getViewModel().updateProfileData(
                    email = getViewBinding().etEmail.text.toString().trim(),
                    username = getViewBinding().etUsername.text.toString().trim(),
                    photoProfile = selectedPicture,
                )
            }
        }
        getViewBinding().btnLogout.setOnClickListener {
            showDialogLogout()
        }
        getViewBinding().flProfilePict.setOnClickListener {
            ImagePicker.with(this)
                .crop()
                .saveDir(
                    File(
                        externalCacheDir,
                        "ImagePicker"
                    )
                ) //Crop image(Optional), Check Customization for more option
                .compress(1024)            //Final image size will be less than 1 MB(Optional)
                .maxResultSize(
                    1080,
                    1080
                )    //Final image resolution will be less than 1080 x 1080(Optional)
                .createIntent { intent ->
                    startForProfileImageResult.launch(intent)
                }
        }
    }

    override fun checkFormValidation(): Boolean {
        val email = getViewBinding().etEmail.text.toString()
        val username = getViewBinding().etUsername.text.toString()
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
        //for checking is username empty
        if (username.isEmpty()) {
            isFormValid = false
            getViewBinding().tilUsername.isErrorEnabled = true
            getViewBinding().tilUsername.error = getString(R.string.error_username_empty)
        } else {
            getViewBinding().tilUsername.isErrorEnabled = false
        }
        return isFormValid
    }

    override fun setDataToView(data: User) {
        getViewBinding().ivProfilePict.load(data.photo) {
            crossfade(true)
            transformations(CircleCropTransformation())
            placeholder(R.drawable.ic_placeholder_profile)
        }
        getViewBinding().etEmail.setText(data.email)
        getViewBinding().etUsername.setText(data.username)
    }


    private fun showDialogLogout() {
        MaterialAlertDialogBuilder(this,R.style.AlertDialogTheme)
            .apply {
                setTitle(getString(R.string.text_logout_dialog_title))
                setMessage(getString(R.string.text_logout_dialog_msg))
                setPositiveButton(R.string.text_dialog_logout_task_positive) { dialog, _ ->
                    logout()
                    dialog.dismiss()
                }
                setNegativeButton(R.string.text_dialog_logout_task_negative) { dialog, _ ->
                    dialog.dismiss()
                }
            }.create().show()
    }

    private fun logout() {
        getViewModel().logout()
        navigateToLogin()
    }

    private fun navigateToLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
        finish()
    }
}