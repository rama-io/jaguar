package com.rama.jaguar.braille

/**
 * Everything the stage screen needs to run a practice session in one language: its sign
 * set (by grade) and the words built from it.
 */
interface BrailleLanguagePack {
    val language: BrailleLanguage

    /** Highest grade this language currently offers practice content for. */
    val maxGrade: Int

    /** Cumulative sign pool for a given grade (grade 2 practice includes grade 1, etc). */
    fun signsForGrade(grade: Int): List<BrailleSign>

    /** Cumulative word pool to practice for a given grade. */
    fun wordsForGrade(grade: Int): List<BrailleWord>

    fun find(id: String): BrailleSign?

    /** Resolves a tapped-out dot pattern back to the sign it represents, if any. */
    fun findByDots(dots: Set<Int>): BrailleSign?
}
