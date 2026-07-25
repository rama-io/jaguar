package com.rama.bohio.widgets

import android.content.Context
import android.util.AttributeSet
import android.view.HapticFeedbackConstants
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.rama.bohio.R
import com.rama.bohio.managers.PrefsManager

class WdCollapsibleSection @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    private val header: LinearLayout
    private val indicator: TextView
    private val labelView: TextView
    private val content: LinearLayout

    private var key: String? = null
    private var defaultExpanded = true
    private val prefs = PrefsManager.getInstance(context)

    init {
        orientation = VERTICAL
        LayoutInflater.from(context).inflate(R.layout.wd_collapsible_section, this, true)

        header = findViewById(R.id.section_header)
        indicator = findViewById(R.id.section_indicator)
        labelView = findViewById(R.id.section_label)
        content = findViewById(R.id.section_content)

        attrs?.let {
            val ta = context.obtainStyledAttributes(it, R.styleable.WdCollapsibleSection)
            labelView.text = ta.getString(R.styleable.WdCollapsibleSection_header) ?: ""
            key = ta.getString(R.styleable.WdCollapsibleSection_key)
            defaultExpanded = ta.getBoolean(R.styleable.WdCollapsibleSection_defaultExpanded, true)
            ta.recycle()
        }

        applyState(loadState())

        header.setOnClickListener {
            it.performHapticFeedback(HapticFeedbackConstants.KEYBOARD_TAP)
            val next = !isExpanded()
            applyState(next)
            saveState(next)
        }

        header.isFocusable = true
        header.isFocusableInTouchMode = false
        header.setOnKeyListener { v, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN &&
                (keyCode == KeyEvent.KEYCODE_ENTER || keyCode == KeyEvent.KEYCODE_DPAD_CENTER)
            ) {
                v.performHapticFeedback(HapticFeedbackConstants.KEYBOARD_TAP)
                val next = !isExpanded()
                applyState(next)
                saveState(next)
                true
            } else false
        }
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        val children = (0 until childCount)
            .map { getChildAt(it) }
            .filter { it.id != R.id.section_root }
        children.forEach { removeView(it); content.addView(it) }
    }

    fun addItem(view: View) {
        content.addView(view)
    }

    fun clearItems() {
        content.removeAllViews()
    }

    private fun isExpanded() = content.visibility == View.VISIBLE

    private fun applyState(expanded: Boolean) {
        content.visibility = if (expanded) View.VISIBLE else View.GONE
        indicator.text = context.getString(
            if (expanded) R.string.settings_section_collapse_indicator
            else R.string.settings_section_expand_indicator
        )
    }

    private fun saveState(expanded: Boolean) = key?.let { prefs.setBoolean(it, expanded) }

    private fun loadState() = key?.let { prefs.getBoolean(it, defaultExpanded) } ?: defaultExpanded
}
