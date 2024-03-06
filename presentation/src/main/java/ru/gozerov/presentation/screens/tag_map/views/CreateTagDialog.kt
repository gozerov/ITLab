package ru.gozerov.presentation.screens.tag_map.views

import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.core.net.toFile
import ru.gozerov.presentation.R
import ru.gozerov.presentation.ui.theme.ITLabTheme

@Composable
fun CreateTagDialog(
    onDismiss: () -> Unit,
    onConfirm: (description: String, imagePath: String?) -> Unit,
) {
    val imageUri = remember { mutableStateOf<Uri?>(null) }
    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri ->
            imageUri.value = uri
        }

    val textState = remember { mutableStateOf("") }
    Dialog(
        onDismissRequest = {
            onDismiss()
        }
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(320.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = ITLabTheme.colors.primaryBackground)
        ) {
            Text(
                modifier = Modifier.padding(top = 16.dp, start = 16.dp),
                text = stringResource(id = R.string.add_tag),
                color = ITLabTheme.colors.primaryText,
                style = ITLabTheme.typography.heading,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(12.dp))
            OutlinedTextField(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth(),
                value = textState.value,
                onValueChange = {
                    textState.value = it
                },
                maxLines = 3,
                textStyle = ITLabTheme.typography.body,
                label = {
                    Text(text = stringResource(R.string.description))
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
                )
            )
            Button(
                modifier = Modifier.padding(start = 16.dp, top = 8.dp),
                onClick = {
                    launcher.launch("image/*")
                }) {

            }
            Box(
                modifier = Modifier.weight(1f),
                contentAlignment = Alignment.BottomCenter
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp, bottom = 8.dp)
                ) {
                    Button(
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = 4.dp),
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = ITLabTheme.colors.secondaryBackground),
                        onClick = {
                            onDismiss()
                        }
                    ) {
                        Text(
                            text = stringResource(id = R.string.cancel)
                        )
                    }
                    Button(
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 4.dp),
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = ITLabTheme.colors.tintColor),
                        onClick = {
                            onConfirm(textState.value, imageUri.value?.path)
                        }
                    ) {
                        Text(
                            text = stringResource(id = R.string.create)
                        )
                    }
                }
            }
        }
    }
}