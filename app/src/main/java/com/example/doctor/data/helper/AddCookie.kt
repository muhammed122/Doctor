package com.example.doctor.data.helper

import android.content.Context
import androidx.preference.PreferenceManager
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

class AddCookie (val context: Context):Interceptor{

    val PREF_COOKIES = "PREF_COOKIES"


    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response? {
        val builder: Request.Builder = chain.request().newBuilder()

        val preferences =
            PreferenceManager.getDefaultSharedPreferences(context)
                .getStringSet(PREF_COOKIES, HashSet<String>()) as HashSet<String>

        // Use the following if you need everything in one line.
        // Some APIs die if you do it differently.
        /*String cookiestring = "";
        for (String cookie : preferences) {
            String[] parser = cookie.split(";");
            cookiestring = cookiestring + parser[0] + "; ";
        }
        builder.addHeader("Cookie", cookiestring);
        */

        for (cookie in preferences) {
            builder.addHeader("Cookie", cookie)

        }
        return chain.proceed(builder.build())
    }
}