package com.example.doctor.data.source

import com.example.doctor.data.model.BedResponse
import com.example.doctor.data.model.DirectionResponse
import com.example.doctor.data.model.MessageResponse
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query


interface WebServices {

    @GET("channels/975083/feeds.json")
    suspend fun getBeds(@Query("results") value: Int): BedResponse


    @POST("medbot")
    suspend fun sendReceiveMessage(@Body body: JsonObject): MessageResponse


    @GET("maps/api/directions/json")
    suspend fun getDirection(
        @Query("origin") origin: String,
        @Query("destination") dest: String,
        @Query("key") key:String,
        @Query("sensor") sensor:Boolean=false,
        @Query("mode") mode:String="driving"
    ): DirectionResponse


}