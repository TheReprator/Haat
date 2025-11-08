package dev.reprator.haat.ui.components.app

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.systemBars
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp

val density: Density
    @Composable
    get() = LocalDensity.current

val systemBarPadding: PaddingValues
    @Composable
    get() = WindowInsets.systemBars.asPaddingValues()


val statusBarHeight: Dp
    @Composable
    get() = systemBarPadding.calculateTopPadding()
