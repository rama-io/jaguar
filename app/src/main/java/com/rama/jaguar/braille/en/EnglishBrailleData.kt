package com.rama.jaguar.braille.en

import com.rama.jaguar.braille.BrailleSign

object EnglishBrailleData {

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

        // Number sign: prefixes a-j's patterns to read them as 1-9, 0 instead of
        // letters. Modeled as its own cell/id here since each stage cell checks one
        // dot pattern at a time; the digit cells below reuse a-j's patterns, which is
        // intentional (that's how real braille numbers work) and mirrors the
        // ambiguity already documented on byDots above.
        sign("#", 3, 4, 5, 6),
        sign("1", 1),
        sign("2", 1, 2),
        sign("3", 1, 4),
        sign("4", 1, 4, 5),
        sign("5", 1, 5),
        sign("6", 1, 2, 4),
        sign("7", 1, 2, 4, 5),
        sign("8", 1, 2, 5),
        sign("9", 2, 4),
        sign("0", 2, 4, 5),

        // Common punctuation.
        sign(",", 2),
        sign(";", 2, 3),
        sign(":", 2, 5),
        sign(".", 2, 5, 6),
        sign("?", 2, 3, 6),
        sign("!", 2, 3, 5),
        sign("'", 3),
        sign("-", 3, 6),
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

    fun find(id: String): BrailleSign? = (GRADE_1 + GRADE_2).find { it.id == id.lowercase() }

    private fun sign(id: String, vararg dots: Int) =
        BrailleSign(id = id, display = id, dots = dots.toSet())
}
