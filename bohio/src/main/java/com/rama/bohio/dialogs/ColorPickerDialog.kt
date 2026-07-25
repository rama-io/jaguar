package com.rama.bohio.dialogs

import android.app.Activity
import android.app.Dialog
import android.graphics.Color
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.rama.bohio.R
import com.rama.bohio.managers.ThemeManager
import com.rama.bohio.widgets.HSVSquareView
import com.rama.bohio.widgets.HueStripView

object ColorPickerDialog {

    fun show(
        activity: Activity,
        initialColor: Int,
        onColorSelected: (Int) -> Unit
    ) {
        val dialog = Dialog(activity)
        val view = LayoutInflater.from(activity).inflate(R.layout.wd_color_picker_dialog, null)

        dialog.setContentView(view)
        dialog.window?.setLayout(MATCH_PARENT, WRAP_CONTENT)

        ThemeManager.applyTheme(activity, view)

        val preview = view.findViewById<View>(R.id.preview)
        val hexInput = view.findViewById<EditText>(R.id.hex_input)
        val applyButton = view.findViewById<Button>(R.id.apply_button)
        val closeButton = view.findViewById<Button>(R.id.close_button)
        val hsvSquare = view.findViewById<HSVSquareView>(R.id.hsv_square)
        val hueSlider = view.findViewById<HueStripView>(R.id.hue_slider)
        
        val hsv = floatArrayOf(0f, 0f, 0f)
        Color.colorToHSV(initialColor, hsv)
        var hue = hsv[0]
        var saturation = hsv[1]
        var value = hsv[2]
        var isUpdatingFromCode = false

        fun updateUI() {
            val color = Color.HSVToColor(floatArrayOf(hue, saturation, value))
            preview.background.setTint(color)

            isUpdatingFromCode = true
            hexInput.setText(String.format("#%06X", 0xFFFFFF and color))
            isUpdatingFromCode = false

            hsvSquare.setHue(hue)
        }
        updateUI()

        hueSlider.onHueChanged = { newHue ->
            hue = newHue
            updateUI()
        }

        hsvSquare.onSaturationValueChanged = { s, v ->
            saturation = s
            value = v
            updateUI()
        }

        hexInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                if (isUpdatingFromCode) return
                try {
                    val color = Color.parseColor(s.toString())
                    Color.colorToHSV(color, hsv)
                    hue = hsv[0]
                    saturation = hsv[1]
                    value = hsv[2]
                    updateUI()
                } catch (_: Exception) {
                    // Ignore while the input is incomplete/invalid mid-typing or mid-paste.
                }
            }
        })

        hexInput.setOnEditorActionListener { _, _, _ ->
            try {
                Color.parseColor(hexInput.text.toString())
            } catch (_: Exception) {
                Toast.makeText(
                    activity,
                    activity.getString(R.string.toast_invalid_color),
                    Toast.LENGTH_SHORT
                ).show()
            }
            true
        }

        applyButton.setOnClickListener {
            onColorSelected(Color.HSVToColor(floatArrayOf(hue, saturation, value)))
            dialog.dismiss()
        }
        closeButton.setOnClickListener { dialog.dismiss() }

        dialog.show()
    }
}