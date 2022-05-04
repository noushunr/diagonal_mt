package com.example.diagonal.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable


/**
 * Created by Noushad N on 04-05-2022.
 */
data class Result(


    @SerializedName("page")
    @Expose
    var page: Page? = null
)
