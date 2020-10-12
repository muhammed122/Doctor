package com.example.doctor.data.model

data class HospitalModel(
    val id: String,
    val name: String,
    val availBeds: Int,
    val busyBeds: Int,
    val location: String,
    val langitude: Double=0.0,
    val latitude: Double=0.0

)