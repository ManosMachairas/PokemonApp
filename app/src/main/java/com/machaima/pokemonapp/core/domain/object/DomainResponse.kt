package com.machaima.pokemonapp.core.domain.`object`

import android.provider.ContactsContract.Data
import com.machaima.pokemonapp.util.EMPTY

/**
 * Each backend response should be transformed to DomainResponse.
 * This should be used by view models to decide what's the next state of the view
 */
class DomainResponse {
    var hasError: Boolean = false
    var errors: List<Error>? = null
    var errorMessage: String = EMPTY
    var responseList: List<ResponseData> = mutableListOf()
}