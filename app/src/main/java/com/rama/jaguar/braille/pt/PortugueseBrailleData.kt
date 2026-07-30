package com.rama.jaguar.braille.pt

import com.rama.jaguar.braille.BrailleSign

object PortugueseBrailleData {

    val GRADE_1: List<BrailleSign> = listOf(
        sign("a", 1),
        sign("b", 1, 2),
        sign("c", 1, 4),
        sign("ç", 1, 2, 3, 4, 6),
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

        // Accented vowels. Portuguese Braille descends from French Braille, with
        // grave-accent French letters remapped to acute-accent Portuguese ones (and a
        // few further shuffles for circumflex/tilde) - see "Portuguese Braille" and
        // "French Braille" on Wikipedia for the derivation. Note ü (trema) was dropped
        // from modern Portuguese orthography entirely, so it's intentionally omitted
        // here (unlike Spanish, which still uses it in "güe"/"güi").
        sign("á", 1, 2, 4, 6),
        sign("à", 1, 2, 3, 5, 6),
        sign("â", 1, 6),
        sign("ã", 3, 4, 5),
        sign("é", 1, 2, 3, 4, 5, 6),
        sign("ê", 1, 2, 6),
        sign("í", 1, 4, 6),
        sign("ó", 3, 4, 6),
        sign("ô", 1, 4, 5, 6),
        sign("õ", 2, 4, 6),
        sign("ú", 1, 5, 6),

        // Number sign: prefixes a-j's patterns to read them as 1-9, 0 instead of
        // letters, same international convention as English/Spanish/UEB. The digit
        // cells below reuse a-j's patterns on purpose (see byDots in BaseBraillePack
        // for how that ambiguity is resolved for display).
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

        // Common punctuation (Portuguese Braille punctuation is nearly identical to
        // Spanish Braille's).
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
