package com.nat.bless.base.network.di

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.nat.bless.BuildConfig
import com.nat.bless.base.dataStore.PreferencesKeys
import com.nat.bless.base.dataStore.dataStore
import com.nat.bless.base.network.ApiServices
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.util.concurrent.TimeUnit

val networkModule = module {

    single<Interceptor>(named("AuthInterceptor")) {
        Interceptor { chain ->
            val token = getToken(get())

            val request = chain.request().newBuilder()
                .addHeader("Authorization", "Bearer $token")
                .addHeader("Accept", "application/json")
                .build()
            chain.proceed(request)
        }
    }

    single<Interceptor>(named("ConnectivityInterceptor")) {
        Interceptor { chain ->
            if (isNetworkAvailable(androidContext()).not()) {
                throw NoConnectionException("Not Internet Connection")
            }
            chain.proceed(chain.request())
        }
    }



    single<OkHttpClient> {
        OkHttpClient.Builder()
            .addInterceptor(get<Interceptor>(named("AuthInterceptor")))
            .addInterceptor(get<Interceptor>(named("ConnectivityInterceptor")))
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .retryOnConnectionFailure(true) // Automatically retry when network failure
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .writeTimeout(120, TimeUnit.SECONDS)
            .build()

    }

    single<Retrofit> {
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(get())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }


    single<ApiServices> {
        provideApiServices(get())
    }
}


fun provideApiServices(retrofit: Retrofit): ApiServices {
    return retrofit.create(ApiServices::class.java)
}


// Helper function to check the network availability
fun isNetworkAvailable(context: Context): Boolean {
    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val network = connectivityManager.activeNetwork ?: return false
    val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
    return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
}

class NoConnectionException(error: String) : IOException(error)

private fun getToken(context: Context): String {
    var token: String
    runBlocking {
        token = context.dataStore.data.map { preferences ->
            preferences[PreferencesKeys.token] ?: ""
        }.first()
    }
    return token
}