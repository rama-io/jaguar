package com.rama.jaguar.braille.es

import com.rama.jaguar.braille.BaseBraillePack
import com.rama.jaguar.braille.BrailleLanguage
import com.rama.jaguar.braille.BrailleSign
import com.rama.jaguar.braille.BrailleWord

object SpanishBraillePack : BaseBraillePack(language = BrailleLanguage.SPANISH, maxGrade = 1) {

    override fun signsIntroducedAtGrade(grade: Int): List<BrailleSign> = when (grade) {
        1 -> SpanishBrailleData.GRADE_1
        else -> emptyList()
    }

    override fun wordPoolsIntroducedAtGrade(grade: Int): List<List<BrailleWord>> = when (grade) {
        1 -> SpanishWordBank.WORDS_GRADE_1
        else -> emptyList()
    }
}
