package com.eli.wittix.data.remote.dto

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CategoryDto(
    val slug: String,
    val name: String,
)
