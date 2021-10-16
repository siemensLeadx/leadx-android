package com.siemens.leadx.di

import android.content.Context
import com.siemens.leadx.BuildConfig
import com.siemens.leadx.BuildConfig.MAIN_HOST
import com.siemens.leadx.data.local.sharedprefs.SharedPrefsUtils
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(MAIN_HOST)
            .client(okHttpClient)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    fun provideOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        @ApplicationContext context: Context,
    ): OkHttpClient {
        val sharedPrefsUtils = SharedPrefsUtils.getInstance(context)
        val builder = OkHttpClient
            .Builder()
            .addInterceptor(httpLoggingInterceptor)
            .addInterceptor {
                val request = it.request().newBuilder()
                    .addHeader("accept", "application/json")

                request.addHeader("x-api-key", "897B69D6-F693-4E4D-A519-CF55D5D5EEA4")
                val user = sharedPrefsUtils.getUser()
                if (user != null)
                    request.addHeader(
                        "authorization",
                        "${user.tokenType} ${user.accessToken}"
                    )
                request.addHeader(
                    "Accept-Language",
                    sharedPrefsUtils.getLanguage()
                )
                it.proceed(request.build())
            }
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
        return builder
            .build()
    }

    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        val logging = HttpLoggingInterceptor()
        logging.level = if (BuildConfig.DEBUG)
            HttpLoggingInterceptor.Level.BODY
        else
            HttpLoggingInterceptor.Level.NONE
        return logging
    }
}
