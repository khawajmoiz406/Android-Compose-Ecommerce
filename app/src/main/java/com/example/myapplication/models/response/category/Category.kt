package com.example.myapplication.models.response.category

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.example.myapplication.core.local.room.DatabaseConfig
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(tableName = DatabaseConfig.CATEGORY)
data class Category(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int? = null,

    @ColumnInfo(name = "slug")
    @SerializedName("slug")
    val slug: String,

    @ColumnInfo(name = "name")
    @SerializedName("name")
    val name: String,

    @Ignore
    var icon: String = ""
) : Serializable
