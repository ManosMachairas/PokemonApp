package com.machaima.pokemonapp.core.domain.service.di

import android.content.Context
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.network.okHttpClient
import com.machaima.pokemonapp.core.domain.service.PokemonService
import com.machaima.pokemonapp.core.domain.service.PokemonServiceImpl
import com.machaima.pokemonapp.core.domain.service.cache.CachedRepository
import com.machaima.pokemonapp.core.domain.service.cache.CachedRepositoryImpl
import com.machaima.pokemonapp.core.domain.service.resource.ResourceRepository
import com.machaima.pokemonapp.core.domain.service.resource.ResourceRepositoryImpl
import com.machaima.pokemonapp.util.CACHE_SIZE
import com.machaima.pokemonapp.util.CACHE_TIME
import com.machaima.pokemonapp.util.SERVER_BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.File
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {

    @Singleton
    @Provides
    fun provideApolloClient(okHttpClient: OkHttpClient): ApolloClient {
        return ApolloClient.Builder()
            .serverUrl(SERVER_BASE_URL)
            .okHttpClient(okHttpClient)
            .build()
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(context: Context): OkHttpClient {
        val cache = Cache(File(context.cacheDir, "http_cache"), CACHE_SIZE)

        val cacheInterceptor = Interceptor { chain ->
            var request: Request = chain.request()
            request = request.newBuilder()
                .header("Cache-Control", "public, max-age=${CACHE_TIME}")
                .build()
            chain.proceed(request)
        }

        return OkHttpClient.Builder()
            .cache(cache)
            .addNetworkInterceptor(cacheInterceptor)
            .addInterceptor(cacheInterceptor)
            .build()
    }

    @Provides
    fun provideResourceRepository(@ApplicationContext context: Context): ResourceRepository =
        ResourceRepositoryImpl(context)

    @Provides
    fun providePokemonService(client: ApolloClient, cachedRepository: CachedRepository): PokemonService =
        PokemonServiceImpl(client, cachedRepository)

    @Singleton
    @Provides
    fun provideCachedRepository(): CachedRepository =
        CachedRepositoryImpl()
}