package com.eli.wittix.ui.screens

import java.io.IOException

fun Throwable.userMessage() = if (this is IOException) "No internet connection" else "Something went wrong"
