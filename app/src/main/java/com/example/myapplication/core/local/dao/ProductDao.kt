package com.example.myapplication.core.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.RawQuery
import androidx.sqlite.db.SimpleSQLiteQuery
import androidx.sqlite.db.SupportSQLiteQuery
import com.example.myapplication.core.local.DatabaseConfig
import com.example.myapplication.core.model.Product
import com.example.myapplication.core.shared.data.remote.dto.Order
import com.example.myapplication.core.shared.data.remote.dto.SortArrangement
import com.example.myapplication.ui.home.data.local.entity.ProductFavoriteStatus
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProducts(products: List<Product>)

    @Query("SELECT id, isFavourite FROM ${DatabaseConfig.PRODUCT}")
    fun observeFavoriteStatus(): Flow<List<ProductFavoriteStatus>?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProduct(product: Product)

    @Query("SELECT * FROM ${DatabaseConfig.PRODUCT} WHERE id = :id")
    suspend fun getProductById(id: Int): Product?

    @Query("DELETE FROM ${DatabaseConfig.PRODUCT} WHERE id = :id")
    suspend fun deleteProduct(id: Int): Int

    @Query("UPDATE ${DatabaseConfig.PRODUCT} SET isFavourite = NOT isFavourite WHERE id = :id")
    suspend fun toggleFavourite(id: Int)

    @Query("UPDATE ${DatabaseConfig.PRODUCT} SET addedToCart = NOT addedToCart WHERE id = :id")
    suspend fun toggleCartValue(id: Int)

    @RawQuery(observedEntities = [Product::class])
    fun getAllProducts(query: SupportSQLiteQuery): Flow<List<Product>>

    fun getProductsDynamically(arrangement: SortArrangement? = null, sort: Order? = null): Flow<List<Product>> {
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