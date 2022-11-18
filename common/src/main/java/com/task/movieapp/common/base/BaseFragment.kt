package com.task.movieapp.common.base

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.Navigation

abstract class BaseFragment<T : BaseViewModel, B : ViewDataBinding> : Fragment() {
    private var _binding: B? = null
    val binding get() = _binding!!

    abstract val layoutRes: Int
    abstract val viewModel: T

    abstract fun observeViewModel()

    abstract fun viewCreated(view: View, savedInstanceState: Bundle?)

    open fun initBinding() {}

    open fun arrangeUI() {}

    open fun gatherArgs() {}

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        this._binding = DataBindingUtil.inflate(inflater, layoutRes, container, false)
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
        observeViewModel()
    }

    fun navigateToNextFragment(action: NavDirections) {
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
        activity?.let {
            it.overridePendingTransition(0,0)
        }
    }

    fun customNavHostBackPress(fragment: Fragment, navHostId: Int) {
        fragment.activity?.let {
            val controller = Navigation.findNavController(it, navHostId)
            controller.let { navController ->
                navController.popBackStack()
            }
        }
    }
}
