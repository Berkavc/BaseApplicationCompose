package com.task.movieapp.common.base

import INTENT_ERROR_DIALOG_BUTTON_TEXT
import INTENT_ERROR_DIALOG_TITLE
import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Window
import android.view.WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LifecycleOwner
import com.task.movieapp.common.R
import com.task.movieapp.common.errordialog.ErrorDialogActivity
import com.task.movieapp.common.errordialog.ErrorDialogActivityDelegate
import com.task.movieapp.domain.model.ResultData

abstract class BaseActivity<T : BaseViewModel, B : ViewDataBinding> : AppCompatActivity(),
    ErrorDialogActivityDelegate {
    abstract val layoutRes: Int
    abstract val viewModel: T

    abstract var viewLifeCycleOwner: LifecycleOwner

    private var errorDialogCallBack: () -> Unit? = {}

    open fun initBinding() {
        this._binding?.lifecycleOwner = this
        viewLifeCycleOwner = this
        errorDialogActivityDelegate = this
    }

    abstract fun observeViewModel()


    private var _binding: B? = null
    val binding get() = _binding!!

    companion object {
        internal var errorDialogActivityDelegate: ErrorDialogActivityDelegate? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.window.setSoftInputMode(SOFT_INPUT_ADJUST_PAN)
        _binding = DataBindingUtil.inflate(layoutInflater, layoutRes, null, false)
        setContentView(_binding!!.root)
        initBinding()
        observeLoadingAndError()
        observeViewModel()
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun observeLoadingAndError() {
        viewModel.loadingErrorState.observe(viewLifeCycleOwner) {
            when (it) {
                is ResultData.Loading -> {
                    showLoading()
                }
                is ResultData.Success -> {
                    hideLoading()
                }
                is ResultData.Failed -> {
                    hideLoading()
                    showErrorDialog(it.errorTitle, it.buttonTitle, it.callback)
                }
            }
        }

    }

    private val loadingAlertDialog by lazy {
        this.let {
            Dialog(it).apply {
                requestWindowFeature(Window.FEATURE_NO_TITLE)
                window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                setContentView(R.layout.dialog_loading)
                setCancelable(false)
            }
        }
    }

    private fun showLoading() {
        loadingAlertDialog.show()
    }

    private fun hideLoading() {
        loadingAlertDialog.dismiss()
    }

    private fun showErrorDialog(
        message: String? = null,
        buttonMessage: String? = null,
        callback: () -> Unit? = {}
    ) {
        this.let {
            errorDialogCallBack = callback
            val intent = Intent(this, ErrorDialogActivity::class.java)
            intent.putExtra(INTENT_ERROR_DIALOG_TITLE, message)
            intent.putExtra(INTENT_ERROR_DIALOG_BUTTON_TEXT, buttonMessage)
            startActivity(intent)
        }
    }

    override fun callBack() {
        errorDialogCallBack.invoke()
    }

    fun navigateToNextActivity(activity: Activity, intent: Intent) {
        activity.finish()
        startActivity(intent)
    }

    fun navigateToWithoutPopNextActivity(intent: Intent) {
        startActivity(intent)
    }

}
