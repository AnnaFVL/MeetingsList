package com.example.mymeetings

import retrofit2.Call
import retrofit2.http.GET
interface ClientsApiService {
    @GET ("api/?inc=name,email,picture&results=15")
    fun getClients(): Call<ApiResponse>
}