package com.paopeye.devshowcase.ui.profile.contact

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.text.Editable
import android.text.SpannableStringBuilder
import android.text.TextWatcher
import android.text.style.RelativeSizeSpan
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import com.paopeye.devshowcase.R
import com.paopeye.devshowcase.base.BaseFragment
import com.paopeye.devshowcase.databinding.FragmentProfileContactBinding
import com.paopeye.devshowcase.util.subscribeSingleState
import com.paopeye.kit.constant.Uris
import com.paopeye.kit.extension.orEmpty
import com.paopeye.kit.util.catchOrDefault
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileContactFragment : BaseFragment<FragmentProfileContactBinding>() {
    companion object {
        private const val COUNTER = 500
        fun newInstance(): ProfileContactFragment {
            return ProfileContactFragment()
        }
    }

    private val viewModel: ProfileContactViewModel by viewModel()
    override fun isUseToolbar() = false
    override fun isUseLeftImageToolbar() = false
    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentProfileContactBinding {
        return FragmentProfileContactBinding.inflate(inflater, container, false)
    }

    private val messageTextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
        override fun afterTextChanged(s: Editable?) = Unit
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            val isWithinLimit = s?.length.orEmpty() <= COUNTER
            binding.emailButton.isEnabled = isWithinLimit
        }
    }

    override fun setupView() {
        binding.messageEditLayout.isCounterEnabled = true
        binding.messageEditLayout.counterMaxLength = COUNTER
        binding.messageEdit.addTextChangedListener(messageTextWatcher)
        binding.emailButton.setOnClickListener {
            val subject = binding.subjectEdit.text.toString()
            val message = binding.messageEdit.text.toString()
            viewModel.onEvent(ProfileContactViewModel.Event.OnClickSendEmail(subject, message))
        }
        binding.linkedinLayout.setOnClickListener {
            viewModel.onEvent(ProfileContactViewModel.Event.OnClickLinkedinCard)
        }
    }

    override fun subscribeState() {
        subscribeSingleState(viewModel.state) {
            when (it) {
                is ProfileContactViewModel.State.NavigateToMailApp -> openEmailClient(
                    requireContext(),
                    it.email,
                    it.subject,
                    it.message
                )

                is ProfileContactViewModel.State.NavigateToExternalBrowser -> navigateToExternalBrowser(
                    it.url
                )
            }
        }
    }

    private fun openEmailClient(context: Context, email: String, subject: String, message: String) {
        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse(Uris.MAIL_TO)
            putExtra(Intent.EXTRA_EMAIL, arrayOf(email))
            putExtra(Intent.EXTRA_SUBJECT, subject)
            putExtra(Intent.EXTRA_TEXT, message)
        }
        val catch = {
            showToast(email)
            copyToClipboard(email)
        }
        catchOrDefault(catch.invoke()) {
            context.startActivity(intent)
        }
    }

    private fun showToast(email: String) {
        val text = getString(R.string.email_app_not_found, email)
        val biggerText = SpannableStringBuilder(text)
        biggerText.setSpan(RelativeSizeSpan(0.75f), 0, text.length, 0)
        Toast.makeText(
            context,
            biggerText,
            Toast.LENGTH_LONG
        ).show()
    }

    private fun copyToClipboard(email: String) {
        val clipboard =
            requireContext().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText(getString(R.string.email), email)
        clipboard.setPrimaryClip(clip)
    }
}
