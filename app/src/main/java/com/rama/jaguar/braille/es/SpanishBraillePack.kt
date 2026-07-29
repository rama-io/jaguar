package com.rama.jaguar.braille.es

import com.rama.jaguar.braille.BaseBraillePack
import com.rama.jaguar.braille.BrailleLanguage
import com.rama.jaguar.braille.BrailleSign
import com.rama.jaguar.braille.BrailleWord

object SpanishBraillePack : BaseBraillePack(language = BrailleLanguage.SPANISH, maxGrade = 2) {

    // Spanish grade 1 already includes every sign the language uses (a-z, ñ, and the
    // accented vowels): there's no separate sign set for "grade 2" the way English adds
    // contractions, so grade 2 just introduces a harder word list on the same signs.
    override fun signsIntroducedAtGrade(grade: Int): List<BrailleSign> = when (grade) {
        1 -> SpanishBrailleData.GRADE_1
        else -> emptyList()
    }

    override fun wordsIntroducedAtGrade(grade: Int): List<BrailleWord> = when (grade) {
        1 -> SpanishWordBank.WORDS_GRADE_1
        else -> emptyList()
    }
}
