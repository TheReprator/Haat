package dev.reprator.haat.util

import android.graphics.Color.parseColor
import androidx.compose.ui.graphics.Color

val String.composeColor: Color
    get() = Color(parseColor(this))