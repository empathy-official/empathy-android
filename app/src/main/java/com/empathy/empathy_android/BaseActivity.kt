package com.empathy.empathy_android

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.empathy.empathy_android.extensions.showDialogFragment
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

internal abstract class BaseActivity<VM>: HasSupportFragmentInjector, AppCompatActivity() where VM: BaseViewModel {

    companion object {
        private const val LOADING_DIALOG = "loading_dialog"
    }

    @Inject lateinit var supportFragmentInjector: DispatchingAndroidInjector<Fragment>

    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory

    protected lateinit var viewModel: VM

    @LayoutRes
    protected abstract fun getLayoutRes(): Int

    protected abstract fun getViewModel(): Class<VM>

    private var loadingFragment: DialogFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)

        super.onCreate(savedInstanceState)
        setContentView(getLayoutRes())

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(getViewModel())
        viewModel.intent(intent)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        viewModel.onActivityResult(requestCode, resultCode, data)
    }

    override fun supportFragmentInjector(): AndroidInjector<Fragment> {
        return supportFragmentInjector
    }

    protected fun showLoadingDialog() {
        if(loadingFragment == null) {
            loadingFragment = LoadingDialogFragment()

            showDialogFragment(loadingFragment!!, LOADING_DIALOG)
        }
    }

    protected fun hideLoadingDialog() {
        loadingFragment?.let {
            it.dismissAllowingStateLoss()

            null
        }
    }

}
