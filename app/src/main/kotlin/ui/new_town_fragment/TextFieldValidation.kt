package ru.deltadelete.lab8.ui.new_town_fragment

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import com.google.android.material.textfield.MaterialAutoCompleteTextView
import com.google.android.material.textfield.TextInputLayout
import ru.deltadelete.lab8.R


public class TextFieldValidation(val context: Context, private val view: View, private val inputLayout: TextInputLayout) : TextWatcher {
    override fun afterTextChanged(s: Editable?) {}
    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        when (view.id) {
            R.id.edit_text_town_name -> {
                validateTownName(view, context, inputLayout)
            }
            R.id.dropdown_country -> {
                validateCountry(view, context, inputLayout)
            }
        }
    }
}

public fun validateTownName(view: View, context: Context, inputLayout: TextInputLayout): Boolean {
    val editText = view as EditText
    if (editText.text.isEmpty()) {
        inputLayout.error = context.getString(R.string.can_not_be_empty)
        return false
    }
    inputLayout.error = null
    return true
}

public fun validateCountry(view: View, context: Context, inputLayout: TextInputLayout): Boolean {
    val selector = view as MaterialAutoCompleteTextView
    if (selector.text.isEmpty()) {
        inputLayout.error = context.getString(R.string.can_not_be_empty)
        return false
    }
    inputLayout.error = null
    return true
}