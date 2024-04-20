package com.example.mymeetings.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.mymeetings.data.ApiResponse
import com.example.mymeetings.data.Client
import com.example.mymeetings.ClientsApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ClientsViewModel(): ViewModel() {
    private var restInterface: ClientsApiService
    val state = mutableStateOf(emptyList<Client>())

    init {
        val retrofit: Retrofit = Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://randomuser.me/").build()
        restInterface = retrofit.create(ClientsApiService::class.java)
    }

    fun getClients() {

        restInterface.getClients().enqueue(
            object : Callback<ApiResponse> {
                override fun onResponse(
                    call: Call<ApiResponse>,
                    response: Response<ApiResponse>
                ) {
                    response.body()?.let { jsonObject ->
                        state.value =
                            jsonObject.clients
                    }
                }

                override fun onFailure(
                    call: Call<ApiResponse>, t: Throwable
                ) {
                    t.printStackTrace()
                }
            })
    }

   /*

        restInterface.getClients().execute().body()
            ?.let { clients ->
                state.value = clients
            }
    }*/

    fun updateClient(person: Client) {
        //
    }

}