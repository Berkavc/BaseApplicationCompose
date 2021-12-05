package com.task.movieapp.common.base

import INTENT_ERROR_DIALOG_BUTTON_TEXT
import INTENT_ERROR_DIALOG_TITLE
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.task.movieapp.common.R
import com.task.movieapp.common.errordialog.ErrorDialogActivity
import com.task.movieapp.common.errordialog.ErrorDialogActivityDelegate
import com.task.movieapp.domain.model.ResultData

abstract class BaseFragment<T : BaseViewModel, B : ViewDataBinding> : Fragment(),
    ErrorDialogActivityDelegate {
    abstract val layoutRes: Int
    abstract val viewModel: T

    open fun initBinding() {
        errorDialogActivityDelegate = this
    }

    abstract fun observeViewModel()
    abstract fun viewCreated(view: View, savedInstanceState: Bundle?)

    open fun arrangeUI() {

    }

    open fun gatherArgs() {

    }

    private var errorDialogCallBack: () -> Unit? = {}

    private var _binding: B? = null
    val binding get() = _binding!!

    companion object {
        internal var errorDialogActivityDelegate: ErrorDialogActivityDelegate? = null
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        this._binding = DataBindingUtil.inflate(inflater, layoutRes, container, false)
        initBinding()
        return binding.root
    }

    fun onCreateViewTheme(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val contextThemeWrapper = ContextThemeWrapper(activity, R.style.Theme_Dialog)
        val localInflater = inflater.cloneInContext(contextThemeWrapper)
        this._binding = DataBindingUtil.inflate(localInflater, layoutRes, container, false)
        initBinding()
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        gatherArgs()
        viewCreated(view, savedInstanceState)
        arrangeUI()
        observeLoadingAndError()
        observeViewModel()
    }

    private fun observeLoadingAndError() {
        viewModel.loadingErrorState.observe(viewLifecycleOwner) {
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
                else -> {
                    hideLoading()
                }
            }
        }
    }

    private val loadingAlertDialog by lazy {
        context?.let {
            Dialog(it).apply {
                requestWindowFeature(Window.FEATURE_NO_TITLE)
                window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                setContentView(R.layout.dialog_loading)
                setCancelable(false)
            }
        }
    }

    private fun showLoading() {
        loadingAlertDialog?.show()
    }

    private fun hideLoading() {
        loadingAlertDialog?.dismiss()
    }

    fun showErrorDialog(
        message: String? = null,
        buttonMessage: String? = null,
        callback: () -> Unit? = {}
    ) {
        this.let {
            errorDialogCallBack = callback
            activity?.let {
                val intent = Intent(it, ErrorDialogActivity::class.java)
                intent.putExtra(INTENT_ERROR_DIALOG_TITLE, message)
                intent.putExtra(INTENT_ERROR_DIALOG_BUTTON_TEXT, buttonMessage)
                startActivity(intent)
            }

        }
    }


    fun navigateToNextFragment(action: NavDirections) {
        view?.let { view ->
            viewModelStore.clear()
            Navigation.findNavController(view).navigate(action)
        }
    }

    fun navigateToNextFragmentWithoutClear(action: NavDirections) {
        view?.let { view ->
            Navigation.findNavController(view).navigate(action)
        }
    }

    fun navigateToUpFragment() {
        view?.let {
            Navigation.findNavController(it).navigateUp()
        }
    }

    fun navigateToNextActivity(intent: Intent) {
        activity?.finish()
        startActivity(intent)
    }

    fun navigateToWithoutPopNextActivity(intent: Intent) {
        startActivity(intent)
    }

    fun customNavHostBackPress(fragment: Fragment, navHostId: Int) {
        fragment.activity?.let {
            val controller = Navigation.findNavController(it, navHostId)
            controller.let { navController ->
                navController.popBackStack()
            }
        }
    }

    override fun callBack() {
        errorDialogCallBack.invoke()
    }

    fun finishFragment() {
//        parentFragmentManager.beginTransaction().remove(parentFragmentManager.fragments[0]).commit()
//        parentFragmentManager.popBackStack()
        findNavController().popBackStack()
//        findNavController().popBackStack(parentFragmentManager.fragments[0].id, true)
    }


}
