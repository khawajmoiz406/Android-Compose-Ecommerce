package com.example.myapplication.utils

import com.example.myapplication.R
import com.example.myapplication.models.helper.NavigationItem
import com.example.myapplication.navigation.Destinations

object Constants {
    const val SPLASH_DELAY = 1500L //In Milliseconds
    const val TOKEN_TIMEOUT = 30 //In Minutes

    val CATEGORIES_ICON_MAP = mapOf(
        "all" to "home",
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
            route = Destinations.Home.route,
            icon = "home",
            showToolbar = true,
        ),
        NavigationItem(
            name = R.string.favourites,
            route = Destinations.Favourites.route,
            icon = "heart",
            showToolbar = true,
        ),
        NavigationItem(
            name = R.string.profile,
            route = Destinations.Profile.route,
            icon = "user",
        ),
    )
}