package com.eli.wittix.screens

import java.io.IOException

fun Throwable.userMessage() = if (this is IOException) "No internet connection" else "Something went wrong"
