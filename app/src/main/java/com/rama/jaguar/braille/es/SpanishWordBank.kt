package com.rama.jaguar.braille.es

import com.rama.jaguar.braille.BrailleSign
import com.rama.jaguar.braille.BrailleWord

object SpanishWordBank {

    private fun cell(id: String): BrailleSign =
        SpanishBrailleData.find(id) ?: error("Missing Spanish braille sign for id \"$id\"")

    private fun word(text: String, vararg ids: String) = BrailleWord(text, ids.map(::cell))

    /**
     * One classic "abecedario" word per letter, matching the order Spanish-speaking
     * children learn to read: a-z, then ñ, then the six accented vowels. Every one of
     * Spanish grade 1's 33 signs gets its own word, so a full stage exercises the whole
     * alphabet (a-z, ñ) plus every accented vowel at least once.
     */
    val WORDS_GRADE_1: List<BrailleWord> = listOf(
        word("avión", "a", "v", "i", "ó", "n"),
        word("barco", "b", "a", "r", "c", "o"),
        word("casa", "c", "a", "s", "a"),
        word("dado", "d", "a", "d", "o"),
        word("elefante", "e", "l", "e", "f", "a", "n", "t", "e"),
        word("foca", "f", "o", "c", "a"),
        word("gato", "g", "a", "t", "o"),
        word("hoja", "h", "o", "j", "a"),
        word("isla", "i", "s", "l", "a"),
        word("jirafa", "j", "i", "r", "a", "f", "a"),
        word("kilo", "k", "i", "l", "o"),
        word("luna", "l", "u", "n", "a"),
        word("mano", "m", "a", "n", "o"),
        word("nube", "n", "u", "b", "e"),
        word("ñu", "ñ", "u"),
        word("oso", "o", "s", "o"),
        word("pato", "p", "a", "t", "o"),
        word("queso", "q", "u", "e", "s", "o"),
        word("rosa", "r", "o", "s", "a"),
        word("sol", "s", "o", "l"),
        word("taza", "t", "a", "z", "a"),
        word("uva", "u", "v", "a"),
        word("vaca", "v", "a", "c", "a"),
        word("web", "w", "e", "b"),
        word("xilófono", "x", "i", "l", "ó", "f", "o", "n", "o"),
        word("yoyo", "y", "o", "y", "o"),
        word("zapato", "z", "a", "p", "a", "t", "o"),
        word("árbol", "á", "r", "b", "o", "l"),
        word("café", "c", "a", "f", "é"),
        word("maíz", "m", "a", "í", "z"),
        word("balón", "b", "a", "l", "ó", "n"),
        word("búho", "b", "ú", "h", "o"),
        word("pingüino", "p", "i", "n", "g", "ü", "i", "n", "o"),
    )
}
