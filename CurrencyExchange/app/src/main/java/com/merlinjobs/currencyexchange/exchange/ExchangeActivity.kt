package com.merlinjobs.currencyexchange.exchange

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.merlinjobs.currencyexchange.BaseApplication
import com.merlinjobs.currencyexchange.R
import com.merlinjobs.currencyexchange.Utils.DecimalDigitsInputFilter
import com.merlinjobs.currencyexchange.data.PREFERENCE_DIALOG_TAG
import com.merlinjobs.currencyexchange.data.local.models.Currency
import com.merlinjobs.currencyexchange.data.local.models.ExchangeConversion
import com.merlinjobs.currencyexchange.databinding.ActivityExchangeBinding
import com.merlinjobs.currencyexchange.preferences.PreferenceDialogFragment
import io.reactivex.Single
import io.reactivex.functions.Consumer
import kotlinx.android.synthetic.main.activity_exchange.*

/**
 * Created by Alexander Fermin (alexfer06@gmail.com) on 02/08/2018.
 */
class ExchangeActivity : AppCompatActivity() {

    private var viewModel: ExchangeActivityViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(ExchangeActivityViewModel::class.java)
        lifecycle.addObserver(viewModel!!)
        val binding: ActivityExchangeBinding = DataBindingUtil.setContentView(this, R.layout.activity_exchange)
        // Assign the component to a property in the binding class.
        binding.viewmodel = viewModel
        initComponents()
    }

    override fun onResume() {
        super.onResume()
        toObserve()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        item?.let {
            if (item.itemId == R.id.action_favorite_currencies) {
                showPreferenceDialog()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menu?.let {
            menuInflater.inflate(R.menu.exchange_menu, it)
        }
        return super.onCreateOptionsMenu(menu)
    }

    private fun toObserve() {
        viewModel?.getConversions()?.observe(this, Observer {
            showConversions(it!!)
        })
        viewModel?.getActions()?.observe(this, Observer {
            when (it) {
                ExchangeActivityViewModel.Action.CLEAR_CONVERSIONS -> clearConversions()
                ExchangeActivityViewModel.Action.OPEN_DIALOG_PREFERENCES -> showPreferenceDialog()
            }
        })
    }

    private fun initComponents() {
        mRVExchanges?.setHasFixedSize(false)
        mRVExchanges?.layoutManager = LinearLayoutManager(this)

        etValue?.filters = arrayOf(DecimalDigitsInputFilter(1))
        Single.create<Map<String, Currency>> { emitter ->
            val map = HashMap<String, Currency>()
            BaseApplication.getInstance().getmCurrencies().forEach {
                map[it.code] = it
            }
            emitter.onSuccess(map)
        }.subscribe(Consumer<Map<String, Currency>> {
            mRVExchanges?.adapter = ExchangeRVAdapter(it)
        })
    }

    private fun showConversions(conversions: List<ExchangeConversion>) {
        mRVExchanges?.adapter?.let {
            (it as ExchangeRVAdapter).clear()
            (it).addConversions(conversions)
        }
    }

    private fun showPreferenceDialog() {
        val ft = fragmentManager.beginTransaction()
        val prev = fragmentManager.findFragmentByTag(PREFERENCE_DIALOG_TAG)
        if (prev != null) {
            ft.remove(prev)
        }
        ft.addToBackStack(null)

        // Create and show the dialog.
        val newFragment = PreferenceDialogFragment.newInstance()
        newFragment.show(ft, PREFERENCE_DIALOG_TAG)
    }

    private fun clearConversions() {
        mRVExchanges?.adapter?.let {
            (it as ExchangeRVAdapter).clear()
        }
    }
}

