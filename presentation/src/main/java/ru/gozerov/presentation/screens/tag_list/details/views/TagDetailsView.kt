package ru.gozerov.presentation.screens.tag_list.details.views

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import ru.gozerov.domain.models.tags.Tag
import ru.gozerov.presentation.R
import ru.gozerov.presentation.screens.shared.DefaultImage
import ru.gozerov.presentation.ui.theme.ITLabTheme

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun TagDetailsView(tag: Tag, isTagLiked: Boolean, onTagClick: (tag: Tag) -> Unit) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        containerColor = ITLabTheme.colors.primaryBackground
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .scrollable(rememberScrollState(), Orientation.Vertical)
                .padding(16.dp)
        ) {
            val imageSource = tag.image
            if (imageSource != null) {
                DefaultImage(
                    source = imageSource,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(400.dp)
                )
            } else {
                Icon(
                    modifier = Modifier
                        .size(400.dp)
                        .padding(32.dp),
                    painter = painterResource(id = R.drawable.ic_blank_image),
                    tint = ITLabTheme.colors.secondaryBackground,
                    contentDescription = null
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
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
                )
            }
            Spacer(Modifier.height(16.dp))
            Text(
                text = tag.description,
                color = ITLabTheme.colors.primaryText,
            )
            Spacer(modifier = Modifier.height(32.dp))
            val likeButtonColor =
                if (isTagLiked) ITLabTheme.colors.errorColor else ITLabTheme.colors.primaryBackground
            val likeIconColor =
                if (isTagLiked) ITLabTheme.colors.primaryText else ITLabTheme.colors.errorColor
            val likePainterResource =
                if (isTagLiked) R.drawable.ic_like_full_24 else R.drawable.ic_like_border_24
            val likeBorderStroke =
                if (!isTagLiked) BorderStroke(2.dp, ITLabTheme.colors.errorColor) else null
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(16.dp),
                border = likeBorderStroke,
                colors = ButtonDefaults.buttonColors(containerColor = likeButtonColor),
                onClick = { onTagClick(tag) }
            ) {
                Icon(
                    painter = painterResource(likePainterResource),
                    contentDescription = null,
                    tint = likeIconColor
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                onClick = { }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_delete),
                    contentDescription = null
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                onClick = { }
            ) {
                Text(text = stringResource(id = R.string.subscribe_on_author))
            }
        }
    }
}