package com.rama.jaguar.braille

import com.rama.jaguar.braille.en.EnglishBraillePack
import com.rama.jaguar.braille.es.SpanishBraillePack

/** Central registry mapping each [BrailleLanguage] to its [BrailleLanguagePack]. */
object BrailleCatalog {
    
    private val packs: Map<BrailleLanguage, BrailleLanguagePack> = mapOf(
        BrailleLanguage.ENGLISH to EnglishBraillePack,
        BrailleLanguage.SPANISH to SpanishBraillePack,
    )

    fun packFor(language: BrailleLanguage): BrailleLanguagePack =
        packs[language] ?: error("No braille pack registered for $language")
}
