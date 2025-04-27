package com.machaima.pokemonapp.core.domain.service.resource

import androidx.annotation.DimenRes
import androidx.annotation.StringRes

interface ResourceRepository {

    fun getResString(@StringRes stringResId: Int): String

    fun getDimens(@DimenRes dimenResId: Int): Int
}