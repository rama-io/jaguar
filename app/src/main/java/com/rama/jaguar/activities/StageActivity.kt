package com.rama.jaguar.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.rama.bohio.objects.PrefKeys
import com.rama.bohio.util.UiActions
import com.rama.jaguar.BrailleBlock
import com.rama.jaguar.BrailleData
import com.rama.jaguar.BrailleWord
import com.rama.jaguar.BrailleWordBank
import com.rama.jaguar.CsActivity
import com.rama.jaguar.R
import com.rama.jaguar.SmallBrailleBlock

class StageActivity : CsActivity() {

    companion object {
        const val EXTRA_GRADE = "extra_grade"
        private const val ROUND_COUNT = 15
        private const val ADVANCE_DELAY_MS = 500L
    }

    private lateinit var words: List<BrailleWord>
    private var wordIndex = 0
    private var cellIndex = 0
    private var correctCells = BooleanArray(0)
    private var wordsCorrect = 0
    private var wordsCompleted = 0
    private var awaitingAdvance = false

    private var grade = 1
    private var startTimeMillis = 0L
    private var elapsedMillis = 0L
    private var timerRunning = false

    private val timerHandler = Handler(Looper.getMainLooper())
    private val advanceHandler = Handler(Looper.getMainLooper())

    private lateinit var brailleBlock: BrailleBlock
    private lateinit var promptText: TextView
    private lateinit var timeText: TextView
    private lateinit var progressText: TextView
    private lateinit var typedRow: LinearLayout
    private var typedSlots: List<SmallBrailleBlock> = emptyList()
    private lateinit var prevBtn: View
    private lateinit var nextBtn: View

    private val tickRunnable = object : Runnable {
        override fun run() {
            if (!timerRunning) return
            elapsedMillis = System.currentTimeMillis() - startTimeMillis
            timeText.text = formatTime(elapsedMillis)
            timerHandler.postDelayed(this, 1000)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.view_stage)

        val root = findViewById<View>(R.id.root)
        applyEdgeToEdgePadding(root)
        applyCurrentTheme(root)

        grade = intent.getIntExtra(EXTRA_GRADE, 1).coerceIn(1, 2)
        brailleBlock = findViewById(R.id.braille_display)
        promptText = findViewById(R.id.prompt_text)
        timeText = findViewById(R.id.time_text)
        progressText = findViewById(R.id.progress_text)
        typedRow = findViewById(R.id.typed_row)
        prevBtn = findViewById(R.id.prev_btn)
        nextBtn = findViewById(R.id.next_btn)

        words = buildWordList(grade)

        findViewById<View>(R.id.close_btn).setOnClickListener { finishStage() }
        UiActions.setClickWithHaptics(prevBtn) { eraseLastCell() }
        UiActions.setClickWithHaptics(nextBtn) { submitCurrentCell() }

        startTimer()
        loadWord(0)
    }

    override fun onResume() {
        super.onResume()
        if (prefs.getBoolean(PrefKeys.SYSTEM_PREVENT_SLEEP, false)) {
            window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        } else {
            window.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        }
        brailleBlock.refreshTheme()
        typedSlots.forEach { it.refreshTheme() }
    }

    override fun onDestroy() {
        super.onDestroy()
        timerRunning = false
        timerHandler.removeCallbacksAndMessages(null)
        advanceHandler.removeCallbacksAndMessages(null)
    }

    // Setup

    private fun buildWordList(grade: Int): List<BrailleWord> {
        val pool = BrailleWordBank.wordsForGrade(grade)
        val count = ROUND_COUNT.coerceAtMost(pool.size)
        return pool.shuffled().take(count)
    }

    private fun loadWord(index: Int) {
        wordIndex = index
        cellIndex = 0
        awaitingAdvance = false
        val word = words[index]
        correctCells = BooleanArray(word.cells.size)

        promptText.text = word.text
        progressText.text = "${index + 1}/${words.size}"
        brailleBlock.reset()
        
        typedRow.removeAllViews()
        typedSlots = word.cells.map {
            val slot = SmallBrailleBlock(this)
            typedRow.addView(slot)
            slot
        }

        updateNavButtons()
    }

    // Input

    private fun submitCurrentCell() {
        if (awaitingAdvance) return
        val word = words[wordIndex]
        val entered = brailleBlock.getPattern()
        if (entered.isEmpty()) {
            Toast.makeText(this, R.string.toast_tap_some_dots, Toast.LENGTH_SHORT).show()
            return
        }

        val expected = word.cells[cellIndex]
        val correct = entered == expected.dots
        correctCells[cellIndex] = correct

        // Show exactly what the player typed, labelled with whatever letter/sign that
        // pattern actually reads as (so a wrong answer is a lesson, not just a red mark).
        val enteredSign = BrailleData.findByDots(entered)
        val label = enteredSign?.display ?: "?"
        val slot = typedSlots[cellIndex]
        slot.setEntry(entered, label)
        slot.setState(if (correct) SmallBrailleBlock.State.CORRECT else SmallBrailleBlock.State.INCORRECT)

        cellIndex++
        brailleBlock.reset()
        updateNavButtons()

        if (cellIndex == word.cells.size) {
            awaitingAdvance = true
            wordsCompleted++
            if (correctCells.all { it }) wordsCorrect++
            advanceHandler.postDelayed({ advanceWord() }, ADVANCE_DELAY_MS)
        }
    }

    private fun eraseLastCell() {
        if (awaitingAdvance || cellIndex == 0) return
        cellIndex--
        val slot = typedSlots[cellIndex]
        slot.setEntry(emptySet(), "")
        slot.setState(SmallBrailleBlock.State.NEUTRAL)
        brailleBlock.reset()
        updateNavButtons()
    }

    private fun updateNavButtons() {
        prevBtn.isEnabled = cellIndex > 0
    }

    private fun advanceWord() {
        if (wordIndex < words.size - 1) {
            loadWord(wordIndex + 1)
        } else {
            finishStage()
        }
    }

    // Timer

    private fun startTimer() {
        startTimeMillis = System.currentTimeMillis()
        timerRunning = true
        timerHandler.post(tickRunnable)
    }

    private fun formatTime(millis: Long): String {
        val totalSeconds = millis / 1000
        val minutes = totalSeconds / 60
        val seconds = totalSeconds % 60
        return String.format("%d:%02d", minutes, seconds)
    }

    // Finish

    private fun finishStage() {
        timerRunning = false
        val intent = Intent(this, ScoreActivity::class.java).apply {
            putExtra(ScoreActivity.EXTRA_GRADE, grade)
            putExtra(ScoreActivity.EXTRA_SCORE, wordsCorrect)
            putExtra(ScoreActivity.EXTRA_TOTAL, wordsCompleted)
            putExtra(ScoreActivity.EXTRA_TIME_MILLIS, elapsedMillis)
        }
        startActivity(intent)
        finish()
    }
}
