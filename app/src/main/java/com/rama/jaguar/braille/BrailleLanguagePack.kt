package com.rama.jaguar.braille

interface BrailleLanguagePack {
    val language: BrailleLanguage

    val maxGrade: Int

    fun signsForGrade(grade: Int): List<BrailleSign>

    fun wordsForGrade(grade: Int): List<BrailleWord>

    fun find(id: String): BrailleSign?

    fun findByDots(dots: Set<Int>): BrailleSign?
}
