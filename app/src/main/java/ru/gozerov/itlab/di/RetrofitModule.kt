package ru.gozerov.itlab.di

import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import ru.gozerov.data.login.remote.LoginApi
import ru.gozerov.itlab.utils.ApiConstants
import javax.inject.Singleton

@Module
class RetrofitModule {

    @Provides
    @Singleton
    fun provideLoginApi(): LoginApi {
        val moshi = Moshi.Builder()
            .build()
        val moshiConverterFactory = MoshiConverterFactory.create(moshi)
        val loggingInterceptor =
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
        val retrofit = Retrofit.Builder()
            .baseUrl(ApiConstants.BASE_URL)
            .client(client)
            .addConverterFactory(moshiConverterFactory)
            .build()
        return retrofit.create(LoginApi::class.java)
    }

}