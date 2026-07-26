package com.rama.jaguar

import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.rama.bohio.R as BohioR

/**
 * A small, tappable braille cell used as one of the multiple-choice answers in the
 * Stage practice screen. Shows both the dot pattern and its letter/word label.
 */
class SmallBrailleBlock @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    enum class State { NEUTRAL, CORRECT, INCORRECT }

    // Same row-major -> standard dot numbering mapping as BrailleBlock (see there for detail).
    private val standardDotToViewId = mapOf(
        1 to R.id.small_dot_1,
        2 to R.id.small_dot_3,
        3 to R.id.small_dot_5,
        4 to R.id.small_dot_2,
        5 to R.id.small_dot_4,
        6 to R.id.small_dot_6
    )

    /** The sign id currently bound to this button (e.g. "a", "and"), or blank if unset. */
    var signId: String = ""
        private set

    init {
        LayoutInflater.from(context).inflate(R.layout.small_braille_block, this, true)
        isClickable = true
        isFocusable = true
        attrs?.let { setAttrs(context, it) }
    }

    private fun setAttrs(context: Context, attrs: AttributeSet) {
        for (i in 0 until attrs.attributeCount) {
            when (attrs.getAttributeName(i)) {
                "value" -> attrs.getAttributeValue(i)?.let { setValue(it) }
            }
        }
    }

    /** Binds this choice to a braille sign: renders its dots and its printed label. */
    fun setValue(text: String) {
        signId = text
        val sign = BrailleData.find(text)
        setPattern(sign?.dots ?: emptySet())
        findViewById<TextView>(R.id.label)?.text = sign?.display ?: text
        setState(State.NEUTRAL)
    }

    fun setPattern(dots: Set<Int>) {
        val onColor = ContextCompat.getColor(context, BohioR.color.foreground)
        val offColor = ContextCompat.getColor(context, BohioR.color.bg_3)

        standardDotToViewId.forEach { (dotNumber, viewId) ->
            val frame = findViewById<FrameLayout>(viewId)
            val image = frame?.getChildAt(0) as? ImageView ?: return@forEach
            image.imageTintList = ColorStateList.valueOf(if (dotNumber in dots) onColor else offColor)
        }
    }

    /** Highlights the button green/red after an answer is submitted, or resets it. */
    fun setState(state: State) {
        val colorRes = when (state) {
            State.NEUTRAL -> BohioR.color.bg_4
            State.CORRECT -> BohioR.color.accent_1
            State.INCORRECT -> BohioR.color.danger
        }
        findViewById<android.view.View>(R.id.small_braille_grid)?.setBackgroundColor(
            ContextCompat.getColor(context, colorRes)
        )
    }
}
