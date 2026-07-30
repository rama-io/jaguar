package com.rama.jaguar.braille.pt

import com.rama.jaguar.braille.BrailleSign
import com.rama.jaguar.braille.BrailleWord

object PortugueseWordBank {

    private fun cell(id: String): BrailleSign =
        PortugueseBrailleData.find(id) ?: error("Missing Portuguese braille sign for id \"$id\"")

    private fun word(text: String, vararg ids: String) = BrailleWord(text, ids.map(::cell))

    val WORDS_GRADE_1: List<List<BrailleWord>> = listOf(
        listOf(
            word("abacaxi", "a", "b", "a", "c", "a", "x", "i"),
            word("amigo", "a", "m", "i", "g", "o")
        ),
        listOf(
            word("bola", "b", "o", "l", "a"),
            word("banana", "b", "a", "n", "a", "n", "a")
        ),
        listOf(
            word("casa", "c", "a", "s", "a"),
            word("carro", "c", "a", "r", "r", "o")
        ),
        listOf(
            word("taça", "t", "a", "ç", "a"),
            word("moço", "m", "o", "ç", "o")
        ),
        listOf(
            word("dado", "d", "a", "d", "o"),
            word("dedo", "d", "e", "d", "o")
        ),
        listOf(
            word("elefante", "e", "l", "e", "f", "a", "n", "t", "e"),
            word("escola", "e", "s", "c", "o", "l", "a")
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
            word("hoje", "h", "o", "j", "e"),
            word("hotel", "h", "o", "t", "e", "l")
        ),
        listOf(
            word("ilha", "i", "l", "h", "a"),
            word("isca", "i", "s", "c", "a")
        ),
        listOf(
            word("janela", "j", "a", "n", "e", "l", "a"),
            word("jogo", "j", "o", "g", "o")
        ),
        listOf(
            word("kiwi", "k", "i", "w", "i"),
            word("kart", "k", "a", "r", "t")
        ),
        listOf(
            word("lua", "l", "u", "a"),
            word("livro", "l", "i", "v", "r", "o")
        ),
        listOf(
            word("mesa", "m", "e", "s", "a"),
            word("macaco", "m", "a", "c", "a", "c", "o")
        ),
        listOf(
            word("nuvem", "n", "u", "v", "e", "m"),
            word("navio", "n", "a", "v", "i", "o")
        ),
        listOf(
            word("ovo", "o", "v", "o"),
            word("osso", "o", "s", "s", "o")
        ),
        listOf(
            word("pato", "p", "a", "t", "o"),
            word("pipa", "p", "i", "p", "a")
        ),
        listOf(
            word("queijo", "q", "u", "e", "i", "j", "o"),
            word("quatro", "q", "u", "a", "t", "r", "o")
        ),
        listOf(
            word("rosa", "r", "o", "s", "a"),
            word("rato", "r", "a", "t", "o")
        ),
        listOf(
            word("sol", "s", "o", "l"),
            word("sapo", "s", "a", "p", "o")
        ),
        listOf(
            word("touro", "t", "o", "u", "r", "o"),
            word("tigre", "t", "i", "g", "r", "e")
        ),
        listOf(
            word("uva", "u", "v", "a"),
            word("urso", "u", "r", "s", "o")
        ),
        listOf(
            word("vaca", "v", "a", "c", "a"),
            word("vela", "v", "e", "l", "a")
        ),
        listOf(
            word("watt", "w", "a", "t", "t"),
            word("kiwi", "k", "i", "w", "i")
        ),
        listOf(
            word("xadrez", "x", "a", "d", "r", "e", "z"),
            word("táxi", "t", "á", "x", "i")
        ),
        listOf(
            word("yoga", "y", "o", "g", "a"),
            word("kayak", "k", "a", "y", "a", "k")
        ),
        listOf(
            word("zebra", "z", "e", "b", "r", "a"),
            word("zero", "z", "e", "r", "o")
        ),
        listOf(
            word("árvore", "á", "r", "v", "o", "r", "e"),
            word("sábado", "s", "á", "b", "a", "d", "o")
        ),
        listOf(
            word("àquele", "à", "q", "u", "e", "l", "e")
        ),
        listOf(
            word("câmera", "c", "â", "m", "e", "r", "a"),
            word("âncora", "â", "n", "c", "o", "r", "a")
        ),
        listOf(
            word("irmã", "i", "r", "m", "ã"),
            word("maçã", "m", "a", "ç", "ã")
        ),
        listOf(
            word("café", "c", "a", "f", "é"),
            word("pé", "p", "é")
        ),
        listOf(
            word("mês", "m", "ê", "s"),
            word("três", "t", "r", "ê", "s")
        ),
        listOf(
            word("país", "p", "a", "í", "s"),
            word("caí", "c", "a", "í")
        ),
        listOf(
            word("só", "s", "ó"),
            word("avó", "a", "v", "ó")
        ),
        listOf(
            word("ônibus", "ô", "n", "i", "b", "u", "s"),
            word("avô", "a", "v", "ô")
        ),
        listOf(
            word("põe", "p", "õ", "e"),
            word("leões", "l", "e", "õ", "e", "s")
        ),
        listOf(
            word("baú", "b", "a", "ú"),
            word("açúcar", "a", "ç", "ú", "c", "a", "r")
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
            word("já!", "j", "á", "!"),
            word("oi!", "o", "i", "!")
        ),
        listOf(
            word("não?", "n", "ã", "o", "?"),
            word("quê?", "q", "u", "ê", "?")
        ),
        listOf(
            word("sr.", "s", "r", "."),
            word("dr.", "d", "r", ".")
        ),
        listOf(
            word("sol, lua", "s", "o", "l", ",", "l", "u", "a"),
            word("céu, mar", "c", "é", "u", ",", "m", "a", "r")
        ),
    )
}
