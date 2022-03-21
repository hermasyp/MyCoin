package com.catnip.mycoin.ui.coinlist

import android.os.Bundle
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import coil.transform.CircleCropTransformation
import com.catnip.mycoin.R
import com.catnip.mycoin.base.arch.BaseActivity
import com.catnip.mycoin.base.model.Resource
import com.catnip.mycoin.data.network.model.response.coin.list.Coin
import com.catnip.mycoin.databinding.ActivityCoinListBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CoinListActivity :
    BaseActivity<ActivityCoinListBinding, CoinListViewModel>(ActivityCoinListBinding::inflate),
    CoinListContract.View {

    private lateinit var adapter: CoinListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getData()
    }

    override fun initView() {
        initSwipeRefresh()
        initList()
        setToolbar()
    }

    override fun setToolbar() {
        setSupportActionBar(getViewBinding().toolbar)
        supportActionBar?.subtitle = getString(R.string.text_subtitle_toolbar_list)
        getViewBinding().ivProfilePict.apply {
            load(getViewModel().getUserData()?.photo){
                crossfade(true)
                placeholder(R.drawable.ic_placeholder_profile)
                transformations(CircleCropTransformation())
            }
            setOnClickListener {
                //todo : navigate to profile page
            }
        }

    }

    override fun initList() {
        adapter = CoinListAdapter {
            //todo : open detail
        }
        getViewBinding().rvContent.apply {
            layoutManager = LinearLayoutManager(this@CoinListActivity)
            adapter = this@CoinListActivity.adapter
        }
    }

    private fun setDataAdapter(data: List<Coin>?) {
        data?.let {
            adapter.setItems(it)
        }
    }

    override fun observeData() {
        super.observeData()
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

    override fun getData() {
        getViewModel().getCoinList()
    }

    override fun initSwipeRefresh() {
        getViewBinding().srlContent.setOnRefreshListener {
            getViewBinding().srlContent.isRefreshing = false
            getData()
        }
    }

    override fun showLoading(isVisible: Boolean) {
        getViewBinding().pbLoading.isVisible = isVisible
    }

    override fun showContent(isVisible: Boolean) {
        getViewBinding().srlContent.isVisible = isVisible
    }

    override fun showError(isErrorEnabled: Boolean, msg: String?) {
        getViewBinding().tvMessage.isVisible = isErrorEnabled
        getViewBinding().tvMessage.text = msg
    }
}