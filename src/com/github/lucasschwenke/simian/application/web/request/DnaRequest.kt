package com.github.lucasschwenke.simian.application.web.request

import com.github.lucasschwenke.simian.application.web.exceptions.InvalidCharacterException

data class DnaRequest(val dna: List<String>) {

    private val allowedCharacters = listOf("A", "T", "C", "G")

    fun validate() {
        dna.forEach { sequence ->
            sequence.forEach{ letter ->
                validateLetter(letter.toString())
            }
        }
    }

    private fun validateLetter(letter: String) {
        if (!allowedCharacters.contains(letter)) {
            throw InvalidCharacterException(
                "The char $this in dna it's invalid."
            )
        }
    }
}
