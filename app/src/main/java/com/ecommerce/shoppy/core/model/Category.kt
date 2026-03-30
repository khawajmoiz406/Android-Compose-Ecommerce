package com.ecommerce.shoppy.core.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.ecommerce.shoppy.core.local.DatabaseConfig
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(tableName = DatabaseConfig.CATEGORY)
data class Category(
    @PrimaryKey
    @ColumnInfo(name = "slug")
    @SerializedName("slug")
    val slug: String,

    @ColumnInfo(name = "name")
    @SerializedName("name")
    val name: String
) : Serializable {
    @Ignore
    var icon: String = ""
}