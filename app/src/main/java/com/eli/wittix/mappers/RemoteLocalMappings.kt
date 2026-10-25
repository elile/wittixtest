package com.eli.wittix.mappers

import com.eli.wittix.data.local.entities.CategoryEntity
import com.eli.wittix.data.local.entities.ProductEntity
import com.eli.wittix.data.remote.dto.CategoryDto
import com.eli.wittix.data.remote.dto.ProductDto

fun ProductDto.toEntity() = ProductEntity(
    id = id,
    title = title,
    description = description,
    price = price,
    discountPercentage = discountPercentage,
    rating = rating,
    stock = stock,
    brand = brand,
    category = category,
    thumbnail = thumbnail,
    images = images,
)

fun CategoryDto.toEntity() = CategoryEntity(slug = slug, name = name)
