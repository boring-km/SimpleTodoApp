package com.boringkm.simpletodo.auth

import com.facebook.login.LoginManager
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

open class Auth (
    private val user: FirebaseUser? = Firebase.auth.currentUser
) {

    fun signOut() {
        if (user != null) {
            Firebase.auth.signOut()
            LoginManager.getInstance().logOut()
        }
    }
}

