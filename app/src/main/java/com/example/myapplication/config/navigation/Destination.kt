package com.example.myapplication.config.navigation

import com.example.myapplication.core.model.Cart
import com.example.myapplication.core.model.PromoCode
import kotlinx.serialization.Serializable

object Destination {
    //Landing routes
    @Serializable
    data object LandingGraph
    @Serializable
    data object Splash
    @Serializable
    data object Intro

    //Authentication routes
    @Serializable
    data object AuthGraph
    @Serializable
    data object Login
    @Serializable
    data object Signup

    //Bottom nav routes
    @Serializable
    data object BottomGraph
    @Serializable
    data object Home
    @Serializable
    data object Favourites
    @Serializable
    data object Profile

    //Drawer routes
    @Serializable
    data object DrawerGraph
    @Serializable
    data object Dashboard
    @Serializable
    data object AboutUs
    @Serializable
    data object PrivacyPolicy
    @Serializable
    data object Settings

    //Profile routes
    @Serializable
    data class AddressListing(val selectionMode: Boolean = false)
    @Serializable
    data class NewAddress(val id: Int = 0)
    @Serializable
    data object OrderListing
    @Serializable
    data class OrderDetail(val orderId: Int)

    //Other routes
    @Serializable
    data object CartScreen
    @Serializable
    data class ProductDetail(val id: Int)
    @Serializable
    data class Checkout(val cart: Cart, val promoCode: PromoCode?)
}