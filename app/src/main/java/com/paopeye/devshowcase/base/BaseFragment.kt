package com.paopeye.devshowcase.base

import android.animation.ArgbEvaluator
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.paopeye.devshowcase.MainActivity
import com.paopeye.devshowcase.R
import com.paopeye.devshowcase.ToolbarListener
import com.paopeye.kit.extension.emptyString

abstract class BaseFragment<VB : ViewBinding> : Fragment() {
    abstract fun isUseToolbar(): Boolean
    abstract fun isUseLeftImageToolbar(): Boolean
    abstract fun setupView()
    abstract fun subscribeState()
    abstract fun inflateBinding(inflater: LayoutInflater, container: ViewGroup?): VB
    open fun isFullScreen(): Boolean = false
    open fun toolbarTitle(): String = emptyString()
    open fun toolbarLeftClickListener(): (() -> Unit) = {
        requireActivity().onBackPressedDispatcher.onBackPressed()
    }

    private lateinit var container: ViewGroup
    private lateinit var toolbarListener: ToolbarListener
    private var _binding: VB? = null
    protected val binding: VB
        get() = _binding ?: throw IllegalStateException(
            "Fragment ${this.javaClass.name} Binding Error"
        )

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context !is MainActivity) {
            throw IllegalArgumentException(
                "Fragment ${this.javaClass.name} Illegal Error"
            )
        }
        toolbarListener = context
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        container?.let { this.container = it }
        _binding = inflateBinding(inflater, container)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        subscribeState()
    }

    override fun onResume() {
        super.onResume()
        (activity as MainActivity).setActivityInset(isFullScreen())
        toolbarListener.setTitleToolbar(toolbarTitle())
        toolbarListener.setVisibilityToolbar(isUseToolbar())
        toolbarListener.setVisibilityLeftImageButton(isUseLeftImageToolbar())
        toolbarListener.setListenerLeftImageButton(toolbarLeftClickListener())
        toolbarListener.setBackgroundStatusBar(Color.TRANSPARENT)
        toolbarListener.setBackgroundToolbar(Color.TRANSPARENT)
    }

    protected fun replaceFragment(fragment: Fragment) {
        (activity as MainActivity).replaceFragment(fragment)
    }

    protected fun replaceFragmentWithSharedElement(
        fragment: Fragment,
        sharedElement: View,
        sharedElementName: String
    ) {
        (activity as MainActivity).replaceFragmentWithSharedElement(
            fragment,
            sharedElement,
            sharedElementName
        )
    }

    protected fun updateToolbar(
        title: String,
        isLeftImageVisible: Boolean,
        toolbarBackgroundColor: Int?,
        statusBarBackgroundColor: Int?
    ) {
        toolbarListener.setTitleToolbar(title)
        toolbarListener.setVisibilityLeftImageButton(isLeftImageVisible)
        updateBackgroundStatusBarColor(statusBarBackgroundColor)
        toolbarListener.setBackgroundToolbar(toolbarBackgroundColor)
    }

    protected fun updateBackgroundStatusBarColor(statusBarBackgroundColor: Int?) {
        toolbarListener.setBackgroundStatusBar(statusBarBackgroundColor)
    }

    protected fun updateToolbarStyling(
        totalScrollY: Int,
        maxScroll: Int,
        titleText: String
    ) {
        val scrollRatio = (totalScrollY.toFloat() / maxScroll).coerceIn(0f, 1f)
        val startColor = ContextCompat.getColor(requireContext(), R.color.an_primary_variant)
        val endColor = Color.TRANSPARENT
        val toolbarColor = ArgbEvaluator().evaluate(1 - scrollRatio, startColor, endColor) as Int
        val title = if (scrollRatio == 1f) titleText else emptyString()
        updateToolbar(title, false, toolbarColor, toolbarColor)
    }

    protected fun showLoading() {
        (activity as MainActivity).loadingVisibility(true)
    }

    protected fun hideLoading() {
        (activity as MainActivity).loadingVisibility(false)
    }

    protected fun navigateToExternalBrowser(url: String) {
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(browserIntent)
    }
}
