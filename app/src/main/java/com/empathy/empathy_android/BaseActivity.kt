package com.empathy.empathy_android

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProviders
import com.empathy.empathy_android.extensions.showDialogFragment
import dagger.android.AndroidInjection
import javax.inject.Inject

internal abstract class BaseActivity<VM>: AppCompatActivity() where VM: BaseViewModel {

    companion object {
        private const val LOADING_DIALOG = "loading_dialog"
    }

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
