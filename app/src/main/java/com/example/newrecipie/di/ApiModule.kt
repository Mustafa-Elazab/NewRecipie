package com.example.newrecipie.di


import com.example.newrecipie.data.remote.ApiServices
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton


//@InstallIn(SingletonComponent::class)
//@Module
//class ApiModule {
//
//
//    @Singleton
//    @Provides
//    fun provideRetrofitServices(): ApiServices = Retrofit.Builder()
//        .baseUrl(ApiServices.BASE_URL)
//        .addConverterFactory(
//            MoshiConverterFactory.create(
//                Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
//            )
//        )
//        .build()
//        .create(ApiServices::class.java)
//
//}