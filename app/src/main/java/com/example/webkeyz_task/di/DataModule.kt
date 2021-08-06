package com.example.webkeyz_task.di

import android.content.Context
import android.content.SharedPreferences
import com.example.webkeyz_task.Data.remote.APIService
import com.example.webkeyz_task.Data.remote.EndPoint
import com.example.webkeyz_task.Data.remote.INetworkManager
import com.example.webkeyz_task.Data.remote.NetworkManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DataModule {


    @Provides
    @Singleton
    fun provideSharedpreferences(@ApplicationContext context: Context): SharedPreferences {
        return   context.getSharedPreferences("news", Context.MODE_MULTI_PROCESS)

    }

    @Provides
    @Singleton
    fun provideNetworkManager(api: APIService): INetworkManager {
        return NetworkManager(api)
    }


    @Provides
    @Singleton
    fun provideAPIService(retrofit: Retrofit): APIService {
        return retrofit.create(APIService::class.java)
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(EndPoint.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
    ): OkHttpClient {
        val okHttpClient = OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)

        return okHttpClient.build()
    }

    @Provides
    fun provideInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }

}