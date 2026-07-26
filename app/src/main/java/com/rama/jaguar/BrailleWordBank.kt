package com.rama.jaguar

/** A target word for a Stage round: its printed text, and the ordered braille cells that spell it. */
data class BrailleWord(val text: String, val cells: List<BrailleSign>)

/**
 * Curated target-word lists. Grade 1 words are spelled letter-by-letter. Grade 2 words either
 * ARE one of the whole-word contractions (typed as a single cell) or embed a groupsign inside an
 * otherwise letter-by-letter spelling (e.g. "night" = n, i, gh, t).
 *
 * Note: these breakdowns are chosen for teaching value, not lifted from a full UEB rule engine —
 * real contracted braille has positional rules (e.g. exactly when "st" or "ing" may be used)
 * that this simplified word list doesn't attempt to fully model.
 */
object BrailleWordBank {

    private fun cell(id: String): BrailleSign =
        BrailleData.find(id) ?: error("Missing braille sign for id \"$id\"")

    private fun word(text: String, vararg ids: String) = BrailleWord(text, ids.map(::cell))

    val WORDS_GRADE_1: List<BrailleWord> = listOf(
        word("cat", "c", "a", "t"),
        word("dog", "d", "o", "g"),
        word("sun", "s", "u", "n"),
        word("pen", "p", "e", "n"),
        word("top", "t", "o", "p"),
        word("red", "r", "e", "d"),
        word("big", "b", "i", "g"),
        word("yes", "y", "e", "s"),
        word("hat", "h", "a", "t"),
        word("map", "m", "a", "p"),
        word("run", "r", "u", "n"),
        word("sit", "s", "i", "t"),
        word("bag", "b", "a", "g"),
        word("cup", "c", "u", "p"),
        word("fox", "f", "o", "x"),
        word("jam", "j", "a", "m"),
        word("leg", "l", "e", "g"),
        word("mud", "m", "u", "d"),
        word("wet", "w", "e", "t"),
        word("zoo", "z", "o", "o"),
    )

    val WORDS_GRADE_2: List<BrailleWord> = listOf(
        // Whole-word contractions: typed as a single cell.
        word("and", "and"),
        word("the", "the"),
        word("for", "for"),
        word("with", "with"),
        word("but", "but"),
        word("you", "you"),
        word("will", "will"),
        word("have", "have"),
        word("that", "that"),
        word("not", "not"),
        word("go", "go"),
        word("so", "so"),
        word("as", "as"),
        word("in", "in"),

        // Groupsigns embedded in a normal letter-by-letter spelling.
        word("night", "n", "i", "gh", "t"),
        word("shall", "sh", "a", "l", "l"),
        word("think", "th", "i", "n", "k"),
        word("chat", "ch", "a", "t"),
        word("when", "wh", "e", "n"),
        word("wish", "w", "i", "sh"),
        word("cow", "c", "ow"),
        word("stop", "st", "o", "p"),
        word("start", "st", "ar", "t"),
        word("arm", "ar", "m"),
        word("ten", "t", "en"),
        word("ring", "r", "ing"),
        word("sing", "s", "ing"),
    )

    // Grade 3 isn't standardized (see BrailleData), so it reuses grade 1+2 words for now
    // rather than inventing content and presenting it as a real standard.
    val WORDS_GRADE_3: List<BrailleWord> = emptyList()

    fun wordsForGrade(grade: Int): List<BrailleWord> = when (grade) {
        1 -> WORDS_GRADE_1
        2 -> WORDS_GRADE_1 + WORDS_GRADE_2
        3 -> WORDS_GRADE_1 + WORDS_GRADE_2 + WORDS_GRADE_3
        else -> WORDS_GRADE_1
    }
}
