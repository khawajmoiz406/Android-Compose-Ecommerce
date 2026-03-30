package com.ecommerce.shoppy.core.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ecommerce.shoppy.core.local.DatabaseConfig
import com.ecommerce.shoppy.core.model.Category
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategories(categories: List<Category>)

    @Query("SELECT * FROM ${DatabaseConfig.CATEGORY}")
    fun getAllCategories(): Flow<List<Category>?>
}