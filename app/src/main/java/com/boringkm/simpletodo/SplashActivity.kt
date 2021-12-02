package com.boringkm.simpletodo

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.boringkm.simpletodo.util.App
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class SplashActivity : AppCompatActivity() {
    private var hasUserToken: Boolean = false
    private var delayTime: Long = 0L
    private val delayMills = 3000L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)

        App.get().getAppComponent().inject(this)

        delayTime = System.currentTimeMillis()
        @Suppress("DEPRECATION")
        Handler().postDelayed({
            if (!hasUserToken) {                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
        }, delayMills)
    }

    override fun onStart() {
        super.onStart()
        val currentUser = Firebase.auth.currentUser
        moveToMainActivity(currentUser)
    }

    private fun moveToMainActivity(user: FirebaseUser?) {
        if (user == null) {
            return
        }
        user.getIdToken(true).addOnCompleteListener {
            if (it.isSuccessful) {

                val delayedTime = System.currentTimeMillis() - delayTime
                val delay = if (delayedTime < delayMills) {
                    delayMills - delayedTime
                } else {
                    0L
                }

                hasUserToken = true
                Handler().postDelayed({
                    App.get().userService.register("Bearer ${it.result.token}")
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({ result ->
                            if (result == "SUCCESS") {
                                val intent = Intent(this@SplashActivity, MainActivity::class.java)
                                intent.putExtra("idToken", it.result.token)
                                intent.putExtra("displayName", user.displayName)
                                startActivity(intent)
                                finish()
                            }
                        }, { error -> Log.e("로그인 에러", error.message!!) }).apply {  }
                }, delay)
            }
        }
    }
}