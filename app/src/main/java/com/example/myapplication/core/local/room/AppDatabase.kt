package com.example.myapplication.core.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.myapplication.core.local.room.DatabaseConfig.DATABASE_VERSION
import com.example.myapplication.core.local.room.DatabaseConfig.EXPORT_SCHEMA
import com.example.myapplication.core.local.room.dao.CategoryDao
import com.example.myapplication.core.local.room.dao.ProductDao
import com.example.myapplication.models.response.category.Category
import com.example.myapplication.models.response.product.Product
import com.example.myapplication.models.response.product.ProductConverters

@Database(version = DATABASE_VERSION, exportSchema = EXPORT_SCHEMA, entities = [Product::class, Category::class])
@TypeConverters(ProductConverters::class)
abstract class AppDatabase : RoomDatabase() {
    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(context, AppDatabase::class.java, DatabaseConfig.databaseName(context)).build()
            }
        }
    }

    abstract fun getProductDao(): ProductDao
    abstract fun getCategoryDao(): CategoryDao
}