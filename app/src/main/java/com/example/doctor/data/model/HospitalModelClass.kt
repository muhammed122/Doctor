package com.example.doctor.data.model

data class HospitalModelClass(
    val Nofunit: Int,
    val emptyofunits: Int,
    val langitude: Double,
    val latitude: Double,
    val location: String,
    val phone_number: String
){

    constructor():this(0, 0,
    0.0,0.0, "",
    ""
    ){}
}