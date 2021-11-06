package com.boringkm.simpletodo.auth

import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

open class Auth {
    fun signOut() {
        Firebase.auth.signOut()
    }
}

