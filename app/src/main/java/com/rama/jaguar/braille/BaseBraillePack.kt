package com.rama.jaguar.braille

abstract class BaseBraillePack(
    final override val language: BrailleLanguage,
    final override val maxGrade: Int
) : BrailleLanguagePack {

    /** Signs newly introduced at exactly this grade tier (not cumulative). */
    protected abstract fun signsIntroducedAtGrade(grade: Int): List<BrailleSign>

    /** Words newly introduced at exactly this grade tier (not cumulative). */
    protected abstract fun wordsIntroducedAtGrade(grade: Int): List<BrailleWord>

    private val allSigns: List<BrailleSign> by lazy {
        (1..maxGrade).flatMap(::signsIntroducedAtGrade)
    }

    private val byId: Map<String, BrailleSign> by lazy {
        allSigns.associateBy { it.id.lowercase() }
    }

    // Some grade-2+ word-signs deliberately reuse a lower-grade pattern (that's how
    // alphabetic wordsigns work in real braille - context decides the meaning). For
    // showing "what does this pattern read as" we prefer the earliest-introduced sign
    // when a pattern is ambiguous.
    private val byDots: Map<Set<Int>, BrailleSign> by lazy {
        val map = LinkedHashMap<Set<Int>, BrailleSign>()
        allSigns.forEach { sign -> if (!map.containsKey(sign.dots)) map[sign.dots] = sign }
        map
    }

    final override fun signsForGrade(grade: Int): List<BrailleSign> =
        (1..grade.coerceIn(1, maxGrade)).flatMap(::signsIntroducedAtGrade)

    final override fun wordsForGrade(grade: Int): List<BrailleWord> =
        (1..grade.coerceIn(1, maxGrade)).flatMap(::wordsIntroducedAtGrade)

    final override fun find(id: String): BrailleSign? = byId[id.lowercase()]

    final override fun findByDots(dots: Set<Int>): BrailleSign? = byDots[dots]
}
