package com.example.proyekakhir.repo

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.proyekakhir.api.ApiConfig
import com.example.proyekakhir.model.GetBudgetGroupResponse
import com.example.proyekakhir.model.DataItem
import com.example.proyekakhir.model.DeleteBudgetGroupResponse
import com.example.proyekakhir.model.PostUpdateBudgetGroupResponse
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BudgetGroupRepository(private val context: Application) {

    private val _dataBudgetGroup = MutableLiveData<List<DataItem>>()
    private val dataBudgetGroup : LiveData<List<DataItem>> = _dataBudgetGroup

    private val _newDataBudgetGroup = MutableLiveData<DataItem>()
    val newDataBudgetGroup : LiveData<DataItem> = _newDataBudgetGroup

    fun getBudgetGroup() : LiveData<List<DataItem>> {
        val client = ApiConfig.getApiService().getBudgetGroups()
        client.enqueue(object : Callback<GetBudgetGroupResponse> {
            override fun onResponse(
                call: Call<GetBudgetGroupResponse>,
                response: Response<GetBudgetGroupResponse>
            ) {
                if (response.isSuccessful) {
                    if (response.body() != null) {
                        _dataBudgetGroup.value = response.body()!!.data
                    }
                }
                else {
                    val errorBody = response.errorBody()?.string()
                    val message = JSONObject(errorBody.toString()).getString("message")
                    Toast.makeText(context.applicationContext, message, Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<GetBudgetGroupResponse>, t: Throwable) {
                Toast.makeText(context.applicationContext, "onFailure: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
        return dataBudgetGroup
    }

    fun addBudgetGroup(name : String, description : String) {
        val client = ApiConfig.getApiService().addBudgetGroups(name, description)
        client.enqueue(object : Callback<PostUpdateBudgetGroupResponse> {
            override fun onResponse(
                call: Call<PostUpdateBudgetGroupResponse>,
                response: Response<PostUpdateBudgetGroupResponse>
            ) {
                if (response.isSuccessful) {
                    _newDataBudgetGroup.value = response.body()?.data
                    Toast.makeText(context.applicationContext, response.body()?.message, Toast.LENGTH_SHORT).show()
                }
                else {
                    val errorBody = response.errorBody()?.string()
                    val message = JSONObject(errorBody.toString()).getString("message")
                    Toast.makeText(context.applicationContext, message, Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<PostUpdateBudgetGroupResponse>, t: Throwable) {
                Toast.makeText(context.applicationContext, "onFailure: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun updateBudgetGroup(id: Int, name: String, description: String) {
        val client = ApiConfig.getApiService().updateBudgetGroups(id, name, description)
        client.enqueue(object : Callback<PostUpdateBudgetGroupResponse> {
            override fun onResponse(
                call: Call<PostUpdateBudgetGroupResponse>,
                response: Response<PostUpdateBudgetGroupResponse>
            ) {
                if (response.isSuccessful) {
                    _newDataBudgetGroup.value = response.body()?.data
                    Toast.makeText(context.applicationContext, response.body()?.message, Toast.LENGTH_SHORT).show()
                }
                else {
                    val errorBody = response.errorBody()?.string()
                    val message = JSONObject(errorBody.toString()).getString("message")
                    Toast.makeText(context.applicationContext, message, Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<PostUpdateBudgetGroupResponse>, t: Throwable) {
                Toast.makeText(context.applicationContext, "onFailure: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun deleteBudgetGroup(id : Int) {
        val client = ApiConfig.getApiService().deleteBudgetGroups(id)
        client.enqueue(object : Callback<DeleteBudgetGroupResponse> {
            override fun onResponse(
                call: Call<DeleteBudgetGroupResponse>,
                response: Response<DeleteBudgetGroupResponse>
            ) {
                if (response.isSuccessful) {
                    Toast.makeText(context.applicationContext, response.body()?.message, Toast.LENGTH_SHORT).show()
                }
                else {
                    val errorBody = response.errorBody()?.string()
                    val message = JSONObject(errorBody.toString()).getString("message")
                    Toast.makeText(context.applicationContext, message, Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<DeleteBudgetGroupResponse>, t: Throwable) {
                Toast.makeText(context.applicationContext, "onFailure: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    companion object {
        @Volatile
        private var instance : BudgetGroupRepository? = null
        fun getInstance(context: Application) : BudgetGroupRepository =
            instance ?: synchronized(this) {
                instance ?: BudgetGroupRepository(context)
            }.also { instance = it }
    }
}