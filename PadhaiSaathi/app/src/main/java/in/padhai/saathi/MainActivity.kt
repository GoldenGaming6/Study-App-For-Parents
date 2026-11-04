package in.padhai.saathi

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.widget.*
import android.graphics.Typeface
import android.view.Gravity
import android.view.ViewGroup
import android.graphics.Color

class MainActivity : Activity() {
    private fun dp(v: Int) = (v * resources.displayMetrics.density).toInt()
    private lateinit var status: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val page = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(dp(22), dp(28), dp(22), dp(20))
            setBackgroundColor(Color.rgb(246, 247, 255))
        }
        val title = TextView(this).apply {
            text = "📚 पढ़ाई साथी"
            textSize = 30f
            setTextColor(Color.rgb(45, 48, 100))
            typeface = Typeface.DEFAULT_BOLD
        }
        page.addView(title)
        page.addView(label("आज का लक्ष्य", 22f, true))
        page.addView(label("आज मन लगाकर पढ़ाई करें। छोटे लक्ष्य बनाएँ और उन्हें पूरा करें।", 16f))
        page.addView(label("आज के लक्ष्य", 19f, true))
        val goals = listOf("गणित — ३० मिनट", "विज्ञान — ३० मिनट", "हिंदी — २० मिनट")
        goals.forEach { goal ->
            val cb = CheckBox(this).apply { text = goal; textSize = 17f }
            page.addView(cb)
        }
        page.addView(label("माता-पिता नियंत्रण", 19f, true))
        page.addView(label("ऐप रोकने की सुविधा चालू करने के लिए माँ/अभिभावक की अनुमति आवश्यक है।", 14f))
        val setup = Button(this).apply {
            text = "ऐप रोकने की अनुमति सेट करें"
            setOnClickListener {
                startActivity(Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS))
            }
        }
        page.addView(setup)
        val parent = Button(this).apply {
            text = "माता-पिता लॉक"
            setOnClickListener { startActivity(Intent(this@MainActivity, LockActivity::class.java)) }
        }
        page.addView(parent)
        status = label("ध्यान दें: यह शुरुआती संस्करण है। ऐप रोकने की सेवा अभी परीक्षण हेतु है।", 13f)
        page.addView(status)
        setContentView(ScrollView(this).apply { addView(page) })
    }

    private fun label(textValue: String, size: Float, bold: Boolean = false): TextView =
        TextView(this).apply {
            text = textValue
            textSize = size
            setTextColor(Color.rgb(35, 38, 60))
            if (bold) typeface = Typeface.DEFAULT_BOLD
            setPadding(0, dp(12), 0, dp(8))
        }
}
