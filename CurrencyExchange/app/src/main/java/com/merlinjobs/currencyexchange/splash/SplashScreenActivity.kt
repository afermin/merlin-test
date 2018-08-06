package com.merlinjobs.currencyexchange.splash

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.merlinjobs.currencyexchange.R
import com.merlinjobs.currencyexchange.exchange.ExchangeActivity
import kotlinx.android.synthetic.main.activity_splash_screen.*
import java.util.*

class SplashScreenActivity : AppCompatActivity(), ISplashScreenView {

    private val mPresenter: ISplashScreenPresenter = SplashScreenPresenter()

    private var mCreationTime: Long = 0

    private var mMinTime = 5000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        lifecycle.addObserver(mPresenter)
        mPresenter.bind(this)
    }

    override fun onResume() {
        super.onResume()
        mCreationTime = Calendar.getInstance().timeInMillis
    }

    override fun onStart() {
        super.onStart()
        val anim : Animation = AnimationUtils.loadAnimation(this, R.anim.scaled)
        mIVIcon?.startAnimation(anim)
    }


    override fun initComponents() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showProgressBar() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun hideProgressBar() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun navigateToNextActivity() {
        mIVIcon?.postDelayed({
            startActivity(Intent(this, ExchangeActivity::class.java))
        }, getDelay())

    }

    private fun getDelay(): Long {
        var differece = Calendar.getInstance().timeInMillis - mMinTime
        val secondsInMilli: Long = 1000
        val minutesInMilli = secondsInMilli * 60
        val hoursInMilli = minutesInMilli * 60
        val daysInMilli = hoursInMilli * 24

        differece %= daysInMilli

        differece %= hoursInMilli

        differece %= minutesInMilli

        val elapsedSeconds = differece / secondsInMilli

        return if (elapsedSeconds > 0) mMinTime - elapsedSeconds else 0

    }

    override fun getContext(): Context {
        return this
    }

}
