package com.college.kjsieitproject

import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task

class LoginPage : MainActivity() {
    private lateinit var mGoogleSignInClient: GoogleSignInClient
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_page)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("723566451883-hodvj0gab2iv9pnspr1cru01d4c5qnqc.apps.googleusercontent.com")
            .requestEmail()
            .build()

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        val googleLoginButton = findViewById<SignInButton>(R.id.sign_in_button)
        googleLoginButton.setOnClickListener {
            signIn()
        }

        //updateUI(account)


        email_checker()

    }

    fun email_checker(){

    }

    fun signIn() {
        val intent = Intent(this, ProfilePage::class.java)
        resultLauncher.launch(intent)
    }

    var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            // There are no request codes
            val data: Intent? = result.data
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(
                ApiException::class.java
            )
            // Signed in successfully
            val googleId = account?.id ?: ""
            Log.i("Google ID",googleId)
            val googleFirstName = account?.givenName ?: ""
            Log.i("Google First Name", googleFirstName)
            val googleLastName = account?.familyName ?: ""
            Log.i("Google Last Name", googleLastName)
            val googleEmail = account?.email ?: ""
            Log.i("Google Email", googleEmail)
            val googleProfilePicURL = account?.photoUrl.toString()
            Log.i("Google Profile Pic URL", googleProfilePicURL)
            val googleIdToken = account?.idToken ?: ""
            Log.i("Google ID Token", googleIdToken)
        } catch (e: ApiException) {
            // Sign in was unsuccessful
            Log.e(
                "failed code=", e.statusCode.toString()
            )
        }
    }

}