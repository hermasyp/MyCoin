package com.catnip.mycoin.ui.coinlist

import android.content.Intent
import android.os.Bundle
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import coil.transform.CircleCropTransformation
import com.catnip.mycoin.R
import com.catnip.mycoin.base.arch.BaseActivity
import com.catnip.mycoin.base.model.Resource
import com.catnip.mycoin.data.network.model.response.coin.Coin
import com.catnip.mycoin.databinding.ActivityCoinListBinding
import com.catnip.mycoin.ui.coindetail.CoinDetailActivity
import com.catnip.mycoin.ui.profile.ProfileActivity
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
        setupToolbar()
    }

    private fun setupToolbar() {
        setSupportActionBar(getViewBinding().toolbar)
        supportActionBar?.subtitle = getString(R.string.text_subtitle_toolbar_list)
        getViewBinding().ivProfilePict.apply {
            load(getViewModel().getUserData()?.photo) {
                crossfade(true)
                placeholder(R.drawable.ic_placeholder_profile)
                transformations(CircleCropTransformation())
            }
            setOnClickListener {
                startActivity(Intent(this@CoinListActivity,ProfileActivity::class.java))
            }
        }

    }

    override fun showLoading(isVisible: Boolean) {
        getViewBinding().pbLoading.isVisible = isVisible
    }

    override fun showContent(isVisible: Boolean) {
        getViewBinding().rvContent.isVisible = isVisible
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

}