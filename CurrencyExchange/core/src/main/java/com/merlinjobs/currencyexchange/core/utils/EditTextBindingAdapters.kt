package com.merlinjobs.currencyexchange.core.utils

import android.databinding.BindingAdapter
import android.text.TextWatcher
import android.widget.EditText

/**
 * Created by Alexander Fermin (alexfer06@gmail.com) on 03/08/2018.
 */

open class EditTextBindingAdapters {

    companion object {
        @BindingAdapter("textChangedListener")
        @JvmStatic
        fun bindTextWatcher(editText: EditText, textWatcher: TextWatcher) {
            editText.addTextChangedListener(textWatcher)
        }
    }
}
