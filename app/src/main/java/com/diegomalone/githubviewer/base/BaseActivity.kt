package com.diegomalone.githubviewer.base

import android.app.Activity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.WindowManager
import android.widget.Toast
import com.diegomalone.githubviewer.R
import com.diegomalone.githubviewer.base.BaseView.Companion.DEFAULT_CLICK_LISTENER
import com.diegomalone.githubviewer.di.ActivityComponent
import com.diegomalone.githubviewer.di.ConfigPersistentDelegate
import com.diegomalone.githubviewer.di.module.ActivityModule
import com.diegomalone.githubviewer.util.extension.visible
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.content_repository_list.*
import kotlinx.android.synthetic.main.include_error_view.*
import timber.log.Timber
import java.lang.ref.WeakReference

abstract class BaseActivity : AppCompatActivity() {
    protected val compositeDisposable = CompositeDisposable()
    protected val configPersistDelegate = ConfigPersistentDelegate()

    lateinit var activityComponent: ActivityComponent

    override fun onCreate(savedInstanceState: Bundle?) {
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)

        configPersistDelegate.onCreate(this, savedInstanceState)
        activityComponent = configPersistDelegate.component + ActivityModule(this)

        super.onCreate(savedInstanceState)
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        configPersistDelegate.onSaveInstanceState(outState ?: return)
    }

    override fun onDestroy() {
        configPersistDelegate.onDestroy()
        compositeDisposable.clear()
        super.onDestroy()
    }

    open fun finishActivity() {
        finish()
    }

    //region Methods toolbar

    fun setupToolbar(toolbar: Toolbar?, displayHome: Boolean = true) {

        toolbar?.let {
            setSupportActionBar(it)

            supportActionBar?.let { actionBar ->
                actionBar.setDisplayShowHomeEnabled(displayHome)
                actionBar.setDisplayHomeAsUpEnabled(displayHome)
            }
        }
    }

    //endregion

    open fun getViewActivity(): WeakReference<Activity>? {
        return WeakReference(this)
    }

    open fun showUnexpectedError(listener: () -> Unit = DEFAULT_CLICK_LISTENER) {
        if (errorContainer == null) {
            Toast.makeText(this, R.string.error_unexpected, Toast.LENGTH_LONG).show()
        } else {
            showErrorView(R.drawable.ic_warning_24dp,
                    R.string.error_unexpected,
                    listener)
        }
    }

    open fun showNoNetworkError(listener: () -> Unit = DEFAULT_CLICK_LISTENER) {
        if (errorContainer == null) {
            Toast.makeText(this, R.string.error_no_network_connection, Toast.LENGTH_LONG).show()
        } else {
            showErrorView(R.drawable.ic_signal_wifi_off_24dp,
                    R.string.error_no_network_connection,
                    listener)
        }
    }

    open fun showLoadingIndicator(loadingVisible: Boolean) {
        Timber.d("showLoadingIndicator not implemented on this screen")
    }

    open fun hideErrorView() {
        errorContainer?.visible(false)
        mainContainer?.visible(true)
    }

    private fun showErrorView(icon: Int, text: Int, listener: () -> Unit) {
        mainContainer?.visible(false)
        errorContainer?.visible(true)

        errorIcon?.setImageDrawable(ContextCompat.getDrawable(this, icon))
        errorTitle?.text = getString(text)

        if (listener != DEFAULT_CLICK_LISTENER) {
            errorSubtitle?.text = getString(R.string.error_icon_tap_to_try_again)
        }

        errorContainer?.setOnClickListener { listener.invoke() }
    }
}