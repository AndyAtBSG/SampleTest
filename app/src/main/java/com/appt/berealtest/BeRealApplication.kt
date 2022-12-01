package com.appt.berealtest

import android.app.Application
import android.util.Base64
import com.appt.berealtest.services.Base64EncoderService
import com.appt.berealtest.services.BeRealApi
import com.appt.berealtest.services.NetworkBeRealImageService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create


class BeRealApplication : Application() {
    private val client = OkHttpClient.Builder()
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.SERVER_URL)
        .client(client)
        .addConverterFactory(
            MoshiConverterFactory.create(
                Moshi.Builder()
                    .add(KotlinJsonAdapterFactory())
                    .build()
            )
        )
        .build()

    private val retrofitApiService = retrofit.create<BeRealApi>()

    private val base64Service: Base64EncoderService = object : Base64EncoderService {
        override fun encode(data: String): String {
            val bytes = data.toByteArray()
            return Base64.encodeToString(bytes, Base64.NO_WRAP)
        }
    }

    val imageService = NetworkBeRealImageService(retrofitApiService, base64Service)
}
