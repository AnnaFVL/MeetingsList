package com.example.mymeetings.data

import com.google.gson.annotations.SerializedName
data class Client (
    @SerializedName("name")
    val name: Name,
    @SerializedName("email")
    val email: String,
    @SerializedName("picture")
    val photoUrl: Photo
)

data class Photo (
    @SerializedName("medium")
    val medium: String
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