package com.example.myapplication.core.local.room

import android.content.Context

object DatabaseConfig {
    const val DATABASE_VERSION = 1
    const val EXPORT_SCHEMA = true //TODO: MUST DISABLE IT IN PRODUCTION

    fun databaseName(context: Context) = "${context.packageName}.db"

    //ENTITIES NAMES
    const val PRODUCT = "product"
    const val CATEGORY = "category"
    const val USER = "user"
}