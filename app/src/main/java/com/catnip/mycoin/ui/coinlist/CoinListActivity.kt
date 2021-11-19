package com.catnip.mycoin.ui.coinlist

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.catnip.mycoin.R
import com.catnip.mycoin.base.Resource
import com.catnip.mycoin.data.network.model.response.coin.Coin
import com.catnip.mycoin.databinding.ActivityCoinListBinding
import com.catnip.mycoin.ui.coindetail.CoinDetailActivity
import com.catnip.mycoin.ui.login.LoginActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CoinListActivity : AppCompatActivity(), CoinListContract.View {
    private lateinit var binding: ActivityCoinListBinding
    private lateinit var adapter: CoinListAdapter
    private val viewModel: CoinListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
        observeViewModel()
        getData()
    }

    override fun getData() {
        viewModel.getCoinList()
    }

    override fun initView() {
        binding = ActivityCoinListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initSwipeRefresh()
        initList()
    }

    override fun showLoading(isLoading: Boolean) {
        binding.pbLoading.isVisible = isLoading
    }

    override fun showContent(isContentShown: Boolean) {
        binding.rvContent.isVisible = isContentShown
    }

    override fun showErrMsg(isError: Boolean, msg: String?) {
        binding.tvMessage.isVisible = isError
        binding.tvMessage.text = msg
    }

    override fun observeViewModel() {
        viewModel.getCoinListLiveData().observe(this) {
            when (it) {
                is Resource.Loading -> {
                    showLoading(true)
                    showContent(false)
                    showErrMsg(false, null)
                }
                is Resource.Success -> {
                    showLoading(false)
                    showContent(true)
                    showErrMsg(false, null)
                    setDataAdapter(it.data)
                }
                is Resource.Error -> {
                    showLoading(false)
                    showContent(false)
                    showErrMsg(true, it.message)
                }
            }
        }

    }

    override fun initList() {
        adapter = CoinListAdapter {
            CoinDetailActivity.startActivity(this, it.id.orEmpty())
        }
        binding.rvContent.apply {
            adapter = this@CoinListActivity.adapter
            layoutManager = LinearLayoutManager(this@CoinListActivity)
        }
    }

    private fun setDataAdapter(data: List<Coin>?) {
        data?.let { adapter.setItems(it) }
    }

    override fun initSwipeRefresh() {
        binding.srlContent.setOnRefreshListener {
            binding.srlContent.isRefreshing = false
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
                setPositiveButton(R.string.text_dialog_logout_task_positive) { dialog, id ->
                    logout()
                    dialog.dismiss()
                }
                setNegativeButton(R.string.text_dialog_logout_task_negative) { dialog, id ->
                    dialog.dismiss()
                }
            }.create().show()
    }

    private fun logout() {
        viewModel.deleteSession()
        navigateToLogin()
    }

    private fun navigateToLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
        finish()
    }
}