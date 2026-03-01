package com.example.myapplication.core.model

import androidx.compose.runtime.Stable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.myapplication.core.local.DatabaseConfig

@Stable
@Entity(tableName = DatabaseConfig.ADDRESS)
data class Address(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int = 0,

    @ColumnInfo(name = "type")
    val type: Int,

    @ColumnInfo(name = "full_name")
    val fullName: String,

    @ColumnInfo(name = "address")
    val address: String,

    @ColumnInfo(name = "house_no")
    val houseNo: String? = "",

    @ColumnInfo(name = "city")
    val city: String,

    @ColumnInfo(name = "state")
    val state: String,

    @ColumnInfo(name = "country")
    val country: String,

    @ColumnInfo(name = "zipCode")
    val zipCode: String,

    @ColumnInfo(name = "default_address")
    val defaultAddress: Boolean = true,
) {
    fun getAddressType() = when (type) {
        0 -> AddressType.Home
        1 -> AddressType.Office
        2 -> AddressType.Other
        else -> throw Throwable("Unknow address type: $type")
    }
}

sealed class AddressType(val value: Int, val asset: String) {
    data object Home : AddressType(0, "home")
    data object Office : AddressType(1, "occupation")
    data object Other : AddressType(2, "location")
}
