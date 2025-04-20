package com.paopeye.resttemplate

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.paopeye.domain.model.User
import com.paopeye.resttemplate.databinding.ActivityMainBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private val binding by viewBinding(ActivityMainBinding::inflate)
    private val viewModel: MainActivityViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        subscribeState()
        viewModel.onEvent(MainActivityViewModel.Event.OnCreate)
    }

    private fun subscribeState() {
        subscribeSingleState(viewModel.state) {
            when (it) {
                is MainActivityViewModel.State.ShowUserData -> showUserData(it.user)
            }
        }
    }

    private fun showUserData(user: User) {
        binding.userTextView.text = user.userId
    }
}
