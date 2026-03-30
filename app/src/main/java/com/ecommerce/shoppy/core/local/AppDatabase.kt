package com.ecommerce.shoppy.core.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.ecommerce.shoppy.core.local.converter.OrderConverters
import com.ecommerce.shoppy.core.local.converter.ProductConverters
import com.ecommerce.shoppy.core.local.dao.AddressDao
import com.ecommerce.shoppy.core.local.dao.CartDao
import com.ecommerce.shoppy.core.local.dao.CategoryDao
import com.ecommerce.shoppy.core.local.dao.OrderDao
import com.ecommerce.shoppy.core.local.dao.ProductDao
import com.ecommerce.shoppy.core.model.Address
import com.ecommerce.shoppy.core.model.Category
import com.ecommerce.shoppy.core.model.Order
import com.ecommerce.shoppy.core.model.Product
import com.ecommerce.shoppy.ui.cart.data.local.entity.CartItem

@Database(
    version = DatabaseConfig.DATABASE_VERSION,
    exportSchema = DatabaseConfig.EXPORT_SCHEMA,
    entities = [Product::class, Category::class, CartItem::class, Address::class, Order::class]
)
@TypeConverters(ProductConverters::class, OrderConverters::class)
abstract class AppDatabase : RoomDatabase() {
    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                Room
                    .databaseBuilder(
                        context,
                        AppDatabase::class.java,
                        DatabaseConfig.databaseName(context)
                    )
                    .addMigrations(MIGRATION_3_4)
                    .build()
            }
        }
    }

    abstract fun getProductDao(): ProductDao
    abstract fun getCategoryDao(): CategoryDao
    abstract fun getCartDao(): CartDao
    abstract fun getAddressDao(): AddressDao
    abstract fun getOrderDao(): OrderDao
}