package com.rama.jaguar.braille

import com.rama.jaguar.braille.en.EnglishBraillePack
import com.rama.jaguar.braille.es.SpanishBraillePack

/** Central registry mapping each [BrailleLanguage] to its [BrailleLanguagePack]. */
object BrailleCatalog {

    // German (and other languages) aren't listed here yet: their accented/extended
    // letters need to be verified against a real braille reference before they're safe
    // to teach, the same way GRADE_3 used to be left empty rather than guessed at.
    private val packs: Map<BrailleLanguage, BrailleLanguagePack> = mapOf(
        BrailleLanguage.ENGLISH to EnglishBraillePack,
        BrailleLanguage.SPANISH to SpanishBraillePack,
    )

    fun packFor(language: BrailleLanguage): BrailleLanguagePack =
        packs[language] ?: error("No braille pack registered for $language")
}
