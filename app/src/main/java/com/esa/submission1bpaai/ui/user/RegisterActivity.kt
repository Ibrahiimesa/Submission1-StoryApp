package com.esa.submission1bpaai.ui.user

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.esa.submission1bpaai.R
import com.esa.submission1bpaai.data.Result
import com.esa.submission1bpaai.databinding.ActivityRegisterBinding
import com.esa.submission1bpaai.util.ViewModelFactory

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var userViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        setupViewModel()
        setupAction()
        setAnimation()
    }

    private fun setupAction() {
        binding.tvLogin.setOnClickListener{
            startActivity(Intent(this, LoginActivity::class.java))
        }
        binding.btnSignup.setOnClickListener{
            if (valid()) {
                val name = binding.etName.text.toString()
                val email = binding.etEmail.text.toString()
                val password = binding.etPass.text.toString()
                userViewModel.userRegister(name, email, password).observe(this) {
                    when (it) {
                        is Result.Success -> {
                            showLoad(false)
                            Toast.makeText(this, it.data.message, Toast.LENGTH_SHORT).show()
                            startActivity(Intent(this, LoginActivity::class.java))
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
    }

    private fun valid() =
        binding.etEmail.error == null && binding.etPass.error == null && binding.etName.error == null && !binding.etEmail.text.isNullOrEmpty() && !binding.etPass.text.isNullOrEmpty() && !binding.etName.text.isNullOrEmpty()

    private fun setAnimation() {
        val appIcon = ObjectAnimator.ofFloat(binding.icon, View.ALPHA, 1f).setDuration(700)
        val appName = ObjectAnimator.ofFloat(binding.tvStory, View.ALPHA, 1f).setDuration(700)
        val etName = ObjectAnimator.ofFloat(binding.etName, View.ALPHA, 1f).setDuration(700)
        val etEmail = ObjectAnimator.ofFloat(binding.etEmail, View.ALPHA, 1f).setDuration(700)
        val etPass = ObjectAnimator.ofFloat(binding.etPass, View.ALPHA, 1f).setDuration(700)
        val btnSignup = ObjectAnimator.ofFloat(binding.btnSignup, View.ALPHA, 1f).setDuration(700)
        val txtHaveAc = ObjectAnimator.ofFloat(binding.tvHaveAcc, View.ALPHA, 1f).setDuration(700)
        val txtLogin = ObjectAnimator.ofFloat(binding.tvLogin, View.ALPHA, 1f).setDuration(700)

        val textAnim = AnimatorSet().apply {
            playTogether(appName, txtLogin, txtHaveAc)
        }
        val layoutAnim = AnimatorSet().apply {
            playTogether(etName, etPass, etEmail)
        }

        AnimatorSet().apply {
            playSequentially(
                appIcon,
                textAnim,
                layoutAnim,
                btnSignup
            )
            start()
        }
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
}