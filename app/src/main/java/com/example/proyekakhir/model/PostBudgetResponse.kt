package com.example.proyekakhir.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class PostBudgetResponse(

	@field:SerializedName("data")
	val data: Data? = null,

	@field:SerializedName("message")
	val message: String? = null
)

@Parcelize
data class Data(

	@field:SerializedName("anggaran")
	var anggaran: Int? = null,

	@field:SerializedName("keterangan")
	var keterangan: String? = null,

	@field:SerializedName("id_kelompok_anggaran")
	val idKelompokAnggaran: Int? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("jenis")
	var jenis: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("tanggal")
	var tanggal: String? = null,

	@field:SerializedName("id")
	val id: Int? = null
): Parcelable

data class GetBudgetResponse(

	@field:SerializedName("data")
	val data: List<Data>? = null
)

data class DeleteBudgetResponse(

	@field:SerializedName("message")
	val message: String? = null
)
