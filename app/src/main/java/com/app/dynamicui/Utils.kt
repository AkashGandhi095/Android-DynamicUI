package com.app.dynamicui

object Utils {

    class JsonConstants {
        companion object {
            val FIELD_LABEL = "field_label"
            val INPUT_FIELD_HINT = "input_field_hint"
            val INPUT_DATA_TYPE = "input_data_type"
            val OPTIONS = "options"
        }
    }

    enum class DATA_TYPE(val value :Int) {
        NUMBER(1) , TEXT(2) , MULTI_LINE_TEXT(3) ,
        DROP_DOWN(4) , RADIO_BUTTON(5)
    }



}