package ru.gozerov.presentation.screens.tag_map.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import ru.gozerov.domain.models.tags.Tag
import ru.gozerov.presentation.R
import ru.gozerov.presentation.screens.shared.SetupSystemBars
import ru.gozerov.presentation.ui.theme.ITLabTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TagDetailsDialog(
    tagState: MutableState<Tag?>,
    tagBottomSheetState: SheetState,
    onLikeClicked: (tag: Tag, isLikedState: MutableState<Boolean>) -> Unit,
    onDismiss: () -> Unit
) {
    SetupSystemBars(statusBarColor = Color(0x52000000))

    ModalBottomSheet(
        containerColor = ITLabTheme.colors.primaryBackground,
        onDismissRequest = onDismiss,
        sheetState = tagBottomSheetState,
    ) {
        tagState.value?.let {
            TagDetailsCard(it, onLikeClicked)
        }

    }
}

@Composable
private fun TagDetailsCard(
    tag: Tag,
    onLikeClicked: (tag: Tag, isLikedState: MutableState<Boolean>) -> Unit
) {
    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp)
    ) {
        tag.user?.let {
            val annotatedString = buildAnnotatedString {
                append(stringResource(id = R.string.author_is, it.username))
                addStyle(
                    style = SpanStyle(
                        color = ITLabTheme.colors.primaryText,
                        fontWeight = FontWeight.Bold
                    ),
                    start = 0,
                    end = 5
                )
            }
            Text(
                text = annotatedString,
                color = ITLabTheme.colors.primaryText
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
        Text(
            text = tag.description,
            color = ITLabTheme.colors.primaryText
        )
        Spacer(modifier = Modifier.height(8.dp))
        tag.image?.let {
            Box(
                modifier = Modifier
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                SubcomposeAsyncImage(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(max = 300.dp),
                    contentScale = ContentScale.Crop,
                    model = "https://maps.rtuitlab.dev$it",
                    contentDescription = null,
                    success = {
                        Image(
                            modifier = Modifier.clip(RoundedCornerShape(16.dp)),
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
                Spacer(modifier = Modifier.height(8.dp))
            }

        }
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            val isFavoriteState = remember { mutableStateOf(tag.isLiked) }
            IconButton(
                modifier = Modifier.padding(end = 8.dp),
                onClick = {
                    onLikeClicked(tag, isFavoriteState)
                }
            ) {
                Icon(
                    painter = painterResource(
                        id = if (isFavoriteState.value) R.drawable.ic_like_full_24 else R.drawable.ic_like_border_24
                    ),
                    contentDescription = null,
                    tint = ITLabTheme.colors.errorColor
                )
            }
            Text(
                text = tag.likes.toString(),
                color = ITLabTheme.colors.primaryText
            )
        }
    }

}