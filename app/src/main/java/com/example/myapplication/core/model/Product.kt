package com.example.myapplication.core.model

import androidx.compose.runtime.Stable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.myapplication.base.BaseResponse
import com.example.myapplication.core.local.DatabaseConfig
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
@Stable
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

    @ColumnInfo(name = "isFavourite", defaultValue = "0")
    val isFavourite: Boolean = false,

    @ColumnInfo(name = "addedToCart", defaultValue = "0")
    val addedToCart: Boolean = false
) {

    fun calculateTotal(quantity: Int) = (price ?: 0.0) * quantity

    fun getPriceBeforeDiscount(): Double {
        val discountPrice = ((discountPercentage ?: 1.0) / 100) * (price ?: 0.0)
        return ((price ?: 0.0) + discountPrice)
    }

    fun getDiscountPrice(): Double {
        return ((discountPercentage ?: 1.0) * (price ?: 0.0)) / 100
    }

    fun calculateVolume(): Double {
        return (dimensions?.height ?: 1.0) * (dimensions?.width ?: 1.0) * (dimensions?.depth ?: 1.0)
    }
}

@Serializable
data class Meta(
    val barcode: String?,
    val createdAt: String?,
    val qrCode: String?,
    val updatedAt: String?
)

@Serializable
data class Dimensions(
    val depth: Double?,
    val height: Double?,
    val width: Double?
)

@Serializable
data class Review(
    val comment: String?,
    val date: String?,
    val rating: Int?,
    val reviewerEmail: String?,
    val reviewerName: String?
)

data class ProductsResponse(
    val products: List<Product>?
) : BaseResponse()