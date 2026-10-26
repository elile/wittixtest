/*
 * Copyright (c) 2026 Eli Levi. All rights reserved.
 * Developed by Eli Levi - https://www.linkedin.com/in/elilevi/
 */

package com.eli.wittix.data.remote.api

import com.eli.wittix.data.remote.dto.CategoryDto
import com.eli.wittix.data.remote.dto.ProductDto
import com.eli.wittix.data.remote.dto.ProductsResponseDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ProductsApi {
    @GET("products")
    suspend fun getProducts(
        @Query("limit") limit: Int,
        @Query("skip") skip: Int
    ): ProductsResponseDto

    @GET("products/search")
    suspend fun searchProducts(
        @Query("q") query: String,
        @Query("limit") limit: Int,
        @Query("skip") skip: Int,
    ): ProductsResponseDto

    @GET("products/category/{slug}")
    suspend fun getProductsByCategory(
        @Path("slug") slug: String,
        @Query("limit") limit: Int,
        @Query("skip") skip: Int,
    ): ProductsResponseDto

    @GET("products/{id}")
    suspend fun getProduct(@Path("id") id: Long): ProductDto

    @GET("products/categories")
    suspend fun getCategories(): List<CategoryDto>
}
