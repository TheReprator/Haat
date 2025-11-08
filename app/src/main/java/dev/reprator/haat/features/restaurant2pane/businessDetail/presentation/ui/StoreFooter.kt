package dev.reprator.haat.features.restaurant2pane.businessDetail.presentation.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import dev.reprator.haat.R
import dev.reprator.haat.features.restaurant2pane.businessDetail.domain.modal.ModelUiOrderInfoItem
import dev.reprator.haat.features.restaurant2pane.businessDetail.domain.modal.testModelUiOrderInfo
import dev.reprator.haat.ui.ThemePreviews
import dev.reprator.haat.ui.theme.HaatTheme


@Composable
fun StoreFooterContainer(
    scrollToTop: Boolean,
    scrollToTopCallback: (Int) -> Unit,
    orderInfo: ModelUiOrderInfoItem?,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.Bottom,
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {

        Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
            AnimatedVisibility(visible = scrollToTop, enter = fadeIn(), exit = fadeOut()) {
                FloatingScrollToTopButton(
                    onClick = {
                        scrollToTopCallback(0)
                    },
                    modifier = Modifier
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            FloatingActionCustomerCareButton(
                onClick = {},
                modifier = Modifier
            )
        }

        if (null != orderInfo) {
            OrderSummaryFloatingBar(
                orderInfo, modifier = Modifier
                    .fillMaxWidth()
            )
        }
    }
}

@Composable
private fun OrderSummaryFloatingBar(
    orderInfo: ModelUiOrderInfoItem,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.error),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        shape = MaterialTheme.shapes.medium
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp, horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {

                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .background(MaterialTheme.colorScheme.surface, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "${orderInfo.totalQuantity}",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.error
                    )
                }

                Spacer(Modifier.width(8.dp))
                Text(
                    text = "View your order",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
            Text(
                text = orderInfo.fullPrice,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
    }
}

@Composable
fun FloatingScrollToTopButton(onClick: () -> Unit, modifier: Modifier = Modifier) {
    IconButton(
        onClick = onClick,
        modifier = modifier
            .size(48.dp)
            .background(MaterialTheme.colorScheme.surface, CircleShape)
            .border(1.dp, MaterialTheme.colorScheme.error, CircleShape),
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(id = R.drawable.icon_scroll_up),
            contentDescription = "Scroll to top",
            modifier = Modifier.size(24.dp),
            tint = Color.Unspecified
        )
    }
}

@Composable
private fun FloatingActionCustomerCareButton(onClick: () -> Unit, modifier: Modifier = Modifier) {
    IconButton(
        onClick = onClick,
        modifier = modifier
            .size(64.dp)
    ) {
        Icon(
            painter = painterResource(id = R.drawable.icon_customer_care), // Placeholder for refresh/repeat icon
            contentDescription = "Repeat order",
            modifier = Modifier.size(48.dp),
            tint = Color.Unspecified
        )
    }
}


@ThemePreviews
@Composable
private fun StoreFooterContainerOnlyWithOrderPreview() {
    HaatTheme {
        StoreFooterContainer(false, {}, testModelUiOrderInfo.storeOrders["1"]!!)
    }
}

@ThemePreviews
@Composable
private fun StoreFooterContainerOnScrollWithOrderPreview() {
    HaatTheme {
        StoreFooterContainer(true, {}, testModelUiOrderInfo.storeOrders["1"]!!)
    }
}

@ThemePreviews
@Composable
private fun FooterOrderSummaryFloatingBarPreview() {
    HaatTheme {
        OrderSummaryFloatingBar(testModelUiOrderInfo.storeOrders["1"]!!)
    }
}

@ThemePreviews
@Composable
private fun FooterFloatingActionCustomerCareButtonPreview() {
    HaatTheme {
        FloatingActionCustomerCareButton({})
    }
}

@ThemePreviews
@Composable
private fun FooterFloatingScrollToTopButtonPreview() {
    HaatTheme {
        FloatingScrollToTopButton({})
    }
}