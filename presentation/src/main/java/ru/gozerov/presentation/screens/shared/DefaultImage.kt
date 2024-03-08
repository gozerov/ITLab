package ru.gozerov.presentation.screens.shared

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import ru.gozerov.presentation.R
import ru.gozerov.presentation.ui.theme.ITLabTheme

@Composable
fun DefaultImage(source: String, modifier: Modifier) {
    SubcomposeAsyncImage(
        modifier = modifier,
        contentScale = ContentScale.Crop,
        model = source,
        contentDescription = null,
        success = {
            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(ITLabTheme.colors.secondaryBackground),
                painter = it.painter,
                contentDescription = null
            )
        },
        error = {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_error_load),
                    contentDescription = null,
                    tint = ITLabTheme.colors.errorColor
                )
            }
        }
    )
}