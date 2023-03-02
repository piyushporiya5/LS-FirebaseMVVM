package com.sociallogin.mvvm.viewmodel

import androidx.lifecycle.ViewModel
import com.sociallogin.mvvm.repository.FirebaseRepository

class DetailViewModel :ViewModel() {

    var repository: FirebaseRepository = FirebaseRepository()

    fun signOut() {
        repository.signOut()
    }
}