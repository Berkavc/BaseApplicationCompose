package com.task.movieapp.common.errordialog

import INTENT_ERROR_DIALOG_BUTTON_TEXT
import INTENT_ERROR_DIALOG_TITLE
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.LifecycleOwner
import com.task.movieapp.common.R
import com.task.movieapp.common.base.BaseActivity
import com.task.movieapp.common.databinding.ActivityErrorDialogBinding

class ErrorDialogActivity : BaseActivity<ErrorDialogViewModel, ActivityErrorDialogBinding>() {
    override val viewModel: ErrorDialogViewModel by viewModels()
    override val layoutRes: Int = R.layout.activity_error_dialog
    override var viewLifeCycleOwner: LifecycleOwner = this


    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_Dialog)
        super.onCreate(savedInstanceState)
//        setFinishOnTouchOutside(false)
        arrangeUI()
    }

    private fun arrangeUI() {
        binding.textViewErrorDialogTitle.text = intent.extras?.getString(INTENT_ERROR_DIALOG_TITLE)
            ?: resources.getString(R.string.error_dialog_failure_message)
        binding.buttonErrorDialog.text = intent.extras?.getString(INTENT_ERROR_DIALOG_BUTTON_TEXT)
            ?: resources.getString(R.string.error_dialog_ok_message)
        binding.buttonErrorDialog.setOnClickListener {
            errorDialogActivityDelegate?.callBack()
            errorDialogActivityDelegate = null
            finish()
        }


    }

    override fun observeViewModel() {}

    companion object {
        var errorDialogActivityDelegate: ErrorDialogActivityDelegate? = null
    }
}
