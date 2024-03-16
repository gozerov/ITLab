package ru.gozerov.presentation.screens.login.views

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.gozerov.presentation.R
import ru.gozerov.presentation.ui.theme.ITLabTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginForm(
    contentPadding: PaddingValues,
    isLoadingState: MutableState<Boolean>,
    onLoginClicked: (username: String, password: String) -> Unit,
    onRegisterClicked: (username: String, password: String) -> Unit
) {
    val usernameField: MutableState<String> = remember { mutableStateOf("") }
    val passwordField: MutableState<String> = remember { mutableStateOf("") }
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
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(id = R.string.entry),
                style = ITLabTheme.typography.body,
                color = ITLabTheme.colors.primaryText
            )
            Spacer(modifier = Modifier.height(24.dp))
            LoginTextField(
                textState = usernameField,
                isLoadingState = isLoadingState,
                hintStringRes = R.string.username
            )
            Spacer(modifier = Modifier.height(8.dp))
            PasswordTextField(
                textState = passwordField,
                isLoadingState = isLoadingState,
                hintStringRes = R.string.password
            )
            Spacer(modifier = Modifier.height(48.dp))
            DefaultLoginButton(
                isLoadingState = isLoadingState,
                onClick = {
                    onLoginClicked(usernameField.value, passwordField.value)
                },
                textRes = R.string.login,
                containerColor = ITLabTheme.colors.tintColor
            )
            Spacer(modifier = Modifier.height(16.dp))
            DefaultLoginButton(
                isLoadingState = isLoadingState,
                onClick = {
                    onRegisterClicked(usernameField.value, passwordField.value)
                },
                textRes = R.string.register,
                containerColor = ITLabTheme.colors.actionColor
            )
        }
    }
}

@Composable
fun LoginTextField(
    textState: MutableState<String>,
    isLoadingState: MutableState<Boolean>,
    @StringRes hintStringRes: Int
) {
    OutlinedTextField(
        modifier = Modifier
            .padding(horizontal = 64.dp)
            .fillMaxWidth(),
        value = textState.value,
        onValueChange = {
            textState.value = it
        },
        enabled = !isLoadingState.value,
        singleLine = true,
        textStyle = ITLabTheme.typography.body,
        label = {
            Text(text = stringResource(hintStringRes))
        },
        colors = TextFieldDefaults.colors(
            focusedLabelColor = ITLabTheme.colors.tintColor,
            unfocusedLabelColor = ITLabTheme.colors.secondaryText,
            focusedContainerColor = ITLabTheme.colors.primaryBackground,
            unfocusedContainerColor = ITLabTheme.colors.primaryBackground,
            focusedIndicatorColor = ITLabTheme.colors.tintColor,
            unfocusedIndicatorColor = ITLabTheme.colors.controlColor,
            cursorColor = ITLabTheme.colors.tintColor,
            focusedTextColor = ITLabTheme.colors.primaryText,
            unfocusedTextColor = ITLabTheme.colors.primaryText,
            disabledContainerColor = ITLabTheme.colors.primaryBackground,
            disabledLabelColor = ITLabTheme.colors.secondaryText,
            disabledIndicatorColor = ITLabTheme.colors.controlColor,
            disabledTextColor = ITLabTheme.colors.controlColor
        )
    )
}


@Composable
fun PasswordTextField(
    textState: MutableState<String>,
    isLoadingState: MutableState<Boolean>,
    @StringRes hintStringRes: Int
) {
    var isPasswordVisible by remember { mutableStateOf(false) }
    OutlinedTextField(
        modifier = Modifier
            .padding(horizontal = 64.dp)
            .fillMaxWidth(),
        value = textState.value,
        onValueChange = {
            textState.value = it
        },
        enabled = !isLoadingState.value,
        singleLine = true,
        textStyle = ITLabTheme.typography.body,
        label = {
            Text(text = stringResource(hintStringRes))
        },
        colors = TextFieldDefaults.colors(
            focusedLabelColor = ITLabTheme.colors.tintColor,
            focusedContainerColor = ITLabTheme.colors.primaryBackground,
            focusedIndicatorColor = ITLabTheme.colors.tintColor,
            focusedTextColor = ITLabTheme.colors.primaryText,
            unfocusedLabelColor = ITLabTheme.colors.secondaryText,
            unfocusedContainerColor = ITLabTheme.colors.primaryBackground,
            unfocusedIndicatorColor = ITLabTheme.colors.controlColor,
            unfocusedTextColor = ITLabTheme.colors.primaryText,
            cursorColor = ITLabTheme.colors.tintColor,
            disabledContainerColor = ITLabTheme.colors.primaryBackground,
            disabledLabelColor = ITLabTheme.colors.secondaryText,
            disabledIndicatorColor = ITLabTheme.colors.controlColor,
            disabledTextColor = ITLabTheme.colors.controlColor
        ),
        visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        trailingIcon = {
            val image = if (isPasswordVisible)
                painterResource(id = R.drawable.ic_visibility_24)
            else
                painterResource(id = R.drawable.ic_visibility_off_24)

            IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                Icon(painter = image, null, tint = ITLabTheme.colors.secondaryText)
            }
        }
    )
}

@Composable
fun DefaultLoginButton(
    isLoadingState: MutableState<Boolean>,
    onClick: () -> Unit,
    @StringRes textRes: Int,
    containerColor: Color
) {
    Button(
        modifier = Modifier
            .padding(horizontal = 64.dp)
            .height(48.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        enabled = !isLoadingState.value,
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            disabledContainerColor = ITLabTheme.colors.secondaryText
        ),
        onClick = onClick
    ) {
        Text(
            text = stringResource(id = textRes),
            color = ITLabTheme.colors.secondaryBackgroundText
        )
    }
}

@Preview(showBackground = true)
@Composable
fun LoginForm_Preview() {
    val isLoading = remember { mutableStateOf(false) }
    ITLabTheme(darkTheme = true) {
        LoginForm(
            contentPadding = PaddingValues(0.dp),
            isLoadingState = isLoading,
            onLoginClicked = { _, _ -> },
            onRegisterClicked = { _, _ -> }
        )
    }
}