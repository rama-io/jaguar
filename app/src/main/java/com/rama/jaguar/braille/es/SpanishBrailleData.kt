package com.rama.jaguar.braille.es

import com.rama.jaguar.braille.BrailleSign

object SpanishBrailleData {

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

        // Number sign: prefixes a-j's patterns to read them as 1-9, 0 instead of
        // letters, same convention as English/UEB. The digit cells below reuse a-j's
        // patterns on purpose (see byDots in BaseBraillePack for how that ambiguity
        // is resolved for display).
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

        // Common punctuation (same marks/dots as English/UEB; Spanish's own inverted
        // ¿ ¡ are left out of this simplified set).
        sign(",", 2),
        sign(";", 2, 3),
        sign(":", 2, 5),
        sign(".", 2, 5, 6),
        sign("?", 2, 3, 6),
        sign("!", 2, 3, 5),
        sign("'", 3),
        sign("-", 3, 6),
    )

    fun find(id: String): BrailleSign? = (GRADE_1).find { it.id == id.lowercase() }

    private fun sign(id: String, vararg dots: Int) =
        BrailleSign(id = id, display = id, dots = dots.toSet())
}
