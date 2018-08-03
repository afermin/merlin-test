package com.merlinjobs.currencyexchange.exchange

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import com.merlinjobs.currencyexchange.BaseApplication
import com.merlinjobs.currencyexchange.R
import com.merlinjobs.currencyexchange.Utils.DecimalDigitsInputFilter
import com.merlinjobs.currencyexchange.data.PREFERENCE_DIALOG_TAG
import com.merlinjobs.currencyexchange.data.local.models.Currency
import com.merlinjobs.currencyexchange.data.local.models.ExchangeConversion
import com.merlinjobs.currencyexchange.preferences.PreferenceDialogFragment
import io.reactivex.Single
import io.reactivex.functions.Consumer
import kotlinx.android.synthetic.main.activity_exchange.*

class ExchangeActivity : AppCompatActivity(), IExchangeActivityView {


    private val mPresenter: IExchangeActivityPresenter = ExchangeActivityPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exchange)
        lifecycle.addObserver(mPresenter)
        mPresenter.bind(this)
        initComponents()

    }

    override fun initComponents() {
        mRVExchanges?.setHasFixedSize(false)
        mRVExchanges?.layoutManager = LinearLayoutManager(this)

        mETValue?.filters = arrayOf(DecimalDigitsInputFilter(1))
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


    override fun showProgressBar() {
    }

    override fun hideProgressBar() {
    }

    override fun showConversions(conversions: List<ExchangeConversion>) {
        mRVExchanges?.adapter?.let {
            (it as ExchangeRVAdapter).clear()
            (it as ExchangeRVAdapter).addConversions(conversions)

        }
    }

    override fun showPreferenceDialog() {
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

    override fun clearConversions() {
        mRVExchanges?.adapter?.let {
            (it as ExchangeRVAdapter).clear()
        }
    }

    override fun getValueEditText(): EditText? {
        return mETValue
    }
}
