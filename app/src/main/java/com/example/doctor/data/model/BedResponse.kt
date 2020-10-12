package com.example.doctor.data.model

import com.google.gson.annotations.SerializedName

data class BedResponse(

	@field:SerializedName("channel")
	val channel: Channel? = null,

	@field:SerializedName("feeds")
	val feeds: List<FeedsItem?>? = null
)

data class Channel(

	@field:SerializedName("last_entry_id")
	val lastEntryId: Int? = null,

	@field:SerializedName("latitude")
	val latitude: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("field1")
	val field1: String? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("field7")
	val field7: String? = null,

	@field:SerializedName("field6")
	val field6: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("field3")
	val field3: String? = null,

	@field:SerializedName("field2")
	val field2: String? = null,

	@field:SerializedName("field5")
	val field5: String? = null,

	@field:SerializedName("longitude")
	val longitude: String? = null,

	@field:SerializedName("field4")
	val field4: String? = null
)

data class FeedsItem(

	@field:SerializedName("field1")
	val field1: String? = null,

	@field:SerializedName("field7")
	val field7: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("field6")
	val field6: String? = null,

	@field:SerializedName("field3")
	val field3: String? = null,

	@field:SerializedName("entry_id")
	val entryId: Int? = null,

	@field:SerializedName("field2")
	val field2: String? = null,

	@field:SerializedName("field5")
	val field5: String? = null,

	@field:SerializedName("field4")
	val field4: String? = null
)
