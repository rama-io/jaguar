package com.rama.jaguar.braille.en

import com.rama.jaguar.braille.BaseBraillePack
import com.rama.jaguar.braille.BrailleLanguage
import com.rama.jaguar.braille.BrailleSign
import com.rama.jaguar.braille.BrailleWord

object EnglishBraillePack : BaseBraillePack(language = BrailleLanguage.ENGLISH, maxGrade = 2) {

    override fun signsIntroducedAtGrade(grade: Int): List<BrailleSign> = when (grade) {
        1 -> EnglishBrailleData.GRADE_1
        2 -> EnglishBrailleData.GRADE_2
        else -> emptyList()
    }

    override fun wordPoolsIntroducedAtGrade(grade: Int): List<List<BrailleWord>> = when (grade) {
        1 -> EnglishWordBank.WORDS_GRADE_1
        2 -> EnglishWordBank.WORDS_GRADE_2
        else -> emptyList()
    }
}
