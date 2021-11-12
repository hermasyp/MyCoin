package com.catnip.mycoin.ui.coinlist

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.catnip.mycoin.base.Resource
import com.catnip.mycoin.data.network.model.response.coin.Coin
import com.catnip.mycoin.databinding.ActivityCoinListBinding
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
            //todo : navigate to detail , bringing coin id to get the detail from API
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

}