package com.rama.jaguar.braille

data class BrailleSign(
    val id: String,
    val display: String,
    val dots: Set<Int>
)
