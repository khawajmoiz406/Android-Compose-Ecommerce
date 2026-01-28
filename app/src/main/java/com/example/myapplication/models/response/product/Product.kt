package com.example.myapplication.models.response.product

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.example.myapplication.core.local.room.DatabaseConfig
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken
import java.io.Serializable

@Entity(tableName = DatabaseConfig.PRODUCT)
data class Product(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    @SerializedName("id")
    val id: Int?,

    @ColumnInfo(name = "availabilityStatus")
    @SerializedName("availabilityStatus")
    val availabilityStatus: String?,

    @ColumnInfo(name = "brand")
    @SerializedName("brand")
    val brand: String?,

    @ColumnInfo(name = "category")
    @SerializedName("category")
    val category: String?,

    @ColumnInfo(name = "description")
    @SerializedName("description")
    val description: String?,

    @ColumnInfo(name = "dimensions")
    @SerializedName("dimensions")
    val dimensions: Dimensions?,

    @ColumnInfo(name = "discountPercentage")
    @SerializedName("discountPercentage")
    val discountPercentage: Double?,

    @ColumnInfo(name = "images")
    @SerializedName("images")
    val images: List<String>?,

    @ColumnInfo(name = "meta")
    @SerializedName("meta")
    val meta: Meta?,

    @ColumnInfo(name = "minimumOrderQuantity")
    @SerializedName("minimumOrderQuantity")
    val minimumOrderQuantity: Int?,

    @ColumnInfo(name = "price")
    @SerializedName("price")
    val price: Double?,

    @ColumnInfo(name = "rating")
    @SerializedName("rating")
    val rating: Double?,

    @ColumnInfo(name = "returnPolicy")
    @SerializedName("returnPolicy")
    val returnPolicy: String?,

    @ColumnInfo(name = "reviews")
    @SerializedName("reviews")
    val reviews: List<Review>?,

    @ColumnInfo(name = "shippingInformation")
    @SerializedName("shippingInformation")
    val shippingInformation: String?,

    @ColumnInfo(name = "sku")
    @SerializedName("sku")
    val sku: String?,

    @ColumnInfo(name = "stock")
    @SerializedName("stock")
    val stock: Int?,

    @ColumnInfo(name = "tags")
    @SerializedName("tags")
    val tags: List<String>?,

    @ColumnInfo(name = "thumbnail")
    @SerializedName("thumbnail")
    val thumbnail: String?,

    @ColumnInfo(name = "title")
    @SerializedName("title")
    val title: String?,

    @ColumnInfo(name = "warrantyInformation")
    @SerializedName("warrantyInformation")
    val warrantyInformation: String?,

    @ColumnInfo(name = "weight")
    @SerializedName("weight")
    val weight: Int?,

    @ColumnInfo(name = "isFavourite")
    var isFavourite: Boolean?
) : Serializable {
    fun calculateTotal(quantity: Int) = (price ?: 0.0) * quantity

    fun getPriceBeforeDiscount(): Double {
        val discountPrice = ((discountPercentage ?: 1.0) / 100) * (price ?: 0.0)
        return ((price ?: 0.0) + discountPrice)
    }

    fun calculateVolume(): Double {
        return (dimensions?.height ?: 1.0) * (dimensions?.width ?: 1.0) * (dimensions?.depth ?: 1.0)
    }
}

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