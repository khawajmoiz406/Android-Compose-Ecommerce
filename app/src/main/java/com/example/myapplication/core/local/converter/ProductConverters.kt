package com.example.myapplication.core.local.converter

import androidx.room.TypeConverter
import com.example.myapplication.core.model.Dimensions
import com.example.myapplication.core.model.Meta
import com.example.myapplication.core.model.Review
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object ProductConverters {
    private val gson = Gson()

    @TypeConverter
    fun fromStringList(value: List<String>?): String? = gson.toJson(value)

    @TypeConverter
    fun toStringList(value: String?): List<String>? = value?.let {
        gson.fromJson(value, object : TypeToken<List<String>>() {}.type)
    }

    @TypeConverter
    fun fromDimensions(dim: Dimensions?): String? = gson.toJson(dim)

    @TypeConverter
    fun toDimensions(value: String?): Dimensions? = value?.let {
        gson.fromJson(it, Dimensions::class.java)
    }

    @TypeConverter
    fun fromMeta(meta: Meta?): String? = gson.toJson(meta)

    @TypeConverter
    fun toMeta(value: String?): Meta? = value?.let {
        gson.fromJson(it, Meta::class.java)
    }

    @TypeConverter
    fun fromReviewList(list: List<Review>?): String? = gson.toJson(list)

    @TypeConverter
    fun toReviewList(value: String?): List<Review>? = value?.let {
        gson.fromJson(it, object : TypeToken<List<Review>>() {}.type)
    }
}
