package com.catnip.mycoin.ui.coindetail

import android.content.Context
import android.content.Intent
import android.view.MenuItem
import android.view.View
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import coil.load
import coil.transform.CircleCropTransformation
import com.catnip.mycoin.R
import com.catnip.mycoin.base.arch.BaseActivity
import com.catnip.mycoin.base.model.Resource
import com.catnip.mycoin.data.network.model.response.coin.detail.CoinDetailResponse
import com.catnip.mycoin.databinding.ActivityCoinDetailBinding
import com.catnip.mycoin.utils.Extension.textFromHtml
import com.google.android.material.chip.Chip
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CoinDetailActivity :
    BaseActivity<ActivityCoinDetailBinding, CoinDetailViewModel>(ActivityCoinDetailBinding::inflate),
    CoinDetailContract.View {

    override fun initView() {
        getIntentData()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = null
    }

    override fun observeData() {
        getViewModel().getCoinDetailResponse().observe(this) {
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
        getViewModel().getCoinId().observe(this) {
            it?.let {
                getViewModel().getCoinDetail(it)
            }
        }
    }

    override fun showContent(isVisible: Boolean) {
        getViewBinding().groupContent.isVisible = isVisible
    }

    override fun showLoading(isVisible: Boolean) {
        getViewBinding().pbLoading.isVisible = isVisible
    }

    override fun showError(isErrorEnabled: Boolean, msg: String?) {
        getViewBinding().tvErrorMessage.visibility = if (isErrorEnabled) View.VISIBLE else View.GONE
        getViewBinding().tvErrorMessage.text = msg
    }

    override fun setContentData(data: CoinDetailResponse) {
        getViewBinding().ivIconCoin.load(data.image?.large) {
            crossfade(true)
            transformations(CircleCropTransformation())
        }
        getViewBinding().tvCoinSymbol.text = data.symbol?.uppercase()
        getViewBinding().tvCoinName.text = data.name
        getViewBinding().tvCoinPrice.text = getString(
            R.string.text_placeholder_coin_price,
            data.marketData?.currentPrice?.usd.toString()
        )
        getViewBinding().tvDescCoin.textFromHtml(data.description?.en)
        generateChips(data.categories)
    }

    override fun getIntentData() {
        getViewModel().setIntentData(intent.extras)
    }


    private fun generateChips(categories: List<String?>?) {
        categories?.filter { !it.isNullOrEmpty() }?.forEach {
            getViewBinding().cgCategory.addView(
                Chip(this).apply {
                    text = it
                    isClickable = false
                })
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