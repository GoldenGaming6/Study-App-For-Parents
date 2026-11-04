package in.padhai.saathi

import android.app.Activity
import android.os.Bundle
import android.widget.*
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import java.security.MessageDigest

class LockActivity : Activity() {
    private val prefs by lazy { getSharedPreferences("parent_settings", MODE_PRIVATE) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val box = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(48, 60, 48, 30)
        }
        box.addView(TextView(this).apply { text = "🔐 माता-पिता लॉक"; textSize = 26f })
        val pin = EditText(this).apply {
            hint = "माता-पिता का पासवर्ड"
            inputType =  numberPassword()
        }
        box.addView(pin)
        box.addView(Button(this).apply {
            text = if (prefs.contains("pin_hash")) "पासवर्ड से खोलें" else "पहला पासवर्ड बनाएँ"
            setOnClickListener {
                val value = pin.text.toString()
                if (value.length < 4) {
                    pin.error = "कम से कम ४ अंक डालें"
                    return@setOnClickListener
                }
                val hash = sha(value)
                if (!prefs.contains("pin_hash")) {
                    prefs.edit().putString("pin_hash", hash).apply()
                    Toast.makeText(this@LockActivity, "पासवर्ड बनाया गया", Toast.LENGTH_LONG).show()
                } else if (prefs.getString("pin_hash", "") == hash) {
                    Toast.makeText(this@LockActivity, "माता-पिता मोड खुल गया", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    pin.error = "गलत पासवर्ड"
                }
            }
        })
        box.addView(Button(this).apply {
            text = "फिंगरप्रिंट / चेहरे से खोलें"
            setOnClickListener { authenticate() }
        })
        setContentView(box)
    }

    private fun numberPassword() = android.text.InputType.TYPE_CLASS_NUMBER or android.text.InputType.TYPE_NUMBER_VARIATION_PASSWORD
    private fun sha(s: String): String = MessageDigest.getInstance("SHA-256")
        .digest(s.toByteArray()).joinToString("") { "%02x".format(it) }

    private fun authenticate() {
        val manager = BiometricManager.from(this)
        if (manager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_STRONG or BiometricManager.Authenticators.BIOMETRIC_WEAK)
            != BiometricManager.BIOMETRIC_SUCCESS) {
            Toast.makeText(this, "इस फोन में बायोमेट्रिक सुविधा उपलब्ध नहीं है।", Toast.LENGTH_LONG).show()
            return
        }
        val prompt = BiometricPrompt(this, ContextCompat.getMainExecutor(this),
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    Toast.makeText(this@LockActivity, "पहचान सफल", Toast.LENGTH_SHORT).show()
                    finish()
                }
            })
        val info = BiometricPrompt.PromptInfo.Builder()
            .setTitle("माता-पिता की पहचान")
            .setSubtitle("फिंगरप्रिंट या उपलब्ध बायोमेट्रिक से पुष्टि करें")
            .setAllowedAuthenticators(BiometricManager.Authenticators.BIOMETRIC_STRONG or BiometricManager.Authenticators.BIOMETRIC_WEAK)
            .setNegativeButtonText("रद्द करें")
            .build()
        prompt.authenticate(info)
    }
}
