package com.simplifiers.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Customer(
    @SerializedName("id")
    val id: Int,

    @SerializedName("name")
    val name: String
): Serializable
