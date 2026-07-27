package com.rama.jaguar

import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.rama.bohio.managers.ThemeManager
import com.rama.bohio.managers.PrefsManager as BohioPrefsManager

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

    private var dots: Set<Int> = emptySet()
    private var state: State = State.NEUTRAL

    init {
        LayoutInflater.from(context).inflate(R.layout.small_braille_block, this, true)
        attrs?.let { setAttrs(context, it) }
        render()
    }

    private fun setAttrs(context: Context, attrs: AttributeSet) {
        for (i in 0 until attrs.attributeCount) {
            when (attrs.getAttributeName(i)) {
                "value" -> attrs.getAttributeValue(i)?.let { setValue(it) }
            }
        }
    }

    fun setValue(text: String) {
        val sign = BrailleData.find(text)
        setEntry(sign?.dots ?: emptySet(), sign?.display ?: text)
    }

    fun setEntry(dots: Set<Int>, label: String) {
        this.dots = dots
        findViewById<TextView>(R.id.label)?.text = label
        render()
    }

    fun setState(state: State) {
        this.state = state
        render()
    }

    fun refreshTheme() = render()

    private fun render() {
        val palette =
            ThemeManager.paletteFor(BohioPrefsManager.getInstance(context).getTheme(), context)
        val onColor = ColorStateList.valueOf(
            when (state) {
                State.NEUTRAL -> palette.bg_4
                State.CORRECT -> palette.accent_1
                State.INCORRECT -> palette.danger
            }
        )
        val offColor = ColorStateList.valueOf(palette.bg_3)

        standardDotToViewId.forEach { (dotNumber, viewId) ->
            val frame = findViewById<FrameLayout>(viewId)
            val image = frame?.getChildAt(0) as? ImageView ?: return@forEach
            image.imageTintList = if (dotNumber in dots) onColor else offColor
        }
    }
}
