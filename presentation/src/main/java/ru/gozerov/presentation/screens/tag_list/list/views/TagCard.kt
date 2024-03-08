package ru.gozerov.presentation.screens.tag_list.list.views

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import ru.gozerov.domain.models.tags.Tag
import ru.gozerov.presentation.R
import ru.gozerov.presentation.ui.theme.ITLabTheme

@Composable
fun TagCard(tag: Tag, onTagClick: (tag: Tag) -> Unit) {
    Log.e("AAA", tag.toString())
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(ITLabTheme.colors.secondaryBackground)
            .padding(horizontal = 8.dp)
            .clickable {
                onTagClick(tag)
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            modifier = Modifier
                .size(48.dp)
                .clip(RoundedCornerShape(12.dp)),
            model = tag.image,
            contentScale = ContentScale.Crop,
            contentDescription = null,
            error = painterResource(id = R.drawable.ic_blank_image)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            tag.user?.username?.let { username ->
                val annotatedString = buildAnnotatedString {
                    append(stringResource(id = R.string.author_is, username))
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
                    color = ITLabTheme.colors.primaryText,
                    maxLines = 1
                )
            }
            Text(
                text = tag.description,
                color = ITLabTheme.colors.primaryText,
                maxLines = 1
            )
        }


    }
}