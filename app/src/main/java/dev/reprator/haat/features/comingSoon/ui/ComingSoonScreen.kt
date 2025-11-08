package dev.reprator.haat.features.comingSoon.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import dev.reprator.haat.ui.theme.HaatTheme

@Composable
fun ComingSoonScreen(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxSize()
    ) {
        Text(text = "Coming Soon")
    }
}


@Preview
@Composable
private fun ComingSoonScreenPreview() {
    HaatTheme {
        ComingSoonScreen()
    }
}