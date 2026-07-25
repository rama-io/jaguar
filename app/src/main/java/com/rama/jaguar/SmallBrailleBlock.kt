package com.rama.jaguar

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout

class SmallBrailleBlock @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    init {
        LayoutInflater.from(context).inflate(R.layout.small_braille_block, this, true)

        attrs?.let { setAttrs(context, it) }
    }

    private fun setAttrs(context: Context, attrs: AttributeSet) {
        for (i in 0 until attrs.attributeCount) {
            val name = attrs.getAttributeName(i)
            val value = attrs.getAttributeValue(i)
            when (name) {
                "value" -> {
                    // TODO
                }
            }
        }
    }

    fun setValue(text: String) {
    }
}