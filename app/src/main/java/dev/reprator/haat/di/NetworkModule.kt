package dev.reprator.haat.di

import android.content.Context
import coil.ImageLoader
import coil.util.DebugLogger
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import dagger.Lazy
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.reprator.haat.util.AppCoroutineDispatchers
import kotlinx.coroutines.Dispatchers
import okhttp3.Call
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

private const val CONNECTION_TIME = 90L

@Module
@InstallIn(SingletonComponent::class)
internal object NetworkModule {

    @Provides
    @Singleton
    fun provideCoroutineDispatchers(): AppCoroutineDispatchers = AppCoroutineDispatchers(
        io = Dispatchers.IO,
        computation = Dispatchers.Default,
        singleThread = Dispatchers.IO.limitedParallelism(1),
        databaseRead = Dispatchers.IO,
        main = Dispatchers.Main.immediate,
    )

    @Provides
    @Singleton
    fun httpLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

    @Provides
    @Singleton
    fun okHttpCallFactory(loggingInterceptor: HttpLoggingInterceptor): Call.Factory = OkHttpClient.Builder().apply {
        connectTimeout(CONNECTION_TIME, TimeUnit.SECONDS)
        readTimeout(CONNECTION_TIME, TimeUnit.SECONDS)
        writeTimeout(CONNECTION_TIME, TimeUnit.SECONDS)
        addInterceptor(loggingInterceptor)
    }.build()

    @Provides
    @Singleton
    fun imageLoader(
        okHttpCallFactory: Lazy<Call.Factory>,
        @ApplicationContext application: Context,
    ): ImageLoader = ImageLoader.Builder(application)
            .callFactory { okHttpCallFactory.get() }
            .respectCacheHeaders(false)
            .apply {
                    logger(DebugLogger())
            }
            .build()

    @Provides
    @Singleton
    fun jacksonAppObjectMapper(): ObjectMapper = jacksonObjectMapper().apply {
        disable(SerializationFeature.FAIL_ON_EMPTY_BEANS)
        disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
    }

    @Provides
    @Singleton
    fun buildRetrofit(objectMapper: ObjectMapper, okHttpCallFactory: Lazy<Call.Factory>): Retrofit = Retrofit.Builder()
        .baseUrl("https://user-new-app-staging.internal.haat.delivery/api/")
        .addConverterFactory(JacksonConverterFactory.create(objectMapper))
        .callFactory(okHttpCallFactory.get())
        .build()

    @Provides
    @Singleton
    fun buildApiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)
}
