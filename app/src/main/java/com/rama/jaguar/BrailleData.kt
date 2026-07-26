package com.rama.jaguar

/**
 * A single braille sign: a letter (grade 1) or a contraction / word-sign (grade 2+).
 *
 * [dots] uses standard braille dot numbering:
 *   1 4
 *   2 5
 *   3 6
 * (1,2,3 down the left column top-to-bottom; 4,5,6 down the right column top-to-bottom.)
 */
data class BrailleSign(
    val id: String,
    val display: String,
    val dots: Set<Int>,
    val grade: Int
)

object BrailleData {

    /**
     * Grade 1 (uncontracted): the plain English alphabet, one cell per letter.
     * a-j use dots from {1,2,4,5}; k-t repeat that pattern with dot 3 added;
     * u-z (except w) repeat it again with dots 3+6 added. w is the historical
     * exception (French didn't use w when Louis Braille devised the system):
     * dots 2,4,5,6.
     */
    val GRADE_1: List<BrailleSign> = listOf(
        sign("a", 1),
        sign("b", 1, 2),
        sign("c", 1, 4),
        sign("d", 1, 4, 5),
        sign("e", 1, 5),
        sign("f", 1, 2, 4),
        sign("g", 1, 2, 4, 5),
        sign("h", 1, 2, 5),
        sign("i", 2, 4),
        sign("j", 2, 4, 5),
        sign("k", 1, 3),
        sign("l", 1, 2, 3),
        sign("m", 1, 3, 4),
        sign("n", 1, 3, 4, 5),
        sign("o", 1, 3, 5),
        sign("p", 1, 2, 3, 4),
        sign("q", 1, 2, 3, 4, 5),
        sign("r", 1, 2, 3, 5),
        sign("s", 2, 3, 4),
        sign("t", 2, 3, 4, 5),
        sign("u", 1, 3, 6),
        sign("v", 1, 2, 3, 6),
        sign("w", 2, 4, 5, 6),
        sign("x", 1, 3, 4, 6),
        sign("y", 1, 3, 4, 5, 6),
        sign("z", 1, 3, 5, 6),
    )

    /**
     * Grade 2 (contracted) additions, taught in the standard order: alphabetic
     * word-signs first (a single letter's dot pattern stands for a whole common
     * word), then the five "strong" wordsigns, then common strong groupsigns.
     * This is a curated subset of the ~180 UEB contractions, not the full rule set.
     */
    val GRADE_2: List<BrailleSign> = listOf(
        // Alphabetic word-signs
        sign("but", 1, 2),
        sign("can", 1, 4),
        sign("do", 1, 4, 5),
        sign("every", 1, 5),
        sign("from", 1, 2, 4),
        sign("go", 1, 2, 4, 5),
        sign("have", 1, 2, 5),
        sign("just", 2, 4, 5),
        sign("knowledge", 1, 3),
        sign("like", 1, 2, 3),
        sign("more", 1, 3, 4),
        sign("not", 1, 3, 4, 5),
        sign("people", 1, 2, 3, 4),
        sign("quite", 1, 2, 3, 4, 5),
        sign("rather", 1, 2, 3, 5),
        sign("so", 2, 3, 4),
        sign("that", 2, 3, 4, 5),
        sign("us", 1, 3, 6),
        sign("very", 1, 2, 3, 6),
        sign("will", 2, 4, 5, 6),
        sign("it", 1, 3, 4, 6),
        sign("you", 1, 3, 4, 5, 6),
        sign("as", 1, 3, 5, 6),

        // The five "strong" wordsigns
        sign("and", 1, 2, 3, 4, 6),
        sign("for", 1, 2, 3, 4, 5, 6),
        sign("of", 1, 2, 3, 5, 6),
        sign("the", 2, 3, 4, 6),
        sign("with", 2, 3, 4, 5, 6),

        // Common strong groupsigns
        sign("ch", 1, 6),
        sign("gh", 1, 2, 6),
        sign("sh", 1, 4, 6),
        sign("th", 1, 4, 5, 6),
        sign("wh", 1, 5, 6),
        sign("ed", 1, 2, 4, 6),
        sign("er", 1, 2, 4, 5, 6),
        sign("ou", 1, 2, 5, 6),
        sign("ow", 2, 4, 6),
        sign("st", 3, 4),
        sign("ar", 3, 4, 5),
        sign("ing", 3, 4, 6),
        sign("en", 2, 6),
        sign("in", 3, 5),
    )

    /**
     * Grade 3 is NOT a standardized system anywhere (unlike grade 1/2, which are
     * governed by Unified English Braille). It historically refers to informal,
     * personal shorthand individual transcribers used, and was never adopted for
     * teaching or publishing. There is no single authoritative "grade 3" table to
     * draw from, so this is intentionally left empty for now rather than presenting
     * invented content as a real standard. Level 3 currently practices Grade 1+2.
     */
    val GRADE_3: List<BrailleSign> = emptyList()

    private val byId: Map<String, BrailleSign> by lazy {
        (GRADE_1 + GRADE_2 + GRADE_3).associateBy { it.id }
    }

    // Some grade-2 wordsigns deliberately reuse a grade-1 letter's pattern (that's how
    // alphabetic wordsigns work in real braille - context decides the meaning). For showing
    // "what letter does this pattern read as" we prefer the plain letter when ambiguous.
    private val byDots: Map<Set<Int>, BrailleSign> by lazy {
        val map = LinkedHashMap<Set<Int>, BrailleSign>()
        (GRADE_1 + GRADE_2 + GRADE_3).forEach { map.putIfAbsent(it.dots, it) }
        map
    }

    fun find(id: String): BrailleSign? = byId[id.lowercase()]

    /** Resolves a tapped-out dot pattern back to the sign it represents, if any. */
    fun findByDots(dots: Set<Int>): BrailleSign? = byDots[dots]

    /** Cumulative pool for a given grade: grade 2 practice includes the grade 1 alphabet too. */
    fun signsForGrade(grade: Int): List<BrailleSign> = when (grade) {
        1 -> GRADE_1
        2 -> GRADE_1 + GRADE_2
        3 -> GRADE_1 + GRADE_2 + GRADE_3
        else -> GRADE_1
    }

    private fun sign(id: String, vararg dots: Int) =
        BrailleSign(id = id, display = id, dots = dots.toSet(), grade = 1)
}
