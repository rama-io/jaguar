package com.rama.jaguar.braille

/** A word to practice, spelled out as the sequence of braille cells that make it up. */
data class BrailleWord(val text: String, val cells: List<BrailleSign>)
