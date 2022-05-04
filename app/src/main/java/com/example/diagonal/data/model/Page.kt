package com.example.diagonal.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable


/**
 * Created by Noushad N on 04-05-2022.
 */
data class Page (
    @SerializedName("title")
    var title: String? = null,

    @SerializedName("total-content-items")
    var totalContentItems: String? = null,

    @SerializedName("page-num")
    var pageNum: String? = null,

    @SerializedName("page-size")
    var pageSize: String? = null,

    @SerializedName("content-items")
    var contentItems: ContentItems? = null
)