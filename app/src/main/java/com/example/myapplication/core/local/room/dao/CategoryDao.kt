package com.example.myapplication.core.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.myapplication.core.local.room.DatabaseConfig
import com.example.myapplication.models.response.category.Category

@Dao
interface CategoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategories(categories: List<Category>)

    @Query("SELECT * FROM ${DatabaseConfig.CATEGORY}")
    suspend fun getAllCategories(): List<Category>
}