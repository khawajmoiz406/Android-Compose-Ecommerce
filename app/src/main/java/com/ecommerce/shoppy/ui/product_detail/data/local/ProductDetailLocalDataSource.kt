package com.ecommerce.shoppy.ui.product_detail.data.local

import com.ecommerce.shoppy.core.local.AppDatabase
import com.ecommerce.shoppy.core.model.Product
import com.ecommerce.shoppy.core.shared.data.remote.dto.AddProductToCartRequest
import com.ecommerce.shoppy.ui.cart.data.local.entity.CartItem

class ProductDetailLocalDataSource(private val database: AppDatabase) {
    suspend fun saveProduct(product: Product) {
        return database.getProductDao().insertProduct(product)
    }

    suspend fun getProduct(productId: String): Product? {
        return database.getProductDao().getProductById(productId.toInt())
    }

    suspend fun addProductToCart(requestParams: AddProductToCartRequest) {
        val item = CartItem(productId = requestParams.product.id ?: 0, quantity = requestParams.quantity)
        database.getCartDao().insertCartItem(item)
        database.getProductDao().toggleCartValue(item.productId)
        return
    }

    suspend fun removeProductFromCart(productId: Int) {
        database.getCartDao().removeCartItem(productId)
        database.getProductDao().toggleCartValue(productId)
        return
    }

    suspend fun toggleFavourite(productId: Int) {
        return database.getProductDao().toggleFavourite(productId)
    }
}