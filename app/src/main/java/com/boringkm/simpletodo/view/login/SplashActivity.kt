package com.boringkm.simpletodo.view.login

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import com.boringkm.simpletodo.R
import com.boringkm.simpletodo.util.App
import com.boringkm.simpletodo.view.BaseActivity
import com.boringkm.simpletodo.view.main.MainActivity
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class SplashActivity : BaseActivity(), LoginContract.View {
    private var hasUserToken: Boolean = false
    private var delayTime: Long = 0L
    private val delayMills = 3000L
    private var currentUser: FirebaseUser? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)

        App.get().getAppComponent().inject(this)

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
        currentUser = Firebase.auth.currentUser
        moveToMainActivity()
    }

    private fun moveToMainActivity() {
        if (currentUser == null) {
            return
        }
        currentUser!!.getIdToken(true).addOnCompleteListener {
            if (it.isSuccessful) {
                val presenter = LoginPresenter(this, it.result.token!!)
                hasUserToken = true
                val delay = getDelay()

                // TODO Handler 말고 coroutine 사용해보기
                @Suppress("DEPRECATION")
                Handler().postDelayed({
                    presenter.start()
                }, delay)
            }
        }
    }

    private fun getDelay(): Long {
        val delayedTime = System.currentTimeMillis() - delayTime
        val delay = if (delayedTime < delayMills) {
            delayMills - delayedTime
        } else {
            0L
        }
        return delay
    }

    override fun login(token: String) {
        val intent = Intent(this@SplashActivity, MainActivity::class.java)
        intent.putExtra("idToken", token)
        intent.putExtra("displayName", currentUser!!.displayName)
        startActivity(intent)
        finish()
    }
}