package com.example.myapplication.core.local

import android.content.Context

object DatabaseConfig {
    const val DATABASE_VERSION = 4
    const val EXPORT_SCHEMA = true //TODO: MUST DISABLE IT IN PRODUCTION

    fun databaseName(context: Context) = "${context.packageName}.db"

    //ENTITIES NAMES
    const val PRODUCT = "product"
    const val CATEGORY = "category"
    const val CART = "cart"
    const val ADDRESS = "address"
    const val ORDER = "order"
}