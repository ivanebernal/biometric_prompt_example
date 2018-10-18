package com.ivanebernal.bioprompttest

import android.os.Bundle
import android.os.Looper
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.biometrics.BiometricPrompt
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {

    var prompt: BiometricPrompt? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        prompt = BiometricPrompt(this,
                Executors.newSingleThreadExecutor(),
                object : BiometricPrompt.AuthenticationCallback() {
                    override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                        runOnUiThread {
                            Toast.makeText(this@MainActivity, "Success!", Toast.LENGTH_LONG).show()
                            cancelAuth()
                        }
                    }

                    override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                        runOnUiThread {
                            Toast.makeText(this@MainActivity, errString, Toast.LENGTH_LONG).show()
                            cancelAuth()
                        }
                    }

                    override fun onAuthenticationFailed() {
                        runOnUiThread {
                            Toast.makeText(this@MainActivity, "Failure!", Toast.LENGTH_LONG).show()
                            cancelAuth()
                        }
                    }
                })
        val dialog = BiometricPrompt.PromptInfo.Builder()
                .setTitle("Biometric")
                .setSubtitle("This is a test")
                .setDescription("Testing BiometricPrompt")
                .setNegativeButtonText("Cancel")
                .build()
        biometricsButton.setOnClickListener {
            prompt?.authenticate(dialog)
        }

    }

    private fun cancelAuth() {
        prompt?.cancelAuthentication()
    }
}
