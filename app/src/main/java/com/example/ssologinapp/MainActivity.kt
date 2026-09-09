package com.example.ssologinapp

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputLayout
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.button.MaterialButton

/**
 * Sign-in screen.
 *
 * Presents a simulated Single Sign-On (SSO) entry point. The user can either:
 *  - Enter their email address and tap "Continue with SSO", which is validated locally
 *    before a simulated authentication success navigates to [WelcomeActivity], or
 *  - Tap "Continue with Google" / "Continue with Microsoft", which simply provides
 *    feedback that a real implementation would redirect to that identity provider.
 *
 * NOTE: No real authentication, network calls, or password collection happens here.
 */
class MainActivity : AppCompatActivity() {

    private lateinit var emailInputLayout: TextInputLayout
    private lateinit var emailEditText: TextInputEditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        emailInputLayout = findViewById(R.id.emailInputLayout)
        emailEditText = findViewById(R.id.emailEditText)

        val ssoButton: MaterialButton = findViewById(R.id.ssoButton)
        val googleButton: MaterialButton = findViewById(R.id.googleButton)
        val microsoftButton: MaterialButton = findViewById(R.id.microsoftButton)

        ssoButton.setOnClickListener { attemptSsoSignIn() }

        googleButton.setOnClickListener {
            Toast.makeText(this, getString(R.string.toast_google), Toast.LENGTH_SHORT).show()
        }

        microsoftButton.setOnClickListener {
            Toast.makeText(this, getString(R.string.toast_microsoft), Toast.LENGTH_SHORT).show()
        }

        // Clear any error state as soon as the user starts typing again.
        emailEditText.addTextChangedListener(object : android.text.TextWatcher {
            override fun afterTextChanged(s: android.text.Editable?) {
                emailInputLayout.error = null
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }

    /**
     * Validates the entered email address and, if valid, simulates a successful
     * SSO authentication by navigating to [WelcomeActivity] with the email attached.
     */
    private fun attemptSsoSignIn() {
        val email = emailEditText.text?.toString()?.trim().orEmpty()

        when {
            email.isEmpty() -> {
                emailInputLayout.error = getString(R.string.error_empty_email)
            }
            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                emailInputLayout.error = getString(R.string.error_invalid_email)
            }
            else -> {
                emailInputLayout.error = null
                // Simulate a successful SSO authentication flow.
                val intent = Intent(this, WelcomeActivity::class.java).apply {
                    putExtra(EXTRA_EMAIL, email)
                }
                startActivity(intent)
                finish()
            }
        }
    }

    companion object {
        const val EXTRA_EMAIL = "extra_email"
    }
}
