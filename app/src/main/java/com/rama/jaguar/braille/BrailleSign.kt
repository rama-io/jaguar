package com.rama.jaguar.braille

/**
 * A single braille sign: a letter (grade 1) or a contraction / word-sign (grade 2+),
 * specific to one language's braille code (see [BrailleLanguage]).
 *
 * [dots] uses standard braille dot numbering:
 *   1 4
 *   2 5
 *   3 6
 * (1,2,3 down the left column top-to-bottom; 4,5,6 down the right column top-to-bottom.)
 */
data class BrailleSign(
    val id: String,
    val display: String,
    val dots: Set<Int>
)
