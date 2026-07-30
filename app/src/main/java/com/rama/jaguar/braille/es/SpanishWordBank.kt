package com.rama.jaguar.braille.es

import com.rama.jaguar.braille.BrailleSign
import com.rama.jaguar.braille.BrailleWord

object SpanishWordBank {

    private fun cell(id: String): BrailleSign =
        SpanishBrailleData.find(id) ?: error("Missing Spanish braille sign for id \"$id\"")

    private fun word(text: String, vararg ids: String) = BrailleWord(text, ids.map(::cell))

    val WORDS_GRADE_1: List<List<BrailleWord>> = listOf(
        listOf(
            word("abanico", "a", "b", "a", "n", "i", "c", "o"),
            word("avión", "a", "v", "i", "ó", "n")
        ),
        listOf(
            word("banana", "b", "a", "n", "a", "n", "a"),
            word("berenjena", "b", "e", "r", "e", "n", "j", "e", "n", "a"),
            word("barco", "b", "a", "r", "c", "o")
        ),
        listOf(
            word("casa", "c", "a", "s", "a"),
            word("cama", "c", "a", "m", "a")
        ),
        listOf(
            word("dado", "d", "a", "d", "o"),
            word("dedo", "d", "e", "d", "o")
        ),
        listOf(
            word("elefante", "e", "l", "e", "f", "a", "n", "t", "e"),
            word("escuela", "e", "s", "c", "u", "e", "l", "a")
        ),
        listOf(
            word("foca", "f", "o", "c", "a"),
            word("flor", "f", "l", "o", "r")
        ),
        listOf(
            word("gato", "g", "a", "t", "o"),
            word("globo", "g", "l", "o", "b", "o")
        ),
        listOf(
            word("hoja", "h", "o", "j", "a"),
            word("huevo", "h", "u", "e", "v", "o")
        ),
        listOf(
            word("isla", "i", "s", "l", "a"),
            word("iglú", "i", "g", "l", "ú")
        ),
        listOf(
            word("jirafa", "j", "i", "r", "a", "f", "a"),
            word("jarra", "j", "a", "r", "r", "a")
        ),
        listOf(
            word("kilo", "k", "i", "l", "o"),
            word("koala", "k", "o", "a", "l", "a")
        ),
        listOf(
            word("luna", "l", "u", "n", "a"),
            word("lápiz", "l", "á", "p", "i", "z")
        ),
        listOf(
            word("mano", "m", "a", "n", "o"),
            word("mesa", "m", "e", "s", "a")
        ),
        listOf(
            word("nube", "n", "u", "b", "e"),
            word("nido", "n", "i", "d", "o")
        ),
        listOf(
            word("ñu", "ñ", "u"),
            word("ñoño", "ñ", "o", "ñ", "o")
        ),
        listOf(
            word("oso", "o", "s", "o"),
            word("oveja", "o", "v", "e", "j", "a")
        ),
        listOf(
            word("pato", "p", "a", "t", "o"),
            word("pelota", "p", "e", "l", "o", "t", "a")
        ),
        listOf(
            word("queso", "q", "u", "e", "s", "o"),
            word("quince", "q", "u", "i", "n", "c", "e")
        ),
        listOf(
            word("rosa", "r", "o", "s", "a"),
            word("radio", "r", "a", "d", "i", "o")
        ),
        listOf(
            word("sol", "s", "o", "l"),
            word("silla", "s", "i", "l", "l", "a")
        ),
        listOf(
            word("taza", "t", "a", "z", "a"),
            word("tigre", "t", "i", "g", "r", "e")
        ),
        listOf(
            word("uva", "u", "v", "a"),
            word("uña", "u", "ñ", "a")
        ),
        listOf(
            word("vaca", "v", "a", "c", "a"),
            word("vela", "v", "e", "l", "a")
        ),
        listOf(
            word("web", "w", "e", "b"),
            word("wifi", "w", "i", "f", "i")
        ),
        listOf(
            word("xilófono", "x", "i", "l", "ó", "f", "o", "n", "o"),
            word("taxi", "t", "a", "x", "i")
        ),
        listOf(
            word("yoyo", "y", "o", "y", "o"),
            word("yogur", "y", "o", "g", "u", "r")
        ),
        listOf(
            word("zapato", "z", "a", "p", "a", "t", "o"),
            word("zorro", "z", "o", "r", "r", "o")
        ),
        listOf(
            word("árbol", "á", "r", "b", "o", "l"),
            word("área", "á", "r", "e", "a")
        ),
        listOf(
            word("café", "c", "a", "f", "é"),
            word("bebé", "b", "e", "b", "é")
        ),
        listOf(
            word("maíz", "m", "a", "í", "z"),
            word("raíz", "r", "a", "í", "z")
        ),
        listOf(
            word("balón", "b", "a", "l", "ó", "n"),
            word("limón", "l", "i", "m", "ó", "n")
        ),
        listOf(
            word("búho", "b", "ú", "h", "o"),
            word("baúl", "b", "a", "ú", "l")
        ),
        listOf(
            word("pingüino", "p", "i", "n", "g", "ü", "i", "n", "o"),
            word("bilingüe", "b", "i", "l", "i", "n", "g", "ü", "e")
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
            word("ya!", "y", "a", "!"),
            word("va!", "v", "a", "!")
        ),
        listOf(
            word("no?", "n", "o", "?"),
            word("qué?", "q", "u", "é", "?")
        ),
        listOf(
            word("sr.", "s", "r", "."),
            word("no.", "n", "o", ".")
        ),
        listOf(
            word("sol, luna", "s", "o", "l", ",", "l", "u", "n", "a"),
            word("sí, no", "s", "í", ",", "n", "o")
        ),
    )
}
