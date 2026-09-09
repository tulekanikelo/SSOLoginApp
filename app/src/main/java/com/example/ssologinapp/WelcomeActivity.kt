package com.example.ssologinapp

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton

/**
 * Shown after a simulated successful SSO sign-in.
 * Displays the email address the user authenticated with and lets them sign out,
 * which returns them to [MainActivity] and clears the back stack so they
 * cannot navigate "back" into an authenticated session.
 */
class WelcomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        val email = intent.getStringExtra(MainActivity.EXTRA_EMAIL) ?: "unknown@user.com"

        val emailDisplayText: TextView = findViewById(R.id.emailDisplayText)
        emailDisplayText.text = email

        val signOutButton: MaterialButton = findViewById(R.id.signOutButton)
        signOutButton.setOnClickListener { signOut() }
    }

    private fun signOut() {
        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        startActivity(intent)
        finish()
    }
}
