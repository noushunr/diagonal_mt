package com.example.diagonal.data.repositories

import android.content.Context
import com.example.diagonal.data.model.Result
import com.google.gson.Gson
import java.io.IOException
import java.io.InputStream

/**
 * Created by Noushad N on 04-05-2022.
 */
class BooksListRepositories(private val context : Context) {

    fun getBooksList(pageNo: Int): Result {
        var json: String? = null
        json = try {
            val `is`: InputStream = context.assets.open("CONTENTLISTINGPAGE-PAGE$pageNo.json")
            val size = `is`.available()
            val buffer = ByteArray(size)
            `is`.read(buffer)
            `is`.close()
            String(buffer, Charsets.UTF_8)
        } catch (ex: IOException) {
            ex.printStackTrace()
            return Result()
        }
        val gson = Gson()
        return gson.fromJson(json, Result::class.java)
    }


}