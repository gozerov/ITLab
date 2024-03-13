package ru.gozerov.presentation.screens.account.views

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ru.gozerov.domain.models.users.User
import ru.gozerov.presentation.R
import ru.gozerov.presentation.ui.theme.ITLabTheme

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AccountView(
    user: User?,
    isGuestMode: Boolean,
    onLoginInAnotherAccount: () -> Unit
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = ITLabTheme.colors.primaryBackground
    ) { contentPadding ->

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.weight(0.2f))
            Icon(
                modifier = Modifier
                    .size(100.dp),
                painter = painterResource(id = R.drawable.ic_account_circle_72),
                contentDescription = null,
                tint = ITLabTheme.colors.primaryText
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = if (isGuestMode) stringResource(id = R.string.guest) else user?.username.toString(),
                color = ITLabTheme.colors.primaryText
            )
            Box(
                modifier = Modifier
                    .weight(1f)
                    .padding(bottom = 16.dp),
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