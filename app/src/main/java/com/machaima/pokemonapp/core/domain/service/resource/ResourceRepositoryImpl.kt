package com.machaima.pokemonapp.core.domain.service.resource

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext

class ResourceRepositoryImpl(@ApplicationContext private val context: Context) : ResourceRepository {
    override fun getResString(stringResId: Int): String =
        context.getString(stringResId)

    override fun getDimens(dimenResId: Int): Int =
        context.resources.getDimension(dimenResId).toInt()
}