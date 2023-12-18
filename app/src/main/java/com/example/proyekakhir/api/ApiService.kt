package com.example.proyekakhir.api

import com.example.proyekakhir.helper.Type
import com.example.proyekakhir.model.PostBudgetResponse
import com.example.proyekakhir.model.DeleteBudgetGroupResponse
import com.example.proyekakhir.model.DeleteBudgetResponse
import com.example.proyekakhir.model.GetBudgetGroupResponse
import com.example.proyekakhir.model.GetBudgetResponse
import com.example.proyekakhir.model.PostUpdateBudgetGroupResponse
import retrofit2.Call
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("api/budget-groups")
    fun getBudgetGroups() : Call<GetBudgetGroupResponse>

    @POST("api/budget-groups")
    fun addBudgetGroups(
        @Query("nama_kelompok") name : String,
        @Query("deskripsi") description : String
    ) : Call<PostUpdateBudgetGroupResponse>

    @DELETE("api/budget-groups/{id}")
    fun deleteBudgetGroups(
        @Path("id") id : Int
    ) : Call<DeleteBudgetGroupResponse>

    @PATCH("api/budget-groups/{id}")
    fun updateBudgetGroups(
        @Path("id") id: Int,
        @Query("nama_kelompok") name: String,
        @Query("deskripsi") description: String
    ) : Call<PostUpdateBudgetGroupResponse>

    @POST("api/budget-data")
    fun addBudgetData(
        @Query("id_kelompok_anggaran") id: Int,
        @Query("anggaran") budget : Int,
        @Query("tanggal") date : String,
        @Query("jenis") type : Type,
        @Query("keterangan") description : String
    ) : Call<PostBudgetResponse>

    @GET("api/budget-data")
    fun getBudgetData() : Call<GetBudgetResponse>

    @PATCH("api/budget-data/{id}")
    fun updateBudgetData(
        @Path("id") id : Int,
        @Query("id_kelompok_anggaran") groupId : Int,
        @Query("anggaran") budget : Int,
        @Query("tanggal") date : String,
        @Query("jenis") type : Type,
        @Query("keterangan") description : String
    ) : Call<PostBudgetResponse>

    @DELETE("api/budget-data/{id}")
    fun deleteBudgetData(
        @Path("id") id: Int
    ) : Call<DeleteBudgetResponse>
}