package com.example.servicecenter.apiconnect.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private val interceptor: HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val client: OkHttpClient = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val request = chain.request().newBuilder()
                .addHeader("apikey", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImlxeGhieWh3YmN4eWdpc2hsZHN4Iiwicm9sZSI6ImFub24iLCJpYXQiOjE3NDE1ODAxMDMsImV4cCI6MjA1NzE1NjEwM30.MvOWopuo2WP7CHZzSyIjKStx8ylFvl0rqlh4LrsoNM8")
                .addHeader("Authorization", "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImlxeGhieWh3YmN4eWdpc2hsZHN4Iiwicm9sZSI6ImFub24iLCJpYXQiOjE3NDE1ODAxMDMsImV4cCI6MjA1NzE1NjEwM30.MvOWopuo2WP7CHZzSyIjKStx8ylFvl0rqlh4LrsoNM8")
                .build()
            chain.proceed(request)
        }
        .addInterceptor(interceptor)
        .build()

    val apiService: ServiceApi = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(ServiceApi.BASE_URL)
        .client(client)
        .build()
        .create(ServiceApi::class.java)
}
