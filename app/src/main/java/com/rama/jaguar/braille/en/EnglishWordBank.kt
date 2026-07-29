package com.rama.jaguar.braille.en

import com.rama.jaguar.braille.BrailleSign
import com.rama.jaguar.braille.BrailleWord

object EnglishWordBank {

    private fun cell(id: String): BrailleSign =
        EnglishBrailleData.find(id) ?: error("Missing English braille sign for id \"$id\"")

    private fun word(text: String, vararg ids: String) = BrailleWord(text, ids.map(::cell))

    /**
     * One classic ABC-primer word per letter, a-z, so a full grade 1 stage exercises
     * every letter of the alphabet at least once.
     */
    val WORDS_GRADE_1: List<BrailleWord> = listOf(
        word("ant", "a", "n", "t"),
        word("bat", "b", "a", "t"),
        word("cat", "c", "a", "t"),
        word("dog", "d", "o", "g"),
        word("egg", "e", "g", "g"),
        word("fox", "f", "o", "x"),
        word("gum", "g", "u", "m"),
        word("hat", "h", "a", "t"),
        word("ink", "i", "n", "k"),
        word("jam", "j", "a", "m"),
        word("kite", "k", "i", "t", "e"),
        word("leg", "l", "e", "g"),
        word("map", "m", "a", "p"),
        word("net", "n", "e", "t"),
        word("owl", "o", "w", "l"),
        word("pig", "p", "i", "g"),
        word("quiz", "q", "u", "i", "z"),
        word("run", "r", "u", "n"),
        word("sun", "s", "u", "n"),
        word("top", "t", "o", "p"),
        word("up", "u", "p"),
        word("van", "v", "a", "n"),
        word("web", "w", "e", "b"),
        word("box", "b", "o", "x"),
        word("yes", "y", "e", "s"),
        word("zoo", "z", "o", "o"),
    )

    /**
     * Grade 2 practice: whole-word contractions and letter-by-letter words with an
     * embedded groupsign, taught in a common beginner order.
     */
    val WORDS_GRADE_2: List<BrailleWord> = listOf(
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
}
