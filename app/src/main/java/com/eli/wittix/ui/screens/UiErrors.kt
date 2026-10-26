package com.eli.wittix.ui.screens

import androidx.annotation.StringRes
import com.eli.wittix.R
import java.io.IOException

@StringRes
fun Throwable.userMessage() =
    if (this is IOException) R.string.error_no_internet else R.string.error_generic
