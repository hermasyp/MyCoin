package com.catnip.mycoin.ui.coindetail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import coil.load
import coil.transform.CircleCropTransformation
import com.catnip.mycoin.R
import com.catnip.mycoin.base.Resource
import com.catnip.mycoin.data.network.model.response.coin.detail.CoinDetailResponse
import com.catnip.mycoin.databinding.ActivityCoinDetailBinding
import com.catnip.mycoin.utils.Extension.textFromHtml
import com.google.android.material.chip.Chip
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CoinDetailActivity : AppCompatActivity(), CoinDetailContract.View {
    private lateinit var binding: ActivityCoinDetailBinding
    private val viewModel: CoinDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
        initViewModel()
        getIntentData()
    }

    override fun initViewModel() {
        viewModel.getCoinDetailResponse().observe(this) {
            when (it) {
                is Resource.Loading -> {
                    showLoading(true)
                    showError(false, null)
                    showContent(false)
                }
                is Resource.Success -> {
                    showLoading(false)
                    showError(false, null)
                    showContent(true)
                    it.data?.let { data ->
                        setContentData(data)
                    }
                }
                is Resource.Error -> {
                    showLoading(false)
                    showError(true, it.message)
                    showContent(false)
                }
            }
        }
        viewModel.getCoinId().observe(this) {
            it?.let {
                viewModel.getCoinDetail(it)
            }
        }
    }

    override fun showContent(isContentVisible: Boolean) {
        binding.groupContent.isVisible = isContentVisible
    }

    override fun showLoading(isLoading: Boolean) {
        binding.pbLoading.isVisible = isLoading
    }

    override fun showError(isErrorEnabled: Boolean, msg: String?) {
        binding.tvErrorMessage.visibility = if (isErrorEnabled) View.VISIBLE else View.GONE
        binding.tvErrorMessage.text = msg
    }

    override fun setContentData(data: CoinDetailResponse) {
        binding.ivIconCoin.load(data.image?.large) {
            crossfade(true)
            transformations(CircleCropTransformation())
        }
        binding.tvCoinSymbol.text = data.symbol?.uppercase()
        binding.tvCoinName.text = data.name
        binding.tvCoinPrice.text = getString(
            R.string.text_placeholder_coin_price,
            data.marketData?.currentPrice?.usd.toString()
        )
        binding.tvDescCoin.textFromHtml(data.description?.en)
        generateChips(data.categories)
    }

    override fun getIntentData() {
        viewModel.setIntentData(intent.extras)
    }

    override fun initView() {
        binding = ActivityCoinDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = null
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == android.R.id.home){
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)

    }

    private fun generateChips(categories: List<String>?) {
        categories?.forEach {
            val chip = Chip(this)
            chip.text = it
            chip.isClickable = false
            binding.cgCategory.addView(chip)
        }
    }

    companion object {
        const val EXTRAS_COIN_ID = "EXTRAS_COIN_ID"

        @JvmStatic
        fun startActivity(context: Context?, coinId: String) {
            val intent = Intent(context, CoinDetailActivity::class.java)
            intent.putExtras(bundleOf().apply {
                putString(EXTRAS_COIN_ID, coinId)
            })
            context?.startActivity(intent)
        }
    }
}