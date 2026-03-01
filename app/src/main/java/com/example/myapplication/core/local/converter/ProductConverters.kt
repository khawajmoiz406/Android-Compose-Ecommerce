package com.example.myapplication.core.local.converter

import androidx.room.TypeConverter
import com.example.myapplication.core.model.Dimensions
import com.example.myapplication.core.model.Meta
import com.example.myapplication.core.model.Review
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

object ProductConverters {
    @TypeConverter
    fun fromStringList(value: List<String>?): String? = value?.let { Json.encodeToString(it) }

    @TypeConverter
    fun toStringList(value: String?): List<String>? = value?.let { Json.decodeFromString(it) }

    @TypeConverter
    fun fromDimensions(value: Dimensions?): String? = value?.let { Json.encodeToString(it) }

    @TypeConverter
    fun toDimensions(value: String?): Dimensions? = value?.let { Json.decodeFromString(it) }

    @TypeConverter
    fun fromMeta(value: Meta?): String? = value?.let { Json.encodeToString(it) }

    @TypeConverter
    fun toMeta(value: String?): Meta? = value?.let { Json.decodeFromString(it) }

    @TypeConverter
    fun fromReviewList(value: List<Review>?): String? = value?.let { Json.encodeToString(it) }

    @TypeConverter
    fun toReviewList(value: String?): List<Review>? = value?.let { Json.decodeFromString(it) }
}