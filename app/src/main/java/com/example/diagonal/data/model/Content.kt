package com.example.diagonal.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * Created by Noushad N on 04-05-2022.
 */
data class Content(

    @SerializedName("name")
    var name: String? = null,

    @SerializedName("poster-image")
    var posterImage: String? = null
)