package com.rama.jaguar.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.WindowManager
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

/**
 * Practice loop: the target word is shown in print at the top. The player taps dots on the big
 * [BrailleBlock] to build each cell, then hits "next" to submit it (checked against the expected
 * cell and appended to the row of typed cells below) or "prev" to erase the last submitted cell.
 */
class StageActivity : CsActivity() {

    companion object {
        const val EXTRA_GRADE = "extra_grade"
        private const val ROUND_COUNT = 15
        private const val MAX_WORD_LENGTH = 7 // matches the 7 slots in view_stage.xml
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
    private lateinit var typedSlots: List<SmallBrailleBlock>
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

        grade = intent.getIntExtra(EXTRA_GRADE, 1).coerceIn(1, 3)
        if (grade == 3 && BrailleData.GRADE_3.isEmpty()) {
            Toast.makeText(this, getString(R.string.toast_grade3_unavailable), Toast.LENGTH_LONG).show()
        }

        brailleBlock = findViewById(R.id.braille_display)
        promptText = findViewById(R.id.prompt_text)
        timeText = findViewById(R.id.time_text)
        progressText = findViewById(R.id.progress_text)
        typedSlots = listOf(
            findViewById(R.id.choice_1), findViewById(R.id.choice_2), findViewById(R.id.choice_3),
            findViewById(R.id.choice_4), findViewById(R.id.choice_5), findViewById(R.id.choice_6),
            findViewById(R.id.choice_7)
        )
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
    }

    override fun onDestroy() {
        super.onDestroy()
        timerRunning = false
        timerHandler.removeCallbacksAndMessages(null)
        advanceHandler.removeCallbacksAndMessages(null)
    }

    // Setup

    private fun buildWordList(grade: Int): List<BrailleWord> {
        val pool = BrailleWordBank.wordsForGrade(grade).filter { it.cells.size <= MAX_WORD_LENGTH }
        return (1..ROUND_COUNT).map { pool.random() }
    }

    private fun loadWord(index: Int) {
        wordIndex = index
        cellIndex = 0
        awaitingAdvance = false
        correctCells = BooleanArray(words[index].cells.size)

        val word = words[index]
        promptText.text = word.text
        progressText.text = "${index + 1}/${words.size}"
        brailleBlock.reset()

        typedSlots.forEachIndexed { i, slot ->
            if (i < word.cells.size) {
                slot.visibility = View.VISIBLE
                slot.setPattern(emptySet())
                slot.setState(SmallBrailleBlock.State.NEUTRAL)
            } else {
                slot.visibility = View.INVISIBLE
            }
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

        val slot = typedSlots[cellIndex]
        slot.setPattern(expected.dots)
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
        slot.setPattern(emptySet())
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
