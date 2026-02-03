package com.example.myapplication.core.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.myapplication.core.local.DatabaseConfig
import com.example.myapplication.core.model.Category
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategories(categories: List<Category>)

    @Query("SELECT * FROM ${DatabaseConfig.CATEGORY}")
    fun getAllCategories(): Flow<List<Category>?>
}