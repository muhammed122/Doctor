package com.example.doctor.data.model

import com.google.gson.annotations.SerializedName

data class MessageResponse(

	@field:SerializedName("message")
	val message: List<String?>? = null
)
