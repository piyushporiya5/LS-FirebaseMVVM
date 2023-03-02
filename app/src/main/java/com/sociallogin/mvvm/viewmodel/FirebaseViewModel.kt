package com.sociallogin.mvvm.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.AuthCredential
import com.sociallogin.mvvm.model.UserDataModal
import com.sociallogin.mvvm.repository.FirebaseRepository
import kotlinx.coroutines.launch

class FirebaseViewModel : ViewModel() {

    var repository: FirebaseRepository = FirebaseRepository()

    var googleAuthUser: MutableLiveData<UserDataModal> = MutableLiveData()
    var registerAuthUser: MutableLiveData<UserDataModal> = MutableLiveData()
    var singInAuthUser: MutableLiveData<UserDataModal> = MutableLiveData()
    var facebookAuthUser: MutableLiveData<UserDataModal> = MutableLiveData()

    var errorLiveData: MutableLiveData<String> = MutableLiveData()

    fun signInWithGoogle(googleAuthCredential: AuthCredential) {
        viewModelScope.launch {
            repository.firebaseSignInWithGoogle(googleAuthCredential, googleAuthUser)
        }

    }

    fun signInWithCredntial(email: String, password: String) {
        viewModelScope.launch {
            repository.firebaseSignInWithCredential(email, password, singInAuthUser)
        }
    }

    fun registerWithCredntial(email: String, password: String) {
        repository.firebaseRegisterWithCredential(email, password, registerAuthUser)
    }

    fun signInWithFacebook(facebookAuthCredential: AuthCredential) {
        repository.firebaseSignInWithFacebook(facebookAuthCredential, facebookAuthUser)
    }

    fun signOut() {
        repository.signOut()
    }


}