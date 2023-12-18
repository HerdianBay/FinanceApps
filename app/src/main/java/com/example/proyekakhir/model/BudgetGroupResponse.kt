package com.example.proyekakhir.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class GetBudgetGroupResponse(

	@field:SerializedName("data")
	val data: List<DataItem>? = null
)

@Parcelize
data class DataItem(

	@field:SerializedName("updated_at")
	var updatedAt: String? = null,

	@field:SerializedName("nama_kelompok")
	var namaKelompok: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("deskripsi")
	var deskripsi: String? = null
): Parcelable

data class PostUpdateBudgetGroupResponse(

	@field:SerializedName("data")
	val data: DataItem? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class DeleteBudgetGroupResponse(

	@field:SerializedName("message")
	val message: String? = null
)
