package com.ecommerce.shoppy.config.utils

import com.ecommerce.shoppy.R
import com.ecommerce.shoppy.core.model.NavigationItem
import com.ecommerce.shoppy.config.navigation.Destination

object Constants {
    const val SPLASH_DELAY = 1500L //In Milliseconds
    const val TOKEN_TIMEOUT = 30 //In Minutes
    const val PLATFORM_FEES = 2.0 //In dollars
    const val DELIVERY_FEES = 10.0 //In dollars
    const val VAT = 1.0 //In dollars

    val CATEGORIES_ICON_MAP = mapOf(
        "all" to "all_categories",
        "beauty" to "beauty",
        "fragrances" to "fragrance",
        "furniture" to "furniture",
        "groceries" to "groceries",
        "home-decoration" to "decoration",
        "kitchen-accessories" to "kitchen",
        "laptops" to "laptop",
        "mens-shirts" to "shirt",
        "mens-shoes" to "sneaker",
        "mens-watches" to "watch",
        "mobile-accessories" to "accessories",
        "motorcycle" to "motorcycle",
        "skin-care" to "skin_care",
        "smartphones" to "mobile",
        "sports-accessories" to "sport",
        "sunglasses" to "glasses",
        "tablets" to "tablets",
        "tops" to "frock",
        "vehicle" to "vehicle",
        "womens-bags" to "purse",
        "womens-dresses" to "frock",
        "womens-jewellery" to "jewellery",
        "womens-shoes" to "heel",
        "womens-watches" to "watch"
    )

    val BOTTOM_NAV_ITEMS = listOf(
        NavigationItem(
            name = R.string.home,
            route = Destination.Home,
            icon = "home",
            showToolbar = true,
        ),
        NavigationItem(
            name = R.string.favourites,
            route = Destination.Favourites,
            icon = "heart",
            showToolbar = true,
        ),
        NavigationItem(
            name = R.string.profile,
            route = Destination.Profile,
            icon = "user",
        ),
    )
}