package com.esa.submission1bpaai.ui.user

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.esa.submission1bpaai.ui.story.MainActivity
import com.esa.submission1bpaai.R
import com.esa.submission1bpaai.data.Result
import com.esa.submission1bpaai.data.model.User
import com.esa.submission1bpaai.databinding.ActivityLoginBinding
import com.esa.submission1bpaai.util.ViewModelFactory

class LoginActivity : AppCompatActivity() {

    private lateinit var binding : ActivityLoginBinding
    private lateinit var userViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        setupViewModel()
        setupAction()
        setAnimation()
    }

    private fun setupAction() {
        binding.btnLogin.setOnClickListener {
            if (valid()) {
                val email = binding.etEmail.text.toString()
                val password = binding.etPass.text.toString()
                userViewModel.userLogin(email, password).observe(this) {
                    when (it) {
                        is Result.Success -> {
                            showLoad(false)
                            val response = it.data
                            saveUserData(
                                User(
                                    response.loginResult?.name.toString(),
                                    response.loginResult?.token.toString(),
                                    true
                                )
                            )
                            startActivity(Intent(this, MainActivity::class.java))
                            finishAffinity()
                        }
                        is Result.Loading -> showLoad(true)
                        is Result.Error -> {
                            Toast.makeText(this, it.error, Toast.LENGTH_SHORT).show()
                            showLoad(false)
                        }
                    }
                }
            } else {
                Toast.makeText(
                    this,
                    resources.getString(R.string.check_input),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        binding.tvSignup.setOnClickListener{
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

    private fun setAnimation() {
        val appIcon = ObjectAnimator.ofFloat(binding.icon, View.ALPHA, 1f).setDuration(700)
        val appName = ObjectAnimator.ofFloat(binding.tvStory, View.ALPHA, 1f).setDuration(700)
        val etEmail = ObjectAnimator.ofFloat(binding.etEmail, View.ALPHA, 1f).setDuration(700)
        val etPass = ObjectAnimator.ofFloat(binding.etPass, View.ALPHA, 1f).setDuration(700)
        val btnLogin = ObjectAnimator.ofFloat(binding.btnLogin, View.ALPHA, 1f).setDuration(700)
        val txtHaveAc = ObjectAnimator.ofFloat(binding.tvDontHaveAcc, View.ALPHA, 1f).setDuration(700)
        val txtSignup = ObjectAnimator.ofFloat(binding.tvSignup, View.ALPHA, 1f).setDuration(700)

        val textAnim = AnimatorSet().apply {
            playTogether(appName, txtSignup, txtHaveAc)
        }
        val layoutAnim = AnimatorSet().apply {
            playTogether(etPass, etEmail)
        }

        AnimatorSet().apply {
            playSequentially(
                appIcon,
                textAnim,
                layoutAnim,
                btnLogin
            )
            start()
        }
    }

    private fun saveUserData(user: User) {
        userViewModel.saveUser(user)
    }

    private fun setupViewModel() {
        val factory: ViewModelFactory = ViewModelFactory.getInstance(this)
        userViewModel = ViewModelProvider(this, factory)[UserViewModel::class.java]
    }

    private fun showLoad(isLoad: Boolean) {
        if (isLoad){
            binding.progressBar.visibility = View.VISIBLE
        }
        else {
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun valid() =
        binding.etEmail.error == null && binding.etPass.error == null && !binding.etEmail.text.isNullOrEmpty() && !binding.etPass.text.isNullOrEmpty()
}