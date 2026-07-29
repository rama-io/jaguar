package com.rama.jaguar.braille.es

import com.rama.jaguar.braille.BrailleSign

object SpanishBrailleData {

    /**
     * Grade 1 (uncontracted): the Spanish alphabet. a-z use the same dot patterns as
     * English/UEB, since uncontracted Latin-script braille codes are essentially
     * identical for the plain a-z letters they share. Spanish adds seven signs no
     * English word needs: ñ, and the six accented vowels (á, é, í, ó, ú, ü).
     *
     * Unlike English, Spanish braille has no separate literary "grade 2": every
     * letter is always spelled out individually (see SpanishBraillePack for how
     * "grade 2" is used here instead).
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
        sign("ñ", 1, 2, 4, 5, 6),
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
        sign("á", 1, 2, 3, 5, 6),
        sign("é", 2, 3, 4, 6),
        sign("í", 3, 4),
        sign("ó", 3, 4, 6),
        sign("ú", 2, 3, 4, 5, 6),
        sign("ü", 1, 2, 5, 6),
    )

    fun find(id: String): BrailleSign? = GRADE_1.find { it.id == id.lowercase() }

    private fun sign(id: String, vararg dots: Int) =
        BrailleSign(id = id, display = id, dots = dots.toSet())
}
