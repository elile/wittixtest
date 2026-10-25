package com.eli.wittix.mappers

import com.eli.wittix.data.local.entities.CartItemWithProduct
import com.eli.wittix.data.local.entities.CategoryEntity
import com.eli.wittix.data.local.entities.ProductEntity
import com.eli.wittix.domain.models.CartItem
import com.eli.wittix.domain.models.Category
import com.eli.wittix.domain.models.Product
import com.eli.wittix.domain.models.ProductDetails

fun ProductEntity.toProduct() = Product(
    id = id,
    title = title,
    price = price,
    discountPercentage = discountPercentage,
    rating = rating,
    stock = stock,
    category = category,
    thumbnail = thumbnail,
)

fun ProductEntity.toDetails() = ProductDetails(
    product = toProduct(),
    description = description,
    brand = brand,
    images = images,
)

fun CategoryEntity.toDomain() = Category(slug = slug, name = name)

fun CartItemWithProduct.toDomain() =
    CartItem(product = product.toProduct(), quantity = item.quantity)
