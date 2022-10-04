package com.esa.submission1bpaai.data.network.response

import com.esa.submission1bpaai.data.model.Login
import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("error")
    val error: Boolean?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("loginResult")
    val loginResult: Login?,
)
