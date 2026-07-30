package com.rama.jaguar.braille.en

import com.rama.jaguar.braille.BrailleSign
import com.rama.jaguar.braille.BrailleWord

object EnglishWordBank {

    private fun cell(id: String): BrailleSign =
        EnglishBrailleData.find(id) ?: error("Missing English braille sign for id \"$id\"")

    private fun word(text: String, vararg ids: String) = BrailleWord(text, ids.map(::cell))

    /**
     * Each inner list holds a few interchangeable
     * words for the same letter/slot, so a
     * stage doesn't always drill the exact same
     * word - one is picked at random each
     * time
     * wordsForGrade() is called. Every one of grade 1's 26 letters gets its own
     * group, so a full stage still exercises the whole alphabet at least once.
     */
    val WORDS_GRADE_1: List<List<BrailleWord>> = listOf(
        listOf(
            word("ant", "a", "n", "t"),
            word("axe", "a", "x", "e")
        ),
        listOf(
            word("bat", "b", "a", "t"),
            word("bus", "b", "u", "s")
        ),
        listOf(
            word("cat", "c", "a", "t"),
            word("cup", "c", "u", "p")
        ),
        listOf(
            word("dog", "d", "o", "g"),
            word("den", "d", "e", "n")
        ),
        listOf(
            word("egg", "e", "g", "g"),
            word("elf", "e", "l", "f")
        ),
        listOf(
            word("fox", "f", "o", "x"),
            word("fan", "f", "a", "n")
        ),
        listOf(
            word("gum", "g", "u", "m"),
            word("gap", "g", "a", "p")
        ),
        listOf(
            word("hat", "h", "a", "t"),
            word("hen", "h", "e", "n")
        ),
        listOf(
            word("ink", "i", "n", "k"),
            word("itch", "i", "t", "c", "h")
        ),
        listOf(
            word("jam", "j", "a", "m"),
            word("jog", "j", "o", "g")
        ),
        listOf(
            word("kite", "k", "i", "t", "e"),
            word("keg", "k", "e", "g")
        ),
        listOf(
            word("leg", "l", "e", "g"),
            word("log", "l", "o", "g")
        ),
        listOf(
            word("map", "m", "a", "p"),
            word("mud", "m", "u", "d")
        ),
        listOf(
            word("net", "n", "e", "t"),
            word("nap", "n", "a", "p")
        ),
        listOf(
            word("owl", "o", "w", "l"),
            word("oak", "o", "a", "k")
        ),
        listOf(
            word("pig", "p", "i", "g"),
            word("pot", "p", "o", "t")
        ),
        listOf(
            word("quiz", "q", "u", "i", "z"),
            word("quit", "q", "u", "i", "t")
        ),
        listOf(
            word("run", "r", "u", "n"),
            word("rat", "r", "a", "t")
        ),
        listOf(
            word("sun", "s", "u", "n"),
            word("sit", "s", "i", "t")
        ),
        listOf(
            word("top", "t", "o", "p"),
            word("tan", "t", "a", "n")
        ),
        listOf(
            word("up", "u", "p"),
            word("us", "u", "s")
        ),
        listOf(
            word("van", "v", "a", "n"),
            word("vet", "v", "e", "t")
        ),
        listOf(
            word("web", "w", "e", "b"),
            word("wig", "w", "i", "g")
        ),
        listOf(
            word("box", "b", "o", "x"),
            word("fix", "f", "i", "x")
        ),
        listOf(
            word("yes", "y", "e", "s"),
            word("yak", "y", "a", "k")
        ),
        listOf(
            word("zoo", "z", "o", "o"),
            word("zip", "z", "i", "p")
        ),

        // Numbers: the number sign plus one or more reused a-j patterns.
        listOf(
            word("5", "#", "5"),
            word("2", "#", "2")
        ),
        listOf(
            word("10", "#", "1", "0"),
            word("21", "#", "2", "1")
        ),

        // Punctuation, embedded the same way grade 2 embeds groupsigns.
        listOf(
            word("hi!", "h", "i", "!"),
            word("go!", "g", "o", "!")
        ),
        listOf(
            word("hi?", "h", "i", "?"),
            word("no?", "n", "o", "?")
        ),
        listOf(
            word("mr.", "m", "r", "."),
            word("no.", "n", "o", ".")
        ),
        listOf(
            word("up, go", "u", "p", ",", "g", "o"),
            word("no, sir", "n", "o", ",", "s", "i", "r")
        ),
    )

    /**
     * Grade 2 practice: whole-
     * word contractions and letter-by-letter
     * words with an
     * embedded groupsign, taught in a common beginner order. Each slot keeps a couple
     * of alternatives so the same contraction/groupsign isn't drilled with the exact
     * same carrier
     * word every time.
     */
    val WORDS_GRADE_2: List<List<BrailleWord>> = listOf(
        listOf(
            word("and", "and")
        ),
        listOf(
            word("the", "the")
        ),
        listOf(
            word("for", "for")
        ),
        listOf(
            word("with", "with")
        ),
        listOf(
            word("but", "but")
        ),
        listOf(
            word("you", "you")
        ),
        listOf(
            word("will", "will")
        ),
        listOf(
            word("have", "have")
        ),
        listOf(
            word("that", "that")
        ),
        listOf(
            word("not", "not")
        ),
        listOf(
            word("go", "go")
        ),
        listOf(
            word("so", "so")
        ),
        listOf(
            word("as", "as")
        ),
        listOf(
            word("in", "in")
        ),

        // Groupsigns embedded in a normal letter-by-letter spelling.
        listOf(
            word("night", "n", "i", "gh", "t"),
            word("light", "l", "i", "gh", "t")
        ),
        listOf(
            word("shall", "sh", "a", "l", "l"),
            word("shop", "sh", "o", "p")
        ),
        listOf(
            word("think", "th", "i", "n", "k"),
            word("thin", "th", "i", "n")
        ),
        listOf(
            word("chat", "ch", "a", "t"),
            word("chip", "ch", "i", "p")
        ),
        listOf(
            word("when", "wh", "e", "n"),
            word("whip", "wh", "i", "p")
        ),
        listOf(
            word("wish", "w", "i", "sh"),
            word("dish", "d", "i", "sh")
        ),
        listOf(
            word("cow", "c", "ow"),
            word("how", "h", "ow")
        ),
        listOf(
            word("stop", "st", "o", "p"),
            word("step", "st", "e", "p")
        ),
        listOf(
            word("start", "st", "ar", "t"),
            word("star", "st", "ar")
        ),
        listOf(
            word("arm", "ar", "m"),
            word("art", "ar", "t")
        ),
        listOf(
            word("ten", "t", "en"),
            word("pen", "p", "en")
        ),
        listOf(
            word("ring", "r", "ing"),
            word("king", "k", "ing")
        ),
        listOf(
            word("sing", "s", "ing"),
            word("wing", "w", "ing")
        ),
    )
}
