package com.example.proyekakhir.repo

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.proyekakhir.api.ApiConfig
import com.example.proyekakhir.helper.Type
import com.example.proyekakhir.model.Data
import com.example.proyekakhir.model.DeleteBudgetResponse
import com.example.proyekakhir.model.GetBudgetResponse
import com.example.proyekakhir.model.PostBudgetResponse
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BudgetRepository(private val context : Application) {

    private val _dataBudget = MutableLiveData<List<Data>>()
    private val dataBudget : LiveData<List<Data>> = _dataBudget

    private val _newDataBudget = MutableLiveData<Data>()
    val newDataBudget : LiveData<Data> = _newDataBudget

    fun getBudget() : LiveData<List<Data>> {
        val client = ApiConfig.getApiService().getBudgetData()
        client.enqueue(object : Callback<GetBudgetResponse> {
            override fun onResponse(
                call: Call<GetBudgetResponse>,
                response: Response<GetBudgetResponse>
            ) {
                if (response.isSuccessful) {
                    _dataBudget.value = response.body()?.data
                } else {
                    val errorBody = response.errorBody()?.string()
                    val message = JSONObject(errorBody.toString()).getString("message")
                    Toast.makeText(context.applicationContext, message, Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<GetBudgetResponse>, t: Throwable) {
                Toast.makeText(context.applicationContext, "onFailure: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
        return dataBudget
    }

    fun addBudget(id : Int, budget : Int, date: String, type: Type, description : String) {
        val client = ApiConfig.getApiService().addBudgetData(id, budget, date, type, description)
        client.enqueue(object : Callback<PostBudgetResponse> {
            override fun onResponse(
                call: Call<PostBudgetResponse>,
                response: Response<PostBudgetResponse>
            ) {
                if (response.isSuccessful) {
                    _newDataBudget.value = response.body()?.data
                    Toast.makeText(context.applicationContext, response.body()?.message, Toast.LENGTH_SHORT).show()
                } else {
                    val errorBody = response.errorBody()?.string()
                    val message = JSONObject(errorBody.toString()).getString("message")
                    Toast.makeText(context.applicationContext, message, Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<PostBudgetResponse>, t: Throwable) {
                Toast.makeText(context.applicationContext, "onFailure: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun updateBudget(id: Int, groupId : Int, budget: Int, date: String, type: Type, description: String) {
        val client = ApiConfig.getApiService().updateBudgetData(id, groupId, budget, date, type, description)
        client.enqueue(object : Callback<PostBudgetResponse> {
            override fun onResponse(
                call: Call<PostBudgetResponse>,
                response: Response<PostBudgetResponse>
            ) {
                if (response.isSuccessful) {
                    _newDataBudget.value = response.body()?.data
                    Toast.makeText(context.applicationContext, response.body()?.message, Toast.LENGTH_SHORT).show()
                } else {
                    val errorBody = response.errorBody()?.string()
                    val message = JSONObject(errorBody.toString()).getString("message")
                    Toast.makeText(context.applicationContext, message, Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<PostBudgetResponse>, t: Throwable) {
                Toast.makeText(context.applicationContext, "onFailure: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun deleteBudget(id: Int) {
        val client = ApiConfig.getApiService().deleteBudgetData(id)
        client.enqueue(object : Callback<DeleteBudgetResponse> {
            override fun onResponse(
                call: Call<DeleteBudgetResponse>,
                response: Response<DeleteBudgetResponse>
            ) {
                if (response.isSuccessful) {
                    Toast.makeText(context.applicationContext, response.body()?.message, Toast.LENGTH_SHORT).show()
                } else {
                    val errorBody = response.errorBody()?.string()
                    val message = JSONObject(errorBody.toString()).getString("message")
                    Toast.makeText(context.applicationContext, message, Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<DeleteBudgetResponse>, t: Throwable) {
                Toast.makeText(context.applicationContext, "onFailure: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    companion object {
        @Volatile
        private var instance : BudgetRepository? = null
        fun getInstance(context: Application) : BudgetRepository =
            instance ?: synchronized(this) {
                instance ?: BudgetRepository(context)
            }.also { instance = it }
    }
}