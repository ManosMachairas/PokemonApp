package com.machaima.pokemonapp.base.di.modules

import android.content.Context
import com.machaima.pokemonapp.base.app.PokemonApplication
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideApp(@ApplicationContext context: Context): PokemonApplication {
        return context as PokemonApplication
    }

    @Provides
    @ApplicationContext
    fun provideContext(@ApplicationContext context: Context): Context = context
}