package ru.gozerov.presentation.screens.choose_account.views

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import ru.gozerov.domain.models.users.User
import ru.gozerov.presentation.R
import ru.gozerov.presentation.screens.shared.SetupSystemBars
import ru.gozerov.presentation.ui.theme.ITLabTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountsList(
    contentPadding: PaddingValues,
    userList: MutableState<List<User>>,
    isListEmpty: Boolean,
    onLoginInAnotherAccount: () -> Unit,
    onItemClick: (user: User) -> Unit,
    onCloseClick: (user: User) -> Unit
) {
    SetupSystemBars()
    Scaffold(
        modifier = Modifier
            .padding(contentPadding)
            .fillMaxSize(),
        containerColor = ITLabTheme.colors.primaryBackground
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(120.dp))
            Text(
                text = stringResource(id = R.string.accounts),
                style = ITLabTheme.typography.body,
                fontWeight = FontWeight.Bold,
                color = ITLabTheme.colors.primaryText
            )
            Spacer(modifier = Modifier.height(24.dp))
            if (!isListEmpty) {
                LazyColumn(
                    modifier = Modifier.fillMaxWidth(),
                    content = {
                        items(userList.value.size) {
                            AccountItem(userList.value[it], onItemClick, onCloseClick)
                        }
                    }
                )
            } else {
                Text(text = stringResource(R.string.no_accounts))
            }
            Box(
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .weight(1f),
                contentAlignment = Alignment.BottomCenter
            ) {
                Button(
                    modifier = Modifier
                        .padding(horizontal = 64.dp)
                        .height(48.dp)
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = ITLabTheme.colors.actionColor),
                    onClick = onLoginInAnotherAccount
                ) {
                    Text(
                        text = stringResource(id = R.string.login_in_another_account)
                    )
                }
            }
        }
    }
}

@Composable
fun AccountItem(user: User, onItemClick: (user: User) -> Unit, onCloseClick: (user: User) -> Unit) {
    val interactionSource = remember { MutableInteractionSource() }
    Row(
        modifier = Modifier
            .padding(horizontal = 56.dp, vertical = 4.dp)
            .fillMaxWidth()
            .height(64.dp)
            .background(ITLabTheme.colors.secondaryBackground, RoundedCornerShape(8.dp))
            .clip(RoundedCornerShape(8.dp))
            .clickable(
                interactionSource = interactionSource,
                indication = rememberRipple(radius = 400.dp)
            ) {
                onItemClick(user)
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier.padding(16.dp),
            imageVector = Icons.Default.AccountCircle,
            tint = ITLabTheme.colors.primaryText,
            contentDescription = null
        )
        Text(
            modifier = Modifier
                .weight(1f),
            text = user.username,
            style = ITLabTheme.typography.heading,
            fontWeight = FontWeight.Normal,
            color = ITLabTheme.colors.primaryText
        )
        IconButton(
            modifier = Modifier.padding(end = 8.dp),
            onClick = { onCloseClick(user) }
        ) {
            Icon(
                imageVector = Icons.Default.Close,
                tint = ITLabTheme.colors.primaryText,
                contentDescription = null
            )
        }
    }
}