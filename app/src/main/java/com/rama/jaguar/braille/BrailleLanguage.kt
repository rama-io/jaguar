package com.rama.jaguar.braille

enum class BrailleLanguage(val code: String, val displayName: String) {
    ENGLISH("en", "English"),
    SPANISH("es", "Spanish");

    companion object {
        val DEFAULT = ENGLISH

        fun fromCode(code: String?): BrailleLanguage =
            values().firstOrNull { it.code == code } ?: DEFAULT
    }
}
