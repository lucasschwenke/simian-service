package com.github.lucasschwenke.simian.domain.services

import com.github.lucasschwenke.simian.domain.dna.Dna
import com.github.lucasschwenke.simian.domain.dna.DnaType
import com.github.lucasschwenke.simian.domain.dna.repositories.DnaRepository
import com.github.lucasschwenke.simian.domain.validations.Validations

class DnaService(
    private val validations: Validations,
    private val dnaRepository: DnaRepository
) {

    /* Here I used Array<String> because in kotlin String[] has changed by Array<String> */
    fun isSimian(dna: Array<String>) : Boolean {
        checkExistsDna(dna)
        val simianFound = validateDna(dna)

        return simianFound.also {
            val newDna = Dna(
                dna = dna,
                type = DnaType.values().first { simianFound }
            )

            dnaRepository.create(newDna)
        }
    }

    private fun checkExistsDna(dna: Array<String>) {
        if (dnaRepository.exists(dna)) {
            //TODO: adjust exception
            throw Exception("")
        }
    }

    private fun validateDna(dna: Array<String>) : Boolean {
        val listOfValidations = validations.getValidations()

        var simian = false

        listOfValidations.find { it.isValid(dna, dna.size) }?.run {
            simian = true
        }

        return simian
    }
}