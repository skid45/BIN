package com.example.bin.di

import com.example.bin.data.network.CardMetadataApi
import com.example.bin.data.network.CardMetadataService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object NetworkModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create())
        .baseUrl(CardMetadataService.BASE_URL)
        .build()


    @Provides
    @Singleton
    fun provideCardMetadataApi(retrofit: Retrofit): CardMetadataApi =
        retrofit.create(CardMetadataApi::class.java)


    @Provides
    @Singleton
    fun provideCardMetadataService(cardMetadataApi: CardMetadataApi): CardMetadataService =
        CardMetadataService(cardMetadataApi)
}