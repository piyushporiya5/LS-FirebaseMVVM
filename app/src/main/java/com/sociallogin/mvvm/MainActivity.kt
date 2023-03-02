package com.sociallogin.mvvm

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.GoogleAuthProvider
import com.sociallogin.mvvm.databinding.ActivityMainBinding
import com.sociallogin.mvvm.viewmodel.FirebaseViewModel
import java.util.*


class MainActivity : AppCompatActivity(), View.OnClickListener {


    private lateinit var binding: ActivityMainBinding
    private val callbackManager = CallbackManager.Factory.create()
    private var googleSignInClient: GoogleSignInClient? = null
    val viewModel: FirebaseViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        FirebaseApp.initializeApp(this)

        signOut()
        subscribeObserver()

        setClick()
        initGoogleSignInClient()
        initFacebookSignIn()


    }

    private fun signOut() {
        viewModel.signOut()
    }

    private fun subscribeObserver() {
        viewModel.registerAuthUser.observe(this) {
            if (it != null) {
                val intent = Intent(this@MainActivity, UserDetailActivity::class.java)
                intent.putExtra(Constant.DATA, it)
                startActivity(intent)
            }

        }

        viewModel.googleAuthUser.observe(this) {
            if (it != null) {
                val intent = Intent(this@MainActivity, UserDetailActivity::class.java)
                intent.putExtra(Constant.DATA, it)
                startActivity(intent)
            }
        }

        viewModel.facebookAuthUser.observe(this) {
            if (it != null) {
                val intent = Intent(this@MainActivity, UserDetailActivity::class.java)
                intent.putExtra(Constant.DATA, it)
                startActivity(intent)
            }
        }

        viewModel.singInAuthUser.observe(this) {
            if (it != null) {
                val intent = Intent(this@MainActivity, UserDetailActivity::class.java)
                intent.putExtra(Constant.DATA, it)
                startActivity(intent)
            }

        }

        viewModel.errorLiveData.observe(this) {
            if (it != null) {
                Toast.makeText(this, it.toString(), Toast.LENGTH_SHORT).show()
            }

        }
    }


    private fun initFacebookSignIn() {
        LoginManager.getInstance()
            .registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
                override fun onCancel() {
                    viewModel.errorLiveData.value = getString(R.string.cancel)
                }

                override fun onError(error: FacebookException) {
                    viewModel.errorLiveData.value = error.message
                }

                override fun onSuccess(result: LoginResult) {
                    handleFacebookAccessToken(result.accessToken)
                }

            })
    }

    private fun setClick() {
        binding.btnGoogle.setOnClickListener(this)
        binding.btnFacebook.setOnClickListener(this)
        binding.btnRegister.setOnClickListener(this)
        binding.btnLogin.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v) {
            binding.btnFacebook -> {
                LoginManager.getInstance().logInWithReadPermissions(
                    this, arrayListOf("user_photos", "email", "user_birthday", "public_profile")
                )
            }

            binding.btnGoogle -> {
                signIn()
            }

            binding.btnRegister -> {
                viewModel.registerWithCredntial(
                    binding.etEmail.text.toString().trim(),
                    binding.etPassword.text.toString().trim()
                )


            }
            binding.btnLogin -> {
                viewModel.signInWithCredntial(
                    binding.etEmail.text.toString().trim(),
                    binding.etPassword.text.toString().trim()
                )
            }
        }

    }

    private fun initGoogleSignInClient() {
        val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id)).requestEmail().build()
        googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        callbackManager.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 101) {
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val googleSignInAccount: GoogleSignInAccount =
                    task.getResult(ApiException::class.java)
                getGoogleAuthCredential(googleSignInAccount)
            } catch (e: ApiException) {
                e.printStackTrace()
                viewModel.errorLiveData.value = e.message
            }

        }
    }

    private fun getGoogleAuthCredential(googleSignInAccount: GoogleSignInAccount) {
        val googleTokenId = googleSignInAccount.idToken
        val googleAuthCredential = GoogleAuthProvider.getCredential(googleTokenId, null)
        signInWithGoogleAuthCredential(googleAuthCredential)
    }

    private fun signInWithGoogleAuthCredential(googleAuthCredential: AuthCredential) {
        viewModel.signInWithGoogle(googleAuthCredential)

    }

    private fun signIn() {
        if (googleSignInClient != null) {
            val signInIntent: Intent = googleSignInClient!!.signInIntent
            startActivityForResult(signInIntent, 101)
        }

    }

    private fun handleFacebookAccessToken(token: AccessToken) {
        val credential = FacebookAuthProvider.getCredential(token.token)
        viewModel.signInWithFacebook(credential)

    }


}