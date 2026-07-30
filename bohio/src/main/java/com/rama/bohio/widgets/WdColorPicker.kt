package com.rama.bohio.widgets

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.rama.bohio.R
import com.rama.bohio.dialogs.ColorPickerDialog

class WdColorPicker @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    private val title: TextView
    private val hexValue: TextView
    private val colorPreview: View

    private var currentColor = Color.WHITE
    private var listener: ((Int) -> Unit)? = null
    private var useDefaultPicker = true

    init {
        orientation = HORIZONTAL
        LayoutInflater.from(context).inflate(R.layout.wd_color_picker, this, true)

        title        = findViewById(R.id.title)
        hexValue     = findViewById(R.id.hexValue)
        colorPreview = findViewById(R.id.colorPreview)

        attrs?.let {
            val ta = context.obtainStyledAttributes(it, intArrayOf(R.attr.text))
            title.text = ta.getString(0) ?: ""
            ta.recycle()
        }

        isClickable = true
        isFocusable = true
        isFocusableInTouchMode = false

        updateUI()
        setOnClickListener { handleClick() }
    }

    private fun handleClick() {
        val activity = context as? Activity ?: return
        if (useDefaultPicker) {
            ColorPickerDialog.show(activity, currentColor) { color ->
                setColor(color)
                listener?.invoke(color)
            }
        } else {
            listener?.invoke(currentColor)
        }
    }

    fun setUseDefaultPicker(enabled: Boolean) { useDefaultPicker = enabled }
    fun setTitle(text: String)                { title.text = text }
    fun setColor(color: Int)                  { currentColor = color; updateUI() }
    fun getColor(): Int                       = currentColor
    fun setOnColorClickListener(cb: (Int) -> Unit) { listener = cb }

    private fun updateUI() {
        hexValue.text = String.format("#%06X", 0xFFFFFF and currentColor)
        colorPreview.background.setTint(currentColor)
    }
}
