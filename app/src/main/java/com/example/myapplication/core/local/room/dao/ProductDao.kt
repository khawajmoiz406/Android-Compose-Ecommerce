package com.example.myapplication.core.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.RawQuery
import androidx.sqlite.db.SimpleSQLiteQuery
import androidx.sqlite.db.SupportSQLiteQuery
import com.example.myapplication.core.local.room.DatabaseConfig
import com.example.myapplication.models.request.Order
import com.example.myapplication.models.request.SortArrangement
import com.example.myapplication.models.response.product.Product

@Dao
interface ProductDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProducts(products: List<Product>)

    @RawQuery(observedEntities = [Product::class])
    suspend fun getAllProducts(query: SupportSQLiteQuery): List<Product>?

    @Query("SELECT * FROM ${DatabaseConfig.PRODUCT} WHERE id = :id")
    suspend fun getProductById(id: Int): Product?

    @Query("DELETE FROM ${DatabaseConfig.PRODUCT} WHERE id = :id")
    suspend fun deleteProduct(id: Int): Int

    @Query("UPDATE ${DatabaseConfig.PRODUCT} SET isFavourite = NOT isFavourite WHERE id = :id")
    suspend fun toggleFavourite(id: Int)

    suspend fun getProductsDynamically(arrangement: SortArrangement? = null, sort: Order? = null): List<Product>? {
        val sortBy = arrangement?.value
        val order = sort?.value ?: "ASC"

        val query = if (sortBy.isNullOrEmpty()) {
            "SELECT * FROM ${DatabaseConfig.PRODUCT}"
        } else {
            "SELECT * FROM ${DatabaseConfig.PRODUCT} ORDER BY $sortBy $order"
        }

        return getAllProducts(SimpleSQLiteQuery(query))
    }
}