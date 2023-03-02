package com.sociallogin.mvvm.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.sociallogin.mvvm.model.UserDataModal

class FirebaseRepository {
    companion object {
        public const val TAG = "FirebaseRepository"
    }

    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance();

    fun firebaseSignInWithGoogle(
        googleAuthCredential: AuthCredential,
        googleAuthUser: MutableLiveData<UserDataModal>
    ) {

        firebaseAuth.signInWithCredential(googleAuthCredential).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                // Sign in success, update UI with the signed-in user's information
                Log.d(TAG, "signInWithCredential:success")
                val isNewUser: Boolean = task.result.additionalUserInfo?.isNewUser == true
                val firebaseUser = firebaseAuth.currentUser
                if (firebaseUser != null) {
                    val uid = firebaseUser.uid
                    val name = firebaseUser.displayName
                    val email = firebaseUser.email
                    val user = UserDataModal(uid, name, email)

                    googleAuthUser.value = user
                } else {
                    Log.e(TAG, "firebaseSignInWithGoogle: null user")
                }
            } else {
                // If sign in fails, display a message to the user.
                Log.w(TAG, "signInWithCredential:failure", task.exception)

            }
        }

    }

    fun firebaseSignInWithCredential(
        email: String,
        password: String,
        singInAuthUser: MutableLiveData<UserDataModal>,
    ) {

        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                // Sign in success, update UI with the signed-in user's information
                Log.d(TAG, "signInWithCredential:success")
                val isNewUser: Boolean = task.result.additionalUserInfo?.isNewUser == true
                val firebaseUser = firebaseAuth.currentUser
                if (firebaseUser != null) {
                    val uid = firebaseUser.uid
                    val name = firebaseUser.displayName
                    val emailUser = firebaseUser.email
                    val user = UserDataModal(uid, name, emailUser)

                    singInAuthUser.value = user
                }
            } else {
                // If sign in fails, display a message to the user.
                Log.w(TAG, "signInWithCredential:failure", task.exception)

            }

        }

    }

    fun firebaseSignInWithFacebook(
        facebookAuthCredential: AuthCredential,
        facebookAuthUser: MutableLiveData<UserDataModal>
    ) {

        firebaseAuth.signInWithCredential(facebookAuthCredential).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                // Sign in success, update UI with the signed-in user's information
                Log.d(TAG, "signInWithCredential:success")
                val isNewUser: Boolean = task.result.additionalUserInfo?.isNewUser == true
                val firebaseUser = firebaseAuth.currentUser
                if (firebaseUser != null) {
                    val uid = firebaseUser.uid
                    val name = firebaseUser.displayName
                    val email = firebaseUser.email
                    val user = UserDataModal(uid, name, email)

                    facebookAuthUser.value = user
                } else {
                    Log.e(TAG, "firebaseSignInWithGoogle: null user")
                }
            } else {
                // If sign in fails, display a message to the user.
                Log.w(TAG, "signInWithCredential:failure", task.exception)

            }
        }


    }

    fun signOut() {
        if (firebaseAuth.currentUser != null) {
            firebaseAuth.signOut()
        }
    }

    fun firebaseRegisterWithCredential(
        email: String,
        password: String,
        registerAuthUser: MutableLiveData<UserDataModal>
    ) {

        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->

            if (task.isSuccessful) {
                // Sign in success, update UI with the signed-in user's information
                Log.d(TAG, "signInWithCredential:success")
                val isNewUser: Boolean = task.result.additionalUserInfo?.isNewUser == true
                val firebaseUser = firebaseAuth.currentUser
                if (firebaseUser != null) {
                    val uid = firebaseUser.uid
                    val name = firebaseUser.displayName
                    val emailUser = firebaseUser.email
                    val user = UserDataModal(uid, name, emailUser)

                    registerAuthUser.value = user
                }
            } else {
                // If sign in fails, display a message to the user.
                Log.w(TAG, "signInWithCredential:failure", task.exception)

            }

        }

    }


}