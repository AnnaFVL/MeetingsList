package com.example.mymeetings

import com.google.gson.annotations.SerializedName
data class Client (
    @SerializedName("name")
    val name: Name,
    @SerializedName("email")
    val email: String,
    @SerializedName("picture.medium")
    val photoUrl: String
)

data class Name (
    @SerializedName("first")
    val first: String,
    @SerializedName("last")
    val last: String
)

data class ApiResponse (
    @SerializedName("results")
    val clients : List<Client>
)