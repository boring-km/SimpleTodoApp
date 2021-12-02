package com.boringkm.simpletodo

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.boringkm.simpletodo.util.App
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LoginActivity : BaseActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var facebookCallback: CallbackManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        App.get().getAppComponent().inject(this)

        initializeFirebaseAuth()
        initializeGoogleAuth()
        initializeFacebookAuth()

        DummyLoginButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("displayName", "Dummy")
            intent.putExtra("idToken", "testToken")
            startActivity(intent)
            finish()
        }
        val currentUser = auth.currentUser
        signUp(currentUser)
    }

    private fun initializeFirebaseAuth() {
        auth = Firebase.auth
    }

    private fun initializeGoogleAuth() {

        google_login_button.setOnClickListener {
            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()

            googleSignInClient = GoogleSignIn.getClient(this, gso)
            signIn()
        }
    }

    private fun initializeFacebookAuth() {
        facebookCallback = CallbackManager.Factory.create()
        facebook_login_button.registerCallback(facebookCallback, object : FacebookCallback<LoginResult> {
            override fun onSuccess(loginResult: LoginResult) {
                firebaseAuthWithFacebook(loginResult.accessToken)
            }

            override fun onCancel() {
                Toast.makeText(applicationContext, "취소됨", Toast.LENGTH_SHORT).show()
            }

            override fun onError(error: FacebookException?) {
                Toast.makeText(applicationContext, "로그인 에러", Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun signUp(user: FirebaseUser?) {
        if (user == null) {
            return
        }
        user.getIdToken(false).addOnCompleteListener {
            if (it.isSuccessful) {
                val token: String? = it.result.token
                if (token != null) {
                    val call = App.get().userService.register("Bearer $token")
                    call.enqueue(object: Callback<String> {
                        override fun onResponse(call: Call<String>, response: Response<String>) {
                            if (response.isSuccessful && response.body() != null) {
                                val result = response.body()
                                if (result == "SUCCESS") {
                                    Log.d("인증결과", "성공")
                                    val intent = Intent(this@LoginActivity, MainActivity::class.java)
                                    intent.putExtra("idToken", token)
                                    intent.putExtra("displayName", user.displayName)
                                    startActivity(intent)
                                    finish()
                                } else {
                                    Log.e("인증 실패", result!!)
                                }
                            }
                        }

                        override fun onFailure(call: Call<String>, error: Throwable) {
                            Log.e("유저 등록 에러", error.message!!)
                        }
                    })
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        facebookCallback.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)!!
                Log.d(TAG, "firebaseAuthWithGoogle:" + account.id)
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e)
            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    signUp(auth.currentUser)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                    signUp(null)
                }
            }
    }

    private fun firebaseAuthWithFacebook(token: AccessToken) {
        val credential = FacebookAuthProvider.getCredential(token.token)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    signUp(auth.currentUser)
                } else {
                    // 로그인 실패

                }
            }
    }

    private fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    companion object {
        private const val TAG = "GoogleActivity"
        private const val RC_SIGN_IN = 9001
    }
}