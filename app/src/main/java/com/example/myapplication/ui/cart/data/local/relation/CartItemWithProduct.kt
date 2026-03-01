package com.example.myapplication.ui.cart.data.local.relation

import androidx.room.Embedded
import androidx.room.Relation
import com.example.myapplication.core.model.Product
import com.example.myapplication.ui.cart.data.local.entity.CartItem
import kotlinx.serialization.Serializable

// Helper to load a CartItem together with its Product in a single transaction.
// Without this, each product would require a separate database query, which can be expensive.
@Serializable
data class CartItemWithProduct(
    @Embedded val cartItem: CartItem,

    @Relation(parentColumn = "product_id", entityColumn = "id")
    val product: Product
)