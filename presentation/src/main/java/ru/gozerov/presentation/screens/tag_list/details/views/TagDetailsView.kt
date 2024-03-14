package ru.gozerov.presentation.screens.tag_list.details.views

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import ru.gozerov.domain.models.tags.Tag
import ru.gozerov.domain.models.tags.TagDetails
import ru.gozerov.presentation.R
import ru.gozerov.presentation.screens.shared.DefaultImage
import ru.gozerov.presentation.ui.theme.ITLabTheme

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun TagDetailsView(
    tagDetails: TagDetails,
    isTagLiked: Boolean,
    isSubscribedState: MutableState<Boolean?>,
    onTagClick: (tag: Tag) -> Unit,
    onDeleteTagClick: (tag: Tag) -> Unit,
    onSubscribeAuthor: (username: String) -> Unit
) {
    val isSubscribed = isSubscribedState.value
    val username = tagDetails.tag.user?.username
    Scaffold(
        modifier = Modifier
            .padding(top = 16.dp)
            .fillMaxSize(),
        containerColor = ITLabTheme.colors.primaryBackground
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
        ) {
            val imageSource = tagDetails.tag.image
            if (imageSource != null) {
                DefaultImage(
                    source = imageSource,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(400.dp)
                        .padding(horizontal = 16.dp)
                )
            } else {
                Icon(
                    modifier = Modifier
                        .size(400.dp)
                        .padding(vertical = 32.dp, horizontal = 48.dp),
                    painter = painterResource(id = R.drawable.ic_blank_image),
                    tint = ITLabTheme.colors.secondaryBackground,
                    contentDescription = null
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            tagDetails.tag.user?.username?.let { username ->
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
                    modifier = Modifier.padding(horizontal = 16.dp),
                    text = annotatedString,
                    color = ITLabTheme.colors.primaryText,
                )
            }
            Spacer(Modifier.height(16.dp))
            Text(
                modifier = Modifier.padding(horizontal = 16.dp),
                text = tagDetails.tag.description,
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
                    .padding(horizontal = 16.dp)
                    .height(56.dp),
                shape = RoundedCornerShape(16.dp),
                border = likeBorderStroke,
                colors = ButtonDefaults.buttonColors(containerColor = likeButtonColor),
                onClick = { onTagClick(tagDetails.tag) }
            ) {
                Icon(
                    painter = painterResource(likePainterResource),
                    contentDescription = null,
                    tint = likeIconColor
                )
            }
            if (tagDetails.isLoggedUserAuthor) {
                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .height(56.dp),
                    shape = RoundedCornerShape(16.dp),
                    border = BorderStroke(2.dp, ITLabTheme.colors.controlColor),
                    colors = ButtonDefaults.buttonColors(containerColor = ITLabTheme.colors.primaryBackground),
                    onClick = { onDeleteTagClick(tagDetails.tag) }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_delete),
                        contentDescription = null,
                        tint = ITLabTheme.colors.controlColor
                    )
                }
            } else if (isSubscribed != null && username != null) {
                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .height(56.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = ITLabTheme.colors.tintColor),
                    onClick = {
                        onSubscribeAuthor(username)
                    }
                ) {
                    Text(
                        text = stringResource(id = if (isSubscribed) R.string.subscribed else R.string.subscribe_on_author),
                        color = ITLabTheme.colors.primaryText
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}