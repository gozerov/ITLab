package ru.gozerov.presentation.screens.tag_map.views

import androidx.annotation.DrawableRes
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import ru.gozerov.presentation.ui.theme.ITLabTheme

@Composable
fun DefaultMapButton(
    modifier: Modifier,
    @DrawableRes iconId: Int,
    onClick: () -> Unit
) {
    FilledIconButton(
        modifier = modifier,
        colors = IconButtonDefaults.iconButtonColors(
            containerColor = ITLabTheme.colors.primaryBackground.copy(
                0.9f
            )
        ),
        onClick = onClick
    ) {
        Icon(
            painter = painterResource(id = iconId),
            contentDescription = null,
            tint = ITLabTheme.colors.primaryText
        )
    }
}