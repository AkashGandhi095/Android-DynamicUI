package com.app.dynamicui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import com.app.dynamicui.databinding.ActivityAnotherBinding
import org.w3c.dom.Text

class AnotherActivity : AppCompatActivity() {
    private lateinit var binding :ActivityAnotherBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAnotherBinding.inflate(layoutInflater)
        setContentView(binding.root)
        buildDynamicUI()
    }

    private fun buildDynamicUI() {
        val linearLayout = LinearLayout(this)
        linearLayout.orientation = LinearLayout.HORIZONTAL
        val layoutParam = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 450)
        linearLayout.layoutParams = layoutParam

        val labelTextView = TextView(this)
        labelTextView.text = "Name"
        labelTextView.typeface = ResourcesCompat.getFont(this , R.font.poppins_semibold)
        val layoutParam1 = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT , LinearLayout.LayoutParams.MATCH_PARENT , 1.0f)
        labelTextView.layoutParams = layoutParam1
        linearLayout.addView(labelTextView)

        val valueTextView = TextView(this)
        valueTextView.text = "Peter Parker"
        labelTextView.typeface = ResourcesCompat.getFont(this , R.font.poppins_light)
        linearLayout.addView(valueTextView)

        binding.dynamicLinearLayout.addView(linearLayout)
    }

}