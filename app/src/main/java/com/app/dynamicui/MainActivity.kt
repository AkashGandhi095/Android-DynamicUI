package com.app.dynamicui

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.Gravity
import android.view.MotionEvent
import android.view.View
import android.view.View.OnFocusChangeListener
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.marginLeft
import com.app.dynamicui.databinding.ActivityMainBinding
import com.google.android.material.button.MaterialButton
import org.json.JSONArray

private const val TAG = "MainActivity***"
class MainActivity : AppCompatActivity() {

    private lateinit var binding :ActivityMainBinding
    private lateinit var inputTextV :EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val jsonArray = getJsonDataAsset()
        buildDynamicViews(jsonArray)
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun buildDynamicViews(jsonArray: JSONArray) {

        for(i in 0 until jsonArray.length()) {
            val jsonObj = jsonArray.getJSONObject(i)

            // create label textViews
            val labelTxtV = TextView(this)
            labelTxtV.text = jsonObj.getString(Utils.JsonConstants.FIELD_LABEL)
            labelTxtV.typeface = ResourcesCompat.getFont(this , R.font.poppins_bold)
            binding.linearLL.addView(labelTxtV)


            val layoutParam = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT , LinearLayout.LayoutParams.WRAP_CONTENT)
            layoutParam.setMargins(labelTxtV.marginLeft , 12 , layoutParam.rightMargin , 12)
            labelTxtV.layoutParams = layoutParam

            inputTextV = EditText(this)
            inputTextV.hint = jsonObj.getString(Utils.JsonConstants.INPUT_FIELD_HINT)
            inputTextV.background = ContextCompat.getDrawable(this , R.drawable.edit_bg_selector)
            inputTextV.setPadding(24,inputTextV.paddingTop , inputTextV.paddingRight , inputTextV.paddingBottom)
            inputTextV.layoutParams = layoutParam
            when(jsonObj.getInt(Utils.JsonConstants.INPUT_DATA_TYPE)) {
                Utils.DATA_TYPE.TEXT.value -> {
                 // create textView
                    inputTextV.inputType = InputType.TYPE_CLASS_TEXT
                    inputTextV.isSingleLine = true
                    binding.linearLL.addView(inputTextV)
                }

                Utils.DATA_TYPE.MULTI_LINE_TEXT.value -> {
                    // increase height
                    inputTextV.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT , 350).also {
                        it.setMargins(labelTxtV.marginLeft , 12 , layoutParam.rightMargin , 12)
                    }
                    inputTextV.gravity = Gravity.TOP
                    binding.linearLL.addView(inputTextV)
                }

                Utils.DATA_TYPE.NUMBER.value -> {
                    inputTextV.inputType = InputType.TYPE_CLASS_NUMBER
                    binding.linearLL.addView(inputTextV)
                }

                Utils.DATA_TYPE.RADIO_BUTTON.value -> {
                    val radioGrpLayout = RadioGroup(this)
                    radioGrpLayout.orientation = RadioGroup.HORIZONTAL
                    val options = jsonObj.getJSONArray(Utils.JsonConstants.OPTIONS)
                    var radioBtn :RadioButton
                    for(j in 0 until options.length()) {
                        radioBtn = RadioButton(this)
                        radioBtn.text = options.get(j).toString()
                        radioGrpLayout.addView(radioBtn)
                    }
                    binding.linearLL.addView(radioGrpLayout)
                }

                Utils.DATA_TYPE.DROP_DOWN.value -> {
                    val optionList = jsonObj.getJSONArray(Utils.JsonConstants.OPTIONS)
                    val arrayList = mutableListOf<String>()
                    for(k in 0 until optionList.length())
                        arrayList.add(optionList.getString(k))
                    val spinner = Spinner(this)
                    spinner.adapter = ArrayAdapter(this , android.R.layout.simple_list_item_1 , arrayList)
                    binding.linearLL.addView(spinner)
                }
            }

            TODO("latest changes amended to last commit(added more info)")
        }

    }



    private fun getJsonDataAsset() : JSONArray {
        return try {
            val data = assets.open("EditDynamic.json").bufferedReader().use { it.readText() }
            return JSONArray(data)
        } catch (e :Exception) {
            Log.e(TAG, "getJsonDataAsset: exception: ${e.localizedMessage}")
            JSONArray()
        }
    }

}