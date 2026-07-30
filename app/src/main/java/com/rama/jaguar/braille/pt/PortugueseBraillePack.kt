package com.rama.jaguar.braille.pt

import com.rama.jaguar.braille.BaseBraillePack
import com.rama.jaguar.braille.BrailleLanguage
import com.rama.jaguar.braille.BrailleSign
import com.rama.jaguar.braille.BrailleWord

// maxGrade is 1: Portuguese's abbreviated/contracted system was never adopted as a
// standard for general reading material the way English Grade 2 was - Brazil's own
// braille commission voted to abolish abbreviation use in official transcription
// production starting 1996, leaving only an optional stenography code for personal
// notes (Estenografia Braille para a Língua Portuguesa, 2006). Since there's no
// standardized Grade 2 in the same sense as English, we only offer Grade 1 here,
// matching the approach already taken for Spanish.
object PortugueseBraillePack : BaseBraillePack(language = BrailleLanguage.PORTUGUESE, maxGrade = 1) {

    override fun signsIntroducedAtGrade(grade: Int): List<BrailleSign> = when (grade) {
        1 -> PortugueseBrailleData.GRADE_1
        else -> emptyList()
    }

    override fun wordPoolsIntroducedAtGrade(grade: Int): List<List<BrailleWord>> = when (grade) {
        1 -> PortugueseWordBank.WORDS_GRADE_1
        else -> emptyList()
    }
}
