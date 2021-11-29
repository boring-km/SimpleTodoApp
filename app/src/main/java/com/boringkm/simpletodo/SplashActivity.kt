package com.boringkm.simpletodo

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class SplashActivity : AppCompatActivity() {
    private var hasUserToken: Boolean = false
    private var delayTime: Long = 0L
    private val delayMills = 3000L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)

        delayTime = System.currentTimeMillis()
        @Suppress("DEPRECATION")
        Handler().postDelayed({
            if (!hasUserToken) {
                val intent = Intent(this, LoginActivity::class.java)
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
                    val intent = Intent(this@SplashActivity, MainActivity::class.java)
                    intent.putExtra("idToken", it.result.token)
                    intent.putExtra("displayName", user.displayName)
                    startActivity(intent)
                    finish()
                }, delay)
            }
        }
    }
}