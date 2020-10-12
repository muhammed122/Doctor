package com.example.doctor.data.source

import android.content.Context
import com.example.doctor.data.helper.AddCookie
import com.example.doctor.data.helper.ReceiveCookie
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


object ApiManager {
    const val BASE_URL1 = "https://api.thingspeak.com/"


    fun getBedService(): WebServices {
        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())

            .baseUrl(BASE_URL1)
            .build()

        return retrofit.create(WebServices::class.java)
    }


    const val BASE_URL2 = "https://medbot-assistant.herokuapp.com/"
    fun getChatService(context: Context): WebServices {

//        val requestInterceptor = Interceptor { chain ->
//            val url: HttpUrl = chain.request().url().newBuilder()
//                .addQueryParameter("api_key", API_KEY)
//                .build()
//
//            val request = chain.request()
//                .newBuilder()
//                .url(url)
//                .build()
//
//            return@Interceptor chain.proceed(request)
//        }


        var client = OkHttpClient()
        val builder = OkHttpClient.Builder()

        builder.addInterceptor(AddCookie(context)) // VERY VERY IMPORTANT
        builder.addInterceptor(ReceiveCookie(context)) // VERY VERY IMPORTANT
        builder.connectTimeout(30, TimeUnit.SECONDS)
        builder.writeTimeout(30, TimeUnit.SECONDS)
        builder.readTimeout(30, TimeUnit.SECONDS)


        client = builder.build()


        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL2)
            .client(client)
            .build()

        return retrofit.create(WebServices::class.java)
    }

    const val BASE_URL3 = "https://maps.googleapis.com/"
    fun getMapService(): WebServices {
        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL3)
            .build()

        return retrofit.create(WebServices::class.java)
    }

}