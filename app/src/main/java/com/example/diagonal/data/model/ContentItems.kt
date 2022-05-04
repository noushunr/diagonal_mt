package com.example.diagonal.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable


/**
 * Created by Noushad N on 04-05-2022.
 */
data class ContentItems (
    @SerializedName("content")
    var content: List<Content>? = null
)