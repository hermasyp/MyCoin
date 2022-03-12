package com.catnip.mycoin.ui.coinlist

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.catnip.mycoin.R
import com.catnip.mycoin.base.arch.BaseActivity
import com.catnip.mycoin.base.model.Resource
import com.catnip.mycoin.data.network.model.response.coin.Coin
import com.catnip.mycoin.databinding.ActivityCoinListBinding
import com.catnip.mycoin.ui.coindetail.CoinDetailActivity
import com.catnip.mycoin.ui.login.LoginActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CoinListActivity : BaseActivity<ActivityCoinListBinding, CoinListViewModel>(
    ActivityCoinListBinding::inflate
), CoinListContract.View {

    private lateinit var adapter: CoinListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getData()
    }

    override fun getData() {
        getViewModel().getCoinList()
    }

    override fun initView() {
        initSwipeRefresh()
        initList()
    }

    override fun showLoading(isLoading: Boolean) {
        getViewBinding().pbLoading.isVisible = isLoading
    }

    override fun showContent(isContentShown: Boolean) {
        getViewBinding().rvContent.isVisible = isContentShown
    }

    override fun showError(isErrorEnabled: Boolean, msg: String?) {
        getViewBinding().tvMessage.isVisible = isErrorEnabled
        getViewBinding().tvMessage.text = msg
    }

    override fun observeData() {
        getViewModel().getCoinListLiveData().observe(this) {
            when (it) {
                is Resource.Loading -> {
                    showLoading(true)
                    showContent(false)
                    showError(false, null)
                }
                is Resource.Success -> {
                    showLoading(false)
                    showContent(true)
                    showError(false, null)
                    setDataAdapter(it.data)
                }
                is Resource.Error -> {
                    showLoading(false)
                    showContent(false)
                    showError(true, it.message)
                }
            }
        }

    }

    override fun initList() {
        adapter = CoinListAdapter {
            CoinDetailActivity.startActivity(this, it.id.orEmpty())
        }
        getViewBinding().rvContent.apply {
            adapter = this@CoinListActivity.adapter
            layoutManager = LinearLayoutManager(this@CoinListActivity)
        }
    }

    private fun setDataAdapter(data: List<Coin>?) {
        data?.let { adapter.setItems(it) }
    }

    override fun initSwipeRefresh() {
        getViewBinding().srlContent.setOnRefreshListener {
            getViewBinding().srlContent.isRefreshing = false
            getData()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_coin_list, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_logout -> {
                showDialogLogout()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showDialogLogout() {
        AlertDialog.Builder(this)
            .apply {
                setTitle(getString(R.string.text_logout_dialog))
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
        getViewModel().deleteSession()
        navigateToLogin()
    }

    private fun navigateToLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
        finish()
    }

}