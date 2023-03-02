package com.sociallogin.mvvm

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.sociallogin.mvvm.databinding.ActivityUserDetailBinding
import com.sociallogin.mvvm.model.UserDataModal
import com.sociallogin.mvvm.viewmodel.DetailViewModel

class UserDetailActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var binding: ActivityUserDetailBinding

    val viewModel: DetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val data = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(Constant.DATA, UserDataModal::class.java)
        } else {
            intent.getParcelableExtra(Constant.DATA) as UserDataModal?
        }

        if (data != null) {
            binding.name.text = data.name ?: "N/A"
            binding.email.text = data.email ?: "N/A"
        }

        setClick()
    }

    private fun setClick() {
        binding.back.setOnClickListener(this)
        binding.signOut.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v) {
            binding.back -> {
                onBackPressed()
            }

            binding.signOut -> {
                viewModel.signOut()
                onBackPressed()
            }
        }
    }
}