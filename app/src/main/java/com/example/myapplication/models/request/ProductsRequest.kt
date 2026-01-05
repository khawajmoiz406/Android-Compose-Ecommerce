package com.example.myapplication.models.request

import com.example.myapplication.base.BaseRequest

data class ProductsRequest(
    val search: String = "",
    val category: String = "",
    val order: Order = Order.Ascending,
    val sortBy: SortArrangement = SortArrangement.Title,
) : BaseRequest() {
    override fun toMap() = hashMapOf<String, Any>(
        "order" to order.value,
        "sortBy" to sortBy.value,
    ).also { if (search.isNotEmpty()) it["q"] = search }

    fun getPath(): String {
        var path = ""
        if (category.isNotEmpty()) {
            path += if (category == "all") "" else "category/${category}"
        }
        if (search.isNotEmpty()) path += "search"
        return path
    }
}

sealed class Order(val value: String) {
    data object Ascending : Order("asc")
    data object Descending : Order("desc")
}

sealed class SortArrangement(val value: String) {
    data object Title : SortArrangement("title")
    data object Price : SortArrangement("price")
    data object Rating : SortArrangement("rating")
    data object Brand : SortArrangement("brand")
}
