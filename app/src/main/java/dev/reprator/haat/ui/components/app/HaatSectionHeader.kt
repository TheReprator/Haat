package dev.reprator.haat.ui.components.app

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import dev.reprator.haat.ui.ThemePreviews
import dev.reprator.haat.ui.theme.HaatTheme

@Composable
fun HaatSectionHeader(
    title: String,
    modifier: Modifier = Modifier,
    showViewAll: Boolean = true,
    showViewAllAction:() -> Unit= {},
    content: @Composable ColumnScope.() -> Unit = {},
) {
    Row(
        modifier = modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text(
                text = title,
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.titleMedium
            )
            content()
        }

        if (showViewAll)
            Text(
                text = "View All",
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier
                    .clip(MaterialTheme.shapes.small)
                    .background(MaterialTheme.colorScheme.inverseOnSurface)
                    .padding(12.dp)
                     .clickable { showViewAllAction() }
            )
    }
}



@ThemePreviews
@Composable
private fun CategoriesMarketHorizontalHeaderPreview() {
    HaatTheme {
        HaatSectionHeader("New in Haat")
    }
}


@ThemePreviews
@Composable
private fun CategoriesMarketHorizontalHeaderEmptyPreview() {
    HaatTheme {
        HaatSectionHeader("")
    }
}