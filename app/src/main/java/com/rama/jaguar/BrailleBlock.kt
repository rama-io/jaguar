package com.rama.jaguar

import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import com.rama.bohio.managers.ThemeManager
import com.rama.bohio.util.UiActions
import com.rama.jaguar.braille.BrailleCatalog
import com.rama.jaguar.braille.BrailleLanguage
import com.rama.bohio.managers.PrefsManager as BohioPrefsManager

/**
 * The big, tappable braille cell the player builds an answer with: tapping a dot toggles it
 * on/off. This is the primary input widget on the Stage screen.
 */
class BrailleBlock @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    // braille_block.xml lays dot_1..dot_6 out row-major in a 2-column GridLayout, so the
    // visual order is: dot_1=top-left, dot_2=top-right, dot_3=mid-left, dot_4=mid-right,
    // dot_5=bottom-left, dot_6=bottom-right. Map that to standard braille dot numbering
    // (1,2,3 down the left column; 4,5,6 down the right column).
    private val standardDotToViewId = mapOf(
        1 to R.id.dot_1,
        2 to R.id.dot_3,
        3 to R.id.dot_5,
        4 to R.id.dot_2,
        5 to R.id.dot_4,
        6 to R.id.dot_6
    )
    private val viewIdToStandardDot = standardDotToViewId.entries.associate { (k, v) -> v to k }

    private val activeDots = mutableSetOf<Int>()

    /** Called whenever the player taps a dot and the active pattern changes. */
    var onPatternChanged: ((Set<Int>) -> Unit)? = null

    init {
        LayoutInflater.from(context).inflate(R.layout.braille_block, this, true)
        attrs?.let { setAttrs(context, it) }
        standardDotToViewId.values.forEach { viewId ->
            val frame = findViewById<FrameLayout>(viewId)
            UiActions.setClickWithHaptics(frame) { toggleDot(viewId) }
        }
        render()
    }

    private fun setAttrs(context: Context, attrs: AttributeSet) {
        for (i in 0 until attrs.attributeCount) {
            when (attrs.getAttributeName(i)) {
                "value" -> attrs.getAttributeValue(i)?.let { setValue(it) }
            }
        }
    }

    private fun toggleDot(viewId: Int) {
        val dotNumber = viewIdToStandardDot[viewId] ?: return
        if (!activeDots.add(dotNumber)) activeDots.remove(dotNumber)
        render()
        onPatternChanged?.invoke(activeDots.toSet())
    }

    /** Current set of active (raised) standard dot numbers, 1-6. */
    fun getPattern(): Set<Int> = activeDots.toSet()

    /** Programmatically sets the active pattern (e.g. to preload or preview a sign). */
    fun setPattern(dots: Set<Int>) {
        activeDots.clear()
        activeDots.addAll(dots)
        render()
    }

    /** Looks [text] up in [language]'s braille pack and displays its pattern. */
    fun setValue(text: String, language: BrailleLanguage = BrailleLanguage.DEFAULT) {
        setPattern(BrailleCatalog.packFor(language).find(text)?.dots ?: emptySet())
    }

    /** Clears all dots back to the empty cell. */
    fun reset() = setPattern(emptySet())

    /** Re-reads the active theme and re-tints the dots. Call after a theme change. */
    fun refreshTheme() = render()

    private fun render() {
        val palette = ThemeManager.paletteFor(BohioPrefsManager.getInstance(context).getTheme(), context)
        val onColor = ColorStateList.valueOf(palette.foreground)
        val offColor = ColorStateList.valueOf(palette.bg_3)

        standardDotToViewId.forEach { (dotNumber, viewId) ->
            val frame = findViewById<FrameLayout>(viewId)
            val image = frame?.getChildAt(0) as? ImageView ?: return@forEach
            image.imageTintList = if (dotNumber in activeDots) onColor else offColor
        }
    }
}
