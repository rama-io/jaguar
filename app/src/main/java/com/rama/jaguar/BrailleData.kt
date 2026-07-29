package com.rama.jaguar

data class BrailleSign(
    val id: String,
    val display: String,
    val dots: Set<Int>,
    val grade: Int
)

object BrailleData {
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

    private val byId: Map<String, BrailleSign> by lazy {
        (GRADE_1 + GRADE_2).associateBy { it.id }
    }

    // Some grade-2 wordsigns deliberately reuse a grade-1 letter's pattern (that's how
    // alphabetic wordsigns work in real braille - context decides the meaning). For showing
    // "what letter does this pattern read as" we prefer the plain letter when ambiguous.
    private val byDots: Map<Set<Int>, BrailleSign> by lazy {
        val map = LinkedHashMap<Set<Int>, BrailleSign>()
        (GRADE_1 + GRADE_2).forEach { sign ->
            if (!map.containsKey(sign.dots)) map[sign.dots] = sign
        }
        map
    }

    fun find(id: String): BrailleSign? = byId[id.lowercase()]

    /** Resolves a tapped-out dot pattern back to the sign it represents, if any. */
    fun findByDots(dots: Set<Int>): BrailleSign? = byDots[dots]

    /** Cumulative pool for a given grade: grade 2 practice includes the grade 1 alphabet too. */
    fun signsForGrade(grade: Int): List<BrailleSign> = when (grade) {
        1 -> GRADE_1
        2 -> GRADE_1 + GRADE_2
        else -> GRADE_1
    }

    private fun sign(id: String, vararg dots: Int) =
        BrailleSign(id = id, display = id, dots = dots.toSet(), grade = 1)
}
